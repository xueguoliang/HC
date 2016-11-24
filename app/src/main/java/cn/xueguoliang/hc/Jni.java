package cn.xueguoliang.hc;

/**
 * Created by xueguoliang on 2016/11/24.
 */

public class Jni {
    static  {
        System.loadLibrary("bc-lib"); // libbc-lib.so
    }

    private static Jni obj = new Jni();
    private Jni(){}

    public static Jni instance(){
        return obj;
    }

    // native接口
    public native boolean Login(String username, String password);
}
