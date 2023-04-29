package com.kakao.kakaobank_assignment.data.model.dto

data class KakaoMediaDto(
    val thumbnailUrl: String,
    val dateTime: String,
    val contentUrl: String,
    var isScraped : Boolean
)