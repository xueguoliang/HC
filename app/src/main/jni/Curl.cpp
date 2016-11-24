//
// Created by 薛国良 on 2016/11/24.
//

#include "Curl.h"
#include "Util.h"

Curl::Curl(string url, bool ignoreCert) {
    _curl = curl_easy_init();
    curl_easy_setopt(_curl, CURLOPT_URL, url.c_str());

    HC_LOG("request url is %s\n", url.c_str());

    // ignore CA problem
    if(ignoreCert) {
        HC_LOG("%s", "set ignore Cert\n");
        curl_easy_setopt(_curl, CURLOPT_SSL_VERIFYPEER, 0L);
        curl_easy_setopt(_curl, CURLOPT_SSL_VERIFYHOST, 0L);
    }
}

Curl::~Curl() {
    curl_easy_cleanup(_curl);
}

ssize_t Curl::callback(char *ptr, size_t m, size_t n, void *arg) {

    Curl* This = (Curl*)arg;
    int count = m*n;

    // 拷贝ptr中的数据到_responseData
   // This->_responseData

    // NO
  //  string data(ptr);
  //  This->_responseData += data;

#if 0
    效率太低
    for(int i=0; i<count; ++i)
    {
        This->_responseData += ptr[i];
    }
#endif

    copy(ptr, ptr+count, back_inserter(This->_responseData));

    return count;
}

bool Curl::execute(string requestData) {
    curl_easy_setopt(_curl, CURLOPT_POST, 1);
    curl_easy_setopt(_curl, CURLOPT_POSTFIELDS, requestData.c_str());

    curl_easy_setopt(_curl, CURLOPT_WRITEFUNCTION, Curl::callback);
    curl_easy_setopt(_curl, CURLOPT_WRITEDATA, this);


    CURLcode code = curl_easy_perform(_curl);
    if(code != CURLE_OK)
    {
        HC_LOG("curl perform error, code is %d\n", (int )code);
    }

    return code == CURLE_OK;
}

string Curl::responseData() {
    return _responseData;
}
