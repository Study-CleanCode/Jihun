package com.kakao.kakaobank_assignment.util

import androidx.recyclerview.widget.DiffUtil
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto


class DiffKakaoMedia : DiffUtil.ItemCallback<KakaoMediaDto>() {
    override fun areItemsTheSame(oldItem: KakaoMediaDto, newItem: KakaoMediaDto): Boolean =
        oldItem.thumbnailUrl == newItem.thumbnailUrl

    override fun areContentsTheSame(oldItem: KakaoMediaDto, newItem: KakaoMediaDto): Boolean =
        oldItem == newItem
}