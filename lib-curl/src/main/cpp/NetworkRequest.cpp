//
// Created by QingDian Fan on 2023/3/28.
//

#include "NetworkRequest.h"



namespace network{

    size_t write_callback(char *ptr, size_t size, size_t nmemb, void *userdata){
        ((string*)userdata)->append(ptr, nmemb);
        return nmemb;
    }


    CURLcode get_request(const std::string &url, std::string &response,vector<char*> &headers){
        //curl初始化
        CURL *curl = curl_easy_init();
        // curl返回值
        CURLcode res = CURLE_INTERFACE_FAILED;
        if (curl)
        {
            //设置curl的请求头
            struct curl_slist* header_list = nullptr;
            header_list = curl_slist_append(header_list, "User-Agent: curl/android");
            for (auto & header : headers){
                header_list = curl_slist_append(header_list,header);
            }
            curl_easy_setopt(curl, CURLOPT_HTTPHEADER, header_list);

            //不接收响应头数据0代表不接收 1代表接收
            curl_easy_setopt(curl, CURLOPT_HEADER, 1);

            //设置请求的URL地址
            curl_easy_setopt(curl, CURLOPT_URL, url.c_str());

            //设置ssl验证
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, false);
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, false);

            //CURLOPT_VERBOSE的值为1时，会显示详细的调试信息
            curl_easy_setopt(curl, CURLOPT_VERBOSE, 0);

            curl_easy_setopt(curl, CURLOPT_READFUNCTION, NULL);

            //设置数据接收函数
            curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, write_callback);
            curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&response);

            curl_easy_setopt(curl, CURLOPT_NOSIGNAL, 1);

            //设置超时时间
            curl_easy_setopt(curl, CURLOPT_CONNECTTIMEOUT, 6); // set transport and time out time
            curl_easy_setopt(curl, CURLOPT_TIMEOUT, 6);

            // 开启请求
            res = curl_easy_perform(curl);
        }
        // 释放curl
        curl_easy_cleanup(curl);
        return res;
    }


    CURLcode post_request(const string &url, const string &postParams, string &response,vector<char*> &headers){
        // curl初始化
        CURL *curl = curl_easy_init();
        // curl返回值
        CURLcode res = CURLE_INTERFACE_FAILED;
        if (curl){
            // set params
            //设置curl的请求头
            struct curl_slist* header_list = nullptr;
            header_list = curl_slist_append(header_list, "Content-Type:application/x-www-form-urlencoded; charset=UTF-8");
            header_list = curl_slist_append(header_list, "User-Agent: curl/android");
            for (auto & header : headers){
                header_list = curl_slist_append(header_list,header);
            }
            curl_easy_setopt(curl, CURLOPT_HTTPHEADER, header_list);

            //不接收响应头数据0代表不接收 1代表接收
            curl_easy_setopt(curl, CURLOPT_HEADER, 1);

            //设置请求为post请求
            curl_easy_setopt(curl, CURLOPT_POST, 1);

            //设置请求的URL地址
            curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
            //设置post请求的参数
            curl_easy_setopt(curl, CURLOPT_POSTFIELDS, postParams.c_str());

            //设置ssl验证
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, false);
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, false);

            //CURLOPT_VERBOSE的值为1时，会显示详细的调试信息
            curl_easy_setopt(curl, CURLOPT_VERBOSE, 0);

            curl_easy_setopt(curl, CURLOPT_READFUNCTION, NULL);

            //设置数据接收和写入函数
            curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, write_callback);
            curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&response);

            curl_easy_setopt(curl, CURLOPT_NOSIGNAL, 1);

            //设置超时时间
            curl_easy_setopt(curl, CURLOPT_CONNECTTIMEOUT, 6);
            curl_easy_setopt(curl, CURLOPT_TIMEOUT, 6);

            // 开启post请求
            res = curl_easy_perform(curl);
        }
        //释放curl
        curl_easy_cleanup(curl);
        return res;
    }


}
