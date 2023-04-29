package com.kakao.kakaobank_assignment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.kakaobank_assignment.data.model.dto.KakaoImageDto
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto
import com.kakao.kakaobank_assignment.data.model.dto.KakaoVideoDto
import com.kakao.kakaobank_assignment.data.model.request.RequestKakaoImage
import com.kakao.kakaobank_assignment.data.model.request.RequestKakaoVideo
import com.kakao.kakaobank_assignment.domain.KakaoMediaRepository
import com.kakao.kakaobank_assignment.util.extension.toMediaDto
import com.kakao.kakaobank_assignment.util.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.math.min


@HiltViewModel
class MainViewModel @Inject constructor(private val kakaoMediaRepository: KakaoMediaRepository) :
    ViewModel() {

    private var _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState>
        get() = _uiState

    var searchText = MutableLiveData<String?>(null)

    private var _kakaoMedias = mutableListOf<KakaoMediaDto>()
    val kakaoMedias: List<KakaoMediaDto>
        get() = _kakaoMedias

    private var _isPageEnd = false
    val isPageEnd: Boolean
        get() = _isPageEnd

    private var _pageCount = DEFAULT_PAGE_COUNT
    val pageCount: Int
        get() = _pageCount

    private var _scrapedItems = mutableListOf<KakaoMediaDto>()
    val scrapedItems: List<KakaoMediaDto>
        get() = _scrapedItems

    private var _scrapCount = MutableLiveData(0)
    val scrapCount: LiveData<Int>
        get() = _scrapCount

    private var _scrapClearedItem = MutableLiveData<String>()
    val scrapClearedItem: LiveData<String>
        get() = _scrapClearedItem

    fun getKakaoImages() {
        viewModelScope.launch {
            runCatching {
                _uiState.value = UiState.Loading
                val deferredImages: Deferred<Pair<List<KakaoImageDto>, Boolean>> =
                    async(Dispatchers.IO) {
                        kakaoMediaRepository.getKakaoImages(
                            RequestKakaoImage(
                                searchText.value ?: DEFAULT_SEARCH_KEYWORD,
                                sort = SORT_TYPE,
                                page = pageCount,
                                size = LOAD_MEDIA_SIZE
                            )
                        )
                    }
                val deferredVideos: Deferred<Pair<List<KakaoVideoDto>, Boolean>> =
                    async(Dispatchers.IO) {
                        kakaoMediaRepository.getKakaoVideos(
                            RequestKakaoVideo(
                                searchText.value ?: DEFAULT_SEARCH_KEYWORD,
                                sort = SORT_TYPE,
                                page = pageCount,
                                size = LOAD_MEDIA_SIZE
                            )
                        )
                    }
                val result = awaitAll(deferredImages, deferredVideos)
                result
            }.onSuccess { (images, videos) ->
                val kakaoImages = images.first
                val kakaoVideos = videos.first
                val minLength = min(kakaoImages.size, kakaoVideos.size)
                for (i in 0 until minLength) {
                    with(_kakaoMedias) {
                        add((kakaoImages[i] as KakaoImageDto).toMediaDto())
                        add((kakaoVideos[i] as KakaoVideoDto).toMediaDto())
                    }
                }
                _isPageEnd = images.second || videos.second
                if (_pageCount > DEFAULT_PAGE_COUNT) {
                    _uiState.value = UiState.PAGED
                } else if (_kakaoMedias.isNotEmpty()) {
                    _uiState.value = UiState.Success
                } else {
                    _uiState.value = UiState.Empty
                }
            }.onFailure {
                _uiState.value = UiState.Failure
            }
        }
    }

    fun scrapItem(kakaoMediaDto: KakaoMediaDto) {
        _kakaoMedias.forEach {
            if (it.thumbnailUrl == kakaoMediaDto.thumbnailUrl) {
                it.isScraped = true
            }
        }
        _scrapedItems.add(kakaoMediaDto)
        _scrapCount.value = _scrapCount.value?.plus(1)
    }

    fun scrapClear(itemInfo: String) {
        _kakaoMedias.forEach {
            if (it.thumbnailUrl == itemInfo) {
                it.isScraped = false
            }
        }
        _scrapClearedItem.value = itemInfo
        _scrapedItems.removeIf { info -> info.thumbnailUrl == _scrapClearedItem.value }
        _scrapCount.value = _scrapCount.value?.minus(1)
    }

    fun initMedias() {
        _kakaoMedias.clear()
        _pageCount = 1
    }

    fun increasePage() {
        _pageCount += 1
    }

    fun restoreScrapItems(restoredList: ArrayList<KakaoMediaDto>) {
        _scrapedItems = restoredList
        _scrapCount.value = restoredList.size
    }

    companion object {
        const val LOAD_MEDIA_SIZE = 10
        const val DEFAULT_PAGE_COUNT = 1
        const val SORT_TYPE = "recency"
        const val DEFAULT_SEARCH_KEYWORD = "kakao"
    }

}