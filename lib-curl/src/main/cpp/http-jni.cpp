
#include <cstdio>
#include <cstdlib>
#include <jni.h>
#include <string>
#include "vector"
#include "android/log.h"
#include "NetworkRequest.h"


using namespace std;
using namespace network;


//  curl -X POST -H "Content-Type:application/x-www-form-urlencoded" -H "Content-Length:39" -H "Host:www.wanandroid.com" -H "Connection:Keep-Alive" -H "Accept-Encoding:gzip" -H "User-Agent:okhttp/4.9.1" --data $'password=dian3426&username=QingDian_Fan' --compressed "https://www.wanandroid.com/user/login"
// curl -X POST --data $'password=dian3426&username=QingDian_Fan' --compressed "https://www.wanandroid.com/user/login"

#define TAG "TAG----->CURL"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, ##__VA_ARGS__);


const char *errorJson = "{\"errorCode\":\"1001\",\"errorMsg\":\"网络请求错误\"}";

extern "C"
JNIEXPORT jstring JNICALL
Java_com_curl_http_HttpClient_RequestGetCurl(JNIEnv *env, jobject thiz, jstring url,
                                             jobject headers) {
    jclass clazz = env->GetObjectClass(headers);
    jmethodID getMethodId = env->GetMethodID(clazz, "get", "(I)Ljava/lang/Object;");
    jmethodID sizeMethodId = env->GetMethodID(clazz, "size", "()I");
    jint len = env->CallIntMethod(headers, sizeMethodId);
    vector<char *> request_headers;
    for (int i = 0; i < len; i++) {
        auto header = (jstring) env->CallObjectMethod(headers, getMethodId, i);
        const char *headerString = env->GetStringUTFChars(header, nullptr);
        request_headers.push_back(const_cast<char *>(headerString));
    }
    string url_string = const_cast<char *>(env->GetStringUTFChars(url, nullptr));
    string response_string;
    auto res = get_request(url_string, response_string, request_headers);
    if (res == CURLE_OK) {
        return env->NewStringUTF(response_string.c_str());
    } else {
        return env->NewStringUTF(errorJson);
    }
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_curl_http_HttpClient_RequestPostCurl(JNIEnv *env, jobject thiz, jstring url,
                                              jobject headers, jstring params) {
    jclass clazz = env->GetObjectClass(headers);
    jmethodID getMethodId = env->GetMethodID(clazz, "get", "(I)Ljava/lang/Object;");
    jmethodID sizeMethodId = env->GetMethodID(clazz, "size", "()I");
    jint len = env->CallIntMethod(headers, sizeMethodId);
    vector<char *> request_headers;

    for (int i = 0; i < len; i++) {
        auto header = (jstring) env->CallObjectMethod(headers, getMethodId, i);
        const char *headerString = env->GetStringUTFChars(header, nullptr);
        request_headers.push_back(const_cast<char *>(headerString));
    }

    string url_string = const_cast<char *>(env->GetStringUTFChars(url, nullptr));
    string params_string = const_cast<char *>(env->GetStringUTFChars(params, nullptr));
    string response_string;
    auto res = post_request(url_string, params_string, response_string, request_headers);
    if (res == CURLE_OK) {
        return env->NewStringUTF(response_string.c_str());
    } else {
        return env->NewStringUTF(errorJson);
    }
}