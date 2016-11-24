//
// Created by 薛国良 on 2016/11/24.
//

#ifndef HC_CURL_H
#define HC_CURL_H

#include <string>
#include "curl/include/curl/curl.h"

using namespace std;

class Curl {
public:
    Curl(string url, bool ignoreCert = true);
    ~Curl();

    bool execute(string requestData);
    string responseData();

    static ssize_t callback(char* ptr, size_t m, size_t n, void* arg);

private:
    string _responseData;
    CURL * _curl;
};


#endif //HC_CURL_H
