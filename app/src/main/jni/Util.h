//
// Created by 薛国良 on 2016/11/24.
//

#ifndef HC_UTIL_H
#define HC_UTIL_H

#include <android/log.h>
#include <string>
using namespace std;

static const char* tag = "HC_JNI_TAG";
#define HC_LOG(fmt, ...) __android_log_print(ANDROID_LOG_ERROR, tag, fmt, __VA_ARGS__)

class Util {
public:
    static string toString(double d);

};


#endif //HC_UTIL_H
