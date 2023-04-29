package com.kakao.kakaobank_assignment.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseKakaoVideo(
    @SerialName("documents")
    val documents: List<VideoDocument>,
    @SerialName("ds")
    val ds: List<String>,
    @SerialName("g")
    val g: List<String>,
    @SerialName("m")
    val m: M,
    @SerialName("meta")
    val meta: Meta
)

@Serializable
data class VideoDocument(
    @SerialName("author")
    val author: String,
    @SerialName("datetime")
    val dateTime: String,
    @SerialName("play_time")
    val playTime: Int,
    @SerialName("thumbnail")
    val thumbnailUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val videoUrl: String
)

@Serializable
class M

