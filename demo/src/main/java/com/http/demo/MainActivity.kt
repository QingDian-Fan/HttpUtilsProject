package com.http.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.curl.http.HttpUtils

class MainActivity : AppCompatActivity() {
    private val map = hashMapOf<String, String>()


    private val tvContent: AppCompatTextView by lazy { findViewById(R.id.tv_content) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
//https://api.apiopen.top/api/getImages?page=0&size=10
    fun clickView(mView: View) {
        when (mView.id) {
            R.id.btn_get -> {
                map.clear()
                map["cid"] = "294"
                HttpUtils.getInstance()
                    .asyncGetRequest(
                        "https://www.wanandroid.com/lg/todo/listnotdo/0/json/1",
                        null,
                        map
                    ) {
                        Log.e("TAG----->GET", it)
                        tvContent.text = it
                    }
            }
            R.id.btn_post -> {
                map.clear()
                map["username"] = "xxx"
                map["password"] = "xxx"
                HttpUtils.getInstance()
                    .asyncPostRequest("https://www.wanandroid.com/user/login", null, map) {
                        Log.e("TAG----->POST", it)
                        tvContent.text = it
                    }

            }
        }
    }
}