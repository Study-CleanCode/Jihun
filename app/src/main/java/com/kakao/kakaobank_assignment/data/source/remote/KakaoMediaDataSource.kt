package com.kakao.kakaobank_assignment.data.source.remote

import com.kakao.kakaobank_assignment.data.api.KakaoMediaService
import com.kakao.kakaobank_assignment.data.model.request.RequestKakaoImage
import com.kakao.kakaobank_assignment.data.model.request.RequestKakaoVideo
import com.kakao.kakaobank_assignment.data.model.response.ResponseKakaoImage
import com.kakao.kakaobank_assignment.data.model.response.ResponseKakaoVideo

class KakaoMediaDataSource(private val kakaoService: KakaoMediaService) {
    suspend fun getKakaoImages(requestKakaoImage: RequestKakaoImage): ResponseKakaoImage =
        kakaoService.getKakaoImages(
            name = requestKakaoImage.name,
            sort = requestKakaoImage.sort,
            page = requestKakaoImage.page,
            size = requestKakaoImage.size
        )

    suspend fun getKakaoVideos(requestKakaoVideo: RequestKakaoVideo): ResponseKakaoVideo =
        kakaoService.getKakaoVideos(
            name = requestKakaoVideo.name,
            sort = requestKakaoVideo.sort,
            page = requestKakaoVideo.page,
            size = requestKakaoVideo.size
        )
}