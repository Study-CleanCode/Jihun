package com.kakao.kakaobank_assignment.presentation.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto
import com.kakao.kakaobank_assignment.databinding.ItemImageBinding
import com.kakao.kakaobank_assignment.util.DiffKakaoMedia
import com.kakao.kakaobank_assignment.util.callback.OnItemClick
import com.kakao.kakaobank_assignment.util.callback.OnScrapClear


class SearchImageAdapter(private val listener: OnItemClick, private val sListener: OnScrapClear) :
    ListAdapter<KakaoMediaDto, SearchImageViewHolder>(DiffKakaoMedia()) {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchImageViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return SearchImageViewHolder(
            parent.context,
            ItemImageBinding.inflate(inflater, parent, false),
            listener,
            sListener
        )
    }

    override fun onBindViewHolder(holder: SearchImageViewHolder, position: Int) {
        holder.onBind(currentList[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

class SearchImageViewHolder(
    private val context: Context,
    private val binding: ItemImageBinding,
    private val listener: OnItemClick,
    private val sListener: OnScrapClear
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: KakaoMediaDto, position: Int) {
        with(binding) {
            Glide.with(context).load(data.thumbnailUrl).into(ivItemImage)
            tvItemDate.text = data.dateTime.subSequence(range = 0..9)
            ivItemImage.setOnClickListener {
                listener.selectImage(it, data.contentUrl) //확대된 이미지는 contentUrl 사용
            }
            btnItemScrap.isSelected = data.isScraped
            btnItemScrap.setOnClickListener {
                it.isSelected = !it.isSelected
                if (it.isSelected) {
                    listener.scrapItem(
                        KakaoMediaDto(
                            data.thumbnailUrl,
                            data.dateTime,
                            data.contentUrl,
                            it.isSelected
                        )
                    )
                } else {
                    sListener.scrapClearItem(data.thumbnailUrl, position)
                }
            }
        }
    }
}
