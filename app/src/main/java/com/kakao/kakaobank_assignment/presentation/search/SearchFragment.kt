package com.kakao.kakaobank_assignment.presentation.search

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaobank_assignment.R
import com.kakao.kakaobank_assignment.binding.BindingFragment
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto
import com.kakao.kakaobank_assignment.databinding.FragmentSearchBinding
import com.kakao.kakaobank_assignment.presentation.MainViewModel
import com.kakao.kakaobank_assignment.presentation.search.adapter.SearchImageAdapter
import com.kakao.kakaobank_assignment.util.GridSpacingItemDecoration
import com.kakao.kakaobank_assignment.util.ImageZoomInActivity
import com.kakao.kakaobank_assignment.util.callback.OnItemClick
import com.kakao.kakaobank_assignment.util.callback.OnScrapClear
import com.kakao.kakaobank_assignment.util.extension.hideKeyboard
import com.kakao.kakaobank_assignment.util.extension.showKeyboard
import com.kakao.kakaobank_assignment.util.extension.showToast
import com.kakao.kakaobank_assignment.util.state.UiState
import timber.log.Timber


class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search),
    OnItemClick, OnScrapClear {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val adapter by lazy {
        SearchImageAdapter(this, this)
    }
    private lateinit var recyclerViewState: Parcelable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        addObservers()
        initLayout()
    }

    override fun onResume() {
        super.onResume()
        //restore scroll state after setting adapter
        if (::recyclerViewState.isInitialized) {
            (binding.rvSearchImage.layoutManager as GridLayoutManager).onRestoreInstanceState(
                recyclerViewState
            )
        }
    }

    override fun onPause() {
        super.onPause()
        //store scroll state
        recyclerViewState =
            (binding.rvSearchImage.layoutManager as GridLayoutManager).onSaveInstanceState()!!
    }
    
    private fun setListeners() {
        with(binding) {
            etSearch.setOnFocusChangeListener { _, b ->
                ivSearchCancel.isSelected = b
            }
            etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == IME_ACTION_SEARCH) {
                        searchMedias(v)
                        return true
                    }
                    return false
                }
            })
            ivSearchCancel.setOnClickListener {
                if (ivSearchCancel.isSelected) {
                    etSearch.text?.clear()
                    etSearch.clearFocus()
                    requireContext().hideKeyboard(binding.etSearch)
                    ivSearchCancel.isSelected = false
                } else {
                    etSearch.requestFocus()
                    requireContext().showKeyboard(binding.etSearch)
                    ivSearchCancel.isSelected = true
                }
            }
            //RecyclerView Scroll Paging
            rvSearchImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                private var lastScrollTime = 0
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        //overscroll event
                        if (!recyclerView.canScrollVertically(1)) {
                            val lastPosition =
                                (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                            val totalCount = recyclerView.adapter?.itemCount
                            //prevent duplicate scroll event call
                            if (SystemClock.elapsedRealtime() - lastScrollTime > 1000) {
                                getPagingData(lastPosition,totalCount!!)
                            }
                            lastScrollTime = SystemClock.elapsedRealtime().toInt()
                        }
                    }

                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //store scroll state
                    recyclerViewState =
                        (recyclerView.layoutManager as GridLayoutManager).onSaveInstanceState()!!
                }
            })
        }
        binding.floatingBtnScrollSearch.setOnClickListener {
            binding.rvSearchImage.smoothScrollToPosition(0)
        }
    }

    private fun searchMedias(tv:TextView?){
        with(binding){
            if (binding.etSearch.text.isNullOrEmpty()) {
                mainViewModel.searchText.value = null
            }
            mainViewModel.initMedias()
            mainViewModel.getKakaoImages()
            ivSearchCancel.isSelected = false
            tv?.clearFocus()
            requireContext().hideKeyboard(binding.etSearch)
        }
    }

    private fun getPagingData(lastPosition: Int, totalCount: Int) {
        if (lastPosition == totalCount - 1 && mainViewModel.pageCount < MAX_PAGE && !mainViewModel.isPageEnd) {
            mainViewModel.increasePage()
            mainViewModel.getKakaoImages()
        } else {
            showToast("맨 끝!")
        }
    }

    private fun addObservers() {
        mainViewModel.uiState.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                UiState.Success -> initAdapter()
                UiState.PAGED -> recyclerViewPaging()
                UiState.PAGING -> Timber.d("Paging")
                UiState.Failure -> showToast("검색 실패")
                UiState.Loading -> Timber.d("Loading")
                UiState.Empty -> showToast("검색 결과가 없습니다!")
            }
        }
        mainViewModel.scrapClearedItem.observe(this.viewLifecycleOwner) {
            initAdapter()
        }
    }

    private fun recyclerViewPaging() {
        with(binding) {
            rvSearchImage.adapter = adapter.apply {
                submitList(mainViewModel.kakaoMedias)
            }
            //restore scroll state after setting adapter
            (rvSearchImage.layoutManager as GridLayoutManager).onRestoreInstanceState(
                recyclerViewState
            )
        }
    }

    private fun initAdapter() {
        with(binding) {
            rvSearchImage.adapter = adapter.apply {
                submitList(mainViewModel.kakaoMedias)
            }
        }
    }

    private fun initLayout() {
        with(binding) {
            vm = mainViewModel
            lifecycleOwner = this@SearchFragment.viewLifecycleOwner
            floatingBtnScrollSearch.setIconResource(R.drawable.search_arrow_scroll)
        }
        with(binding.rvSearchImage) {
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            addItemDecoration(
                GridSpacingItemDecoration(
                    requireContext(),
                    SPAN_COUNT,
                    SPACE,
                    SPACE_TOP
                )
            )
            setHasFixedSize(true)
        }
    }

    override fun scrapItem(itemInfo: KakaoMediaDto) {
        //store scroll state
        recyclerViewState =
            (binding.rvSearchImage.layoutManager as GridLayoutManager).onSaveInstanceState()!!
        mainViewModel.scrapItem(itemInfo)
    }

    override fun scrapClearItem(itemInfo: String, position: Int) {
        mainViewModel.scrapClear(itemInfo)
        //restore scroll state after setting adapter
        (binding.rvSearchImage.layoutManager as GridLayoutManager).onRestoreInstanceState(
            recyclerViewState
        )
    }

    override fun selectImage(view: View, imageUrl: String) {
        val intent = Intent(context, ImageZoomInActivity::class.java)
        intent.putExtra("imageUrl", imageUrl)
        val opt = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), view, "imgTrans")
        ContextCompat.startActivity(requireContext(), intent, opt.toBundle())
    }

    companion object {
        const val SPAN_COUNT = 2
        const val SPACE = 10
        const val SPACE_TOP = 16
        const val MAX_PAGE = 15
    }
}