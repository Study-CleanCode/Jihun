package com.kakao.kakaobank_assignment.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestKakaoVideo(
    @SerialName("query")
    val name:String,
    @SerialName("sort")
    val sort:String="accuracy",
    @SerialName("page")
    val page:Int=1,
    @SerialName("size")
    val size:Int=15
)
