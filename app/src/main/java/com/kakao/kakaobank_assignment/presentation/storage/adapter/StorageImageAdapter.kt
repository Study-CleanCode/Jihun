package com.kakao.kakaobank_assignment.presentation.storage.adapter

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
import timber.log.Timber

class StorageImageAdapter(private val listener: OnItemClick, private val sListener: OnScrapClear) :
    ListAdapter<KakaoMediaDto, StorageImageViewHolder>(DiffKakaoMedia()) {
    private lateinit var inflater: LayoutInflater


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageImageViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return StorageImageViewHolder(
            parent.context,
            ItemImageBinding.inflate(inflater, parent, false),
            listener,
            sListener
        )
    }

    override fun onBindViewHolder(holder: StorageImageViewHolder, position: Int) {
        holder.onBind(currentList[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateData(data: List<KakaoMediaDto>) {
        val newData = mutableListOf<KakaoMediaDto>()
        newData.addAll(data)
        submitList(newData)
    }

    fun removeData(position: Int) {
        if (currentList.isNotEmpty()) {
            val newData = mutableListOf<KakaoMediaDto>()
            newData.addAll(currentList)
            newData.removeAt(position)
            submitList(newData)
        }
    }
}

class StorageImageViewHolder(
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
            Timber.d("isScraped ${data.isScraped}")
            btnItemScrap.setOnClickListener {
                it.isSelected = !it.isSelected
                sListener.scrapClearItem(data.thumbnailUrl, position)
            }
        }
    }
}

