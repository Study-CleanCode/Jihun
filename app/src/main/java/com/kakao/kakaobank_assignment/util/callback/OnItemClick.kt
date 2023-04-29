package com.kakao.kakaobank_assignment.util.callback

import android.view.View
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto

interface OnItemClick {
    fun scrapItem(itemInfo: KakaoMediaDto)
    fun selectImage(view: View, imageUrl: String)
}