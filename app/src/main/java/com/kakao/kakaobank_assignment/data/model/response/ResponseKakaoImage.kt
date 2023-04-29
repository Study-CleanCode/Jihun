package com.kakao.kakaobank_assignment.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseKakaoImage(
    @SerialName("documents")
    val documents: List<Document>,
    @SerialName("meta")
    val meta: Meta
)

@Serializable
data class Document(
    @SerialName("collection")
    val collection: String,
    @SerialName("datetime")
    val dateTime: String,
    @SerialName("display_sitename")
    val displaySiteName: String,
    @SerialName("doc_url")
    val docUrl: String,
    @SerialName("height")
    val height: Int,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("width")
    val width: Int
)