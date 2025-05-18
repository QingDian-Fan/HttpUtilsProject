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


    CURLcode get_request(const std::string &url, std::string &response,vector<char*> &headers);


    CURLcode post_request(const string &url, const string &postParams, string &response,vector<char*> &headers);



}
#endif //JNI_DEMO_NETWORKREQUEST_H
