package com.kakao.kakaobank_assignment.data.api

import com.kakao.kakaobank_assignment.data.model.response.ResponseKakaoImage
import com.kakao.kakaobank_assignment.data.model.response.ResponseKakaoVideo
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoMediaService {
    @GET("/v2/search/image")
    suspend fun getKakaoImages(
        @Query("query") name: String? = "kakao",
        @Query("sort") sort: String? = "recency",
        @Query("page") page: Int? = 1,
        @Query("size") size: Int? = 10
    ): ResponseKakaoImage

    @GET("/v2/search/vclip")
    suspend fun getKakaoVideos(
        @Query("query") name: String? = "kakao",
        @Query("sort") sort: String? = "recency",
        @Query("page") page: Int? = 1,
        @Query("size") size: Int? = 15
    ): ResponseKakaoVideo
}