package com.kakao.kakaobank_assignment.data.repository

import com.kakao.kakaobank_assignment.data.model.dto.KakaoImageDto
import com.kakao.kakaobank_assignment.data.model.dto.KakaoVideoDto
import com.kakao.kakaobank_assignment.data.model.request.RequestKakaoImage
import com.kakao.kakaobank_assignment.data.model.request.RequestKakaoVideo
import com.kakao.kakaobank_assignment.data.source.remote.KakaoMediaDataSource
import com.kakao.kakaobank_assignment.domain.KakaoMediaRepository
import com.kakao.kakaobank_assignment.util.extension.toDto

class KakaoMediaRepositoryImpl(private val kakaoMediaDataSource: KakaoMediaDataSource) :
    KakaoMediaRepository {
    override suspend fun getKakaoImages(requestKakaoImage: RequestKakaoImage): Pair<List<KakaoImageDto>, Boolean> {
        val kakaoResult = kakaoMediaDataSource.getKakaoImages(requestKakaoImage)
        val kakaoImageIterator = kakaoResult.documents.listIterator()
        val kakaoImageList = mutableListOf<KakaoImageDto>()
        kakaoImageIterator.forEach {
            kakaoImageList.add(it.toDto())
        }
        return Pair(kakaoImageList.sortedBy { it.dateTime }.reversed(), kakaoResult.meta.isEnd)
    }

    override suspend fun getKakaoVideos(requestKakaoVideo: RequestKakaoVideo): Pair<List<KakaoVideoDto>, Boolean> {
        val kakaoResult = kakaoMediaDataSource.getKakaoVideos(requestKakaoVideo)
        val kakaoVideoIterator = kakaoResult.documents.listIterator()
        val kakaoVideoList = mutableListOf<KakaoVideoDto>()
        kakaoVideoIterator.forEach {
            kakaoVideoList.add(it.toDto())
        }
        return Pair(kakaoVideoList.sortedBy { it.dateTime }.reversed(), kakaoResult.meta.isEnd)
    }
}