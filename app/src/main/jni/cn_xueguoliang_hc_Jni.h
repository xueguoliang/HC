/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class cn_xueguoliang_hc_Jni */

#ifndef _Included_cn_xueguoliang_hc_Jni
#define _Included_cn_xueguoliang_hc_Jni
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     cn_xueguoliang_hc_Jni
 * Method:    Login
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_cn_xueguoliang_hc_Jni_Login
  (JNIEnv *, jobject, jstring, jstring, jstring);

JNIEXPORT jboolean JNICALL Java_cn_xueguoliang_hc_Jni_Reg
        (JNIEnv *env, jobject /* Jni object */,
         jstring jUsername, jstring jPassword, jstring mobile, jstring email, jstring id);
#ifdef __cplusplus
}
#endif
#endif
