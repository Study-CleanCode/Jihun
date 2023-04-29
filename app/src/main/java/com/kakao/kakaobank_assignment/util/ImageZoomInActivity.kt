package com.kakao.kakaobank_assignment.util

import android.os.Bundle
import com.bumptech.glide.Glide
import com.kakao.kakaobank_assignment.R
import com.kakao.kakaobank_assignment.binding.BindingActivity
import com.kakao.kakaobank_assignment.databinding.ActivityImageZoomInBinding

class ImageZoomInActivity :
    BindingActivity<ActivityImageZoomInBinding>(R.layout.activity_image_zoom_in) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val img = intent.getStringExtra(resources.getString(R.string.img_url))
        Glide.with(this).load(img).into(binding.imageFull)
        binding.imageFull.setOnClickListener {
            supportFinishAfterTransition()
        }
    }
}