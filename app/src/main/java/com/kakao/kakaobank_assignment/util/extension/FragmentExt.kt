package com.kakao.kakaobank_assignment.util.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}
