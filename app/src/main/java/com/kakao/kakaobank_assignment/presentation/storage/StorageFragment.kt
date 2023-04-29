package com.kakao.kakaobank_assignment.presentation.storage

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kakao.kakaobank_assignment.R
import com.kakao.kakaobank_assignment.binding.BindingFragment
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto
import com.kakao.kakaobank_assignment.databinding.FragmentStorageBinding
import com.kakao.kakaobank_assignment.presentation.MainViewModel
import com.kakao.kakaobank_assignment.presentation.storage.adapter.StorageImageAdapter
import com.kakao.kakaobank_assignment.util.GridSpacingItemDecoration
import com.kakao.kakaobank_assignment.util.ImageZoomInActivity
import com.kakao.kakaobank_assignment.util.callback.OnItemClick
import com.kakao.kakaobank_assignment.util.callback.OnScrapClear

class StorageFragment : BindingFragment<FragmentStorageBinding>(R.layout.fragment_storage),
    OnItemClick, OnScrapClear {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val adapter by lazy {
        StorageImageAdapter(this, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initAdapter()
        addObservers()
    }

    private fun initLayout() {
        with(binding) {
            vm = mainViewModel
            lifecycleOwner = this@StorageFragment.viewLifecycleOwner
        }
        with(binding.rvStorageImage) {
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            addItemDecoration(
                GridSpacingItemDecoration(
                    requireContext(),
                    SPAN_COUNT,
                    SPACE,
                    SPACE_TOP
                )
            )
            setHasFixedSize(true)
        }
    }

    private fun initAdapter() {
        with(binding) {
            rvStorageImage.adapter = adapter.apply {
                submitList(mainViewModel.scrapedItems)
            }
        }
    }

    private fun addObservers() {
        mainViewModel.scrapCount.observe(this.viewLifecycleOwner) {
            with(binding) {
                rvStorageImage.adapter = adapter.apply {
                    addList(mainViewModel.scrapedItems)
                }
            }
        }
    }

    override fun scrapItem(itemInfo: KakaoMediaDto) {

    }

    override fun selectImage(view: View, imageUrl: String) {
        val intent = Intent(context, ImageZoomInActivity::class.java)
        intent.putExtra("imageUrl", imageUrl)
        val opt = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), view, "imgTrans")
        ContextCompat.startActivity(requireContext(), intent, opt.toBundle())
    }

    override fun scrapClearItem(itemInfo: String, position: Int) {
        mainViewModel.scrapClear(itemInfo)
        adapter.removeData(position)
    }

    companion object {
        const val SPAN_COUNT = 2
        const val SPACE = 10
        const val SPACE_TOP = 16
    }
}