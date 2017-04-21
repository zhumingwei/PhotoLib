package com.zhumingwei.photolib.util;

/**
 * Created by zhumingwei on 2017/4/20.
 */

public class ImageUtilEngine {

    static {
        System.loadLibrary("photoLib");
    }

    //浮雕算法
    public native int[] toFudiao(int[] buffer, int width, int height);
}
