

LOCAL_PATH:=$(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := curl
LOCAL_SRC_FILES := curl/armeabi/libcurl.a
include $(PREBUILT_STATIC_LIBRARY)


include $(CLEAR_VARS)
LOCAL_SRC_FILES:= jni.cpp User.cpp cJSON.c Json.cpp Util.cpp Curl.cpp Data.cpp
LOCAL_MODULE:=bc-lib
# 将静态库编译到动态库中
LOCAL_STATIC_LIBRARIES := curl
# 系统的库
LOCAL_LDLIBS += -llog -lz
include $(BUILD_SHARED_LIBRARY)

