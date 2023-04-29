package com.kakao.kakaobank_assignment.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.kakao.kakaobank_assignment.R
import com.kakao.kakaobank_assignment.application.PreferenceManager
import com.kakao.kakaobank_assignment.binding.BindingActivity
import com.kakao.kakaobank_assignment.data.model.dto.KakaoMediaDto
import com.kakao.kakaobank_assignment.databinding.ActivityMainBinding
import com.kakao.kakaobank_assignment.presentation.search.SearchFragment
import com.kakao.kakaobank_assignment.presentation.storage.StorageFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var searchFragment: SearchFragment
    private lateinit var storageFragment: StorageFragment
    private lateinit var fragmentManager: FragmentManager
    private val gson = GsonBuilder().create()
    private val kakaoMediaType: Type = object :
        TypeToken<ArrayList<KakaoMediaDto?>?>() {}.type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initFragment()
        addListeners()
    }

    override fun onStart() {
        super.onStart()

        val scrapItemsToRestore = PreferenceManager.getString(applicationContext, "scrapedList")
        if (scrapItemsToRestore != "none") {
            val tempArray: ArrayList<KakaoMediaDto> = (gson.fromJson(scrapItemsToRestore, kakaoMediaType))
            viewModel.restoreScrapItems(tempArray)
        }
    }

    override fun onStop() {
        super.onStop()
        val scrapItemsToStore = gson.toJson(viewModel.scrapedItems, kakaoMediaType)
        PreferenceManager.setString(applicationContext, "scrapedList", scrapItemsToStore)
    }

    private fun addListeners() {
        binding.bottomNaviMain.setOnItemSelectedListener {
            convertFragment(it.itemId)
            true
        }
    }

    private fun initFragment() {
        fragmentManager = supportFragmentManager
        searchFragment = SearchFragment()
        storageFragment = StorageFragment()
        fragmentManager.commit {
            add(R.id.fragment_container_view_main, storageFragment)
            hide(storageFragment)
            add(R.id.fragment_container_view_main, searchFragment)
        }
    }

    private fun convertFragment(menuItemId: Int) {
        with(fragmentManager) {
            when (menuItemId) {
                R.id.menu_search -> commit {
                    setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right)
                    hide(storageFragment)
                    show(searchFragment)
                }
                R.id.menu_storage -> commit {
                    setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    hide(searchFragment)
                    show(storageFragment)
                }
                else -> IllegalArgumentException("${this::class.java.simpleName} Not found menu item id")
            }
        }

    }
}