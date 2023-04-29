package com.kakao.kakaobank_assignment.domain

import com.kakao.kakaobank_assignment.data.model.dto.KakaoImageDto
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto
import com.kakao.kakaobank_assignment.data.model.dto.KakaoVideoDto
import com.kakao.kakaobank_assignment.data.model.request.RequestKakaoImage
import com.kakao.kakaobank_assignment.data.model.request.RequestKakaoVideo

interface KakaoMediaRepository {
    suspend fun getKakaoImages(requestKakaoImage: RequestKakaoImage): Pair<List<KakaoImageDto>, Boolean>
    suspend fun getKakaoVideos(requestKakaoVideo: RequestKakaoVideo): Pair<List<KakaoVideoDto>, Boolean>
}