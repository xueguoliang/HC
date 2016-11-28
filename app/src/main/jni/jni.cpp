//
// Created by 薛国良 on 2016/11/24.
//
#include "cn_xueguoliang_hc_Jni.h"
#include "User.h"
#include "Order.h"

jstring c2j(JNIEnv* env, string cstr)
{
    return env->NewStringUTF(cstr.c_str());
}

string j2c(JNIEnv* env, jstring jstr)
{
    string ret;
    jclass stringClass = env->FindClass("java/lang/String");
    jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");

    // 把参数用到的字符串转化成java的字符
    jstring arg = c2j(env, "utf-8");

    jbyteArray jbytes = (jbyteArray)env->CallObjectMethod(jstr, getBytes, arg);

    // 从jbytes中，提取UTF8格式的内容
    jsize byteLen = env->GetArrayLength(jbytes);
    jbyte* JBuffer = env->GetByteArrayElements(jbytes, JNI_FALSE);

    // 将内容拷贝到C++内存中
    if(byteLen > 0)
    {
        char* buf = (char*)JBuffer;
        std::copy(buf, buf+byteLen, back_inserter(ret));
    }

    // 释放
    env->ReleaseByteArrayElements(jbytes, JBuffer, 0);
    return ret;
}

JNIEXPORT jboolean JNICALL Java_cn_xueguoliang_hc_Jni_Login
        (JNIEnv *env, jobject /* Jni object */, jstring jUsername, jstring jPassword, jstring type)
{
    return (jboolean)User::instance()->Login(j2c(env, jUsername), j2c(env, jPassword),
    j2c(env, type));
}

JNIEXPORT jboolean JNICALL Java_cn_xueguoliang_hc_Jni_Reg
        (JNIEnv *env, jobject /* Jni object */,
         jstring jUsername, jstring jPassword, jstring mobile, jstring email, jstring id)
{
    return (jboolean)User::instance()->Reg(
            j2c(env, jUsername),
            j2c(env, jPassword),
            j2c(env, mobile),
            j2c(env, email),
            j2c(env, id));
}

JNIEXPORT jboolean JNICALL Java_cn_xueguoliang_hc_Jni_LocationChange
        (JNIEnv *, jobject, jdouble lng, jdouble lat)
{
    User::instance()->LocationChange(lng, lat);
    return (jboolean)true;
}


JNIEXPORT jboolean JNICALL Java_cn_xueguoliang_hc_Jni_StartOrder
        (JNIEnv *, jobject, jdouble lng1, jdouble lat1, jdouble lng2, jdouble lat2)
{
    return (jboolean)Order::instance()->start(lng1, lat1, lng2, lat2);
}