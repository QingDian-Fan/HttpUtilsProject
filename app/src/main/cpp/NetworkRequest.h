//
// Created by QingDian Fan on 2023/3/28.
//

#ifndef JNI_DEMO_NETWORKREQUEST_H
#define JNI_DEMO_NETWORKREQUEST_H


#include <string>
#include "vector"
#include "curl.h"

using namespace std;

namespace network {


    static size_t write_callback(char *ptr, size_t size, size_t nmemb, void *userdata);


    CURLcode get_request(const std::string &url, std::string &response, vector<char *> &headers);


    CURLcode post_request(const string &url, const string &postParams, string &response,vector<char *> &headers);


    size_t download_request_reply(void *buffer, size_t size, size_t nmemb, void *user_p);


    CURLcode download_get_request(const string &url, string filename);

}
#endif //JNI_DEMO_NETWORKREQUEST_H
