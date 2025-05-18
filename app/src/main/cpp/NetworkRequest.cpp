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
        CURLcode res;
        if (curl)
        {
            //设置curl的请求头
            struct curl_slist* header_list = NULL;
            header_list = curl_slist_append(header_list, "User-Agent: curl/android");
            for (int i = 0;i < headers.size(); i++ ){
                header_list = curl_slist_append(header_list,headers[i]);
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
        CURLcode res;
        if (curl){
            // set params
            //设置curl的请求头
            struct curl_slist* header_list = NULL;
            header_list = curl_slist_append(header_list, "Content-Type:application/x-www-form-urlencoded; charset=UTF-8");
            header_list = curl_slist_append(header_list, "User-Agent: curl/android");
            for (int i = 0;i < headers.size(); i++ ){
                header_list = curl_slist_append(header_list,headers[i]);
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



    //下载文件数据接收函数
    size_t download_request_reply(void *buffer, size_t size, size_t nmemb, void *user_p){
        FILE *fp = (FILE *)user_p;
        size_t return_size = fwrite(buffer, size, nmemb, fp);
        //cout << (char *)buffer << endl;
        return return_size;
    }

    //http GET请求文件下载
    CURLcode download_get_request(const string &url, string filename){
        //int len = filename.length();
        //char* file_name = new char(len + 1);//char*最后有一个结束字符\0
        //strcpy_s(file_name, len + 1, filename.c_str());

        const char* file_name = filename.c_str();
        char* pc = new char[1024];//足够长
        strcpy(pc, file_name);

        FILE *fp = fopen(pc, "wb");

        //curl初始化
        CURL *curl = curl_easy_init();
        // curl返回值
        CURLcode res;
        if (curl)
        {
            //设置curl的请求头
            struct curl_slist* header_list = NULL;
            header_list = curl_slist_append(header_list, "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
            curl_easy_setopt(curl, CURLOPT_HTTPHEADER, header_list);

            //不接收响应头数据0代表不接收 1代表接收
            curl_easy_setopt(curl, CURLOPT_HEADER, 0);

            //设置请求的URL地址
            curl_easy_setopt(curl, CURLOPT_URL, url.c_str());

            //设置ssl验证
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, false);
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, false);

            //CURLOPT_VERBOSE的值为1时，会显示详细的调试信息
            curl_easy_setopt(curl, CURLOPT_VERBOSE, 0);

            curl_easy_setopt(curl, CURLOPT_READFUNCTION, NULL);

            //设置数据接收函数
            curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, &download_request_reply);
            curl_easy_setopt(curl, CURLOPT_WRITEDATA, fp);

            curl_easy_setopt(curl, CURLOPT_NOSIGNAL, 1);

            //设置超时时间
            curl_easy_setopt(curl, CURLOPT_CONNECTTIMEOUT, 6); // set transport and time out time
            curl_easy_setopt(curl, CURLOPT_TIMEOUT, 6);

            // 开启请求
            res = curl_easy_perform(curl);
        }
        // 释放curl
        curl_easy_cleanup(curl);
        //释放文件资源
        fclose(fp);

        return res;
    }



}
