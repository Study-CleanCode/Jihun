package com.kakao.kakaobank_assignment.util.state

sealed class UiState {
    object Success : UiState()
    object Empty : UiState()
    object Failure : UiState()
    object Loading : UiState()
    object PAGED : UiState()
    object PAGING : UiState()
}
