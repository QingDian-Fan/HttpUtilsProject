package com.ndk.network.http

import android.net.Uri
import android.util.Log
import java.util.regex.Matcher
import java.util.regex.Pattern

object Utils {
    fun getDomain(url: String): String {
        return try {
            val pattern: Pattern = Pattern.compile(
                "[^//]*?\\.(com|cn|net|org|biz|info|cc|tv|top)",
                Pattern.CASE_INSENSITIVE
            )
            val matcher: Matcher = pattern.matcher(url)
            matcher.find()
            matcher.group()
        } catch (e: IllegalStateException) {
            Log.e("TAG----->IllegalStateException", e.message + "")
            Uri.parse(url).host + ""
        }

    }
}