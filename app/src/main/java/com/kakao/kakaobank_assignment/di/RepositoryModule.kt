package com.kakao.kakaobank_assignment.di

import com.kakao.kakaobank_assignment.data.api.ApiClient
import com.kakao.kakaobank_assignment.data.api.KakaoMediaService
import com.kakao.kakaobank_assignment.data.repository.KakaoMediaRepositoryImpl
import com.kakao.kakaobank_assignment.data.source.remote.KakaoMediaDataSource
import com.kakao.kakaobank_assignment.domain.KakaoMediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideKakaoMediaRepository(): KakaoMediaRepository {
        return KakaoMediaRepositoryImpl(
            KakaoMediaDataSource(
                ApiClient.getApiClient()!!.create(KakaoMediaService::class.java)
            )
        )
    }
}