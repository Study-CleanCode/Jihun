package com.kakao.kakaobank_assignment.util.extension

import com.kakao.kakaobank_assignment.data.model.dto.KakaoImageDto
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto
import com.kakao.kakaobank_assignment.data.model.dto.KakaoVideoDto
import com.kakao.kakaobank_assignment.data.model.response.Document
import com.kakao.kakaobank_assignment.data.model.response.VideoDocument

fun Document.toDto(): KakaoImageDto {
    return KakaoImageDto(thumbnailUrl, dateTime, imageUrl,)
}

fun VideoDocument.toDto(): KakaoVideoDto {
    return KakaoVideoDto(thumbnailUrl, dateTime, videoUrl)
}

fun KakaoImageDto.toMediaDto(): KakaoMediaDto{
    return KakaoMediaDto(thumbnailUrl,dateTime,imageUrl,false)
}
fun KakaoVideoDto.toMediaDto(): KakaoMediaDto{
    return KakaoMediaDto(thumbnailUrl,dateTime,thumbnailUrl,false)
}