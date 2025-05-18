package com.ndk.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ndk.network.databinding.ActivityMainBinding
import com.ndk.network.http.HttpUtils
import com.ndk.network.http.Request

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val map = hashMapOf<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


    }


    fun clickView(mView: View) {
        when (mView.id) {
            R.id.btn_get -> {
                map.clear()
                map["page"] = "0"
                map["size"] = "10"
                HttpUtils.getInstance()
                    .asyncGetRequest(
                        "https://api.apiopen.top/api/getImages",
                        null,
                        map
                    ) {
                        Log.e("TAG----->GET", it)
                        binding.tvContent.text = it
                    }
            }
            R.id.btn_post -> {

                /* map.clear()
                 map["k"] = "android";

                 val request = Request.Builder()
                     .setUrl("https://www.wanandroid.com/article/query/0/json")
                     .setParams(map)
                     .setCookies(cookies)
                     .build()

                 HttpUtils.getInstance()
                     .asyncPostRequest(request) {
                         Log.e("TAG----->POST", it)
                         binding.tvContent.text = it
                     }*/

                map.clear()
                map["username"] = "QingDian_Fan"
                map["password"] = "dian3426"
                HttpUtils.getInstance()
                    .asyncPostRequest("https://www.wanandroid.com/user/login", null, map) {
                        Log.e("TAG----->POST", it)
                        binding.tvContent.text = it
                    }

            }
        }
    }


}