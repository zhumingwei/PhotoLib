#include <stdlib.h>
#include <android/log.h>
#include <android/bitmap.h>
#include <math.h>

#define LOG_TAG "libibmphotophun"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

JNIEXPORT jintArray JNICALL
Java_com_zhumingwei_photolib_util_ImageUtilEngine_toFudiao(JNIEnv *env, jobject instance,
                                                           jintArray buf, jint width,
                                                           jint height) {
    jint *cbuf;
    cbuf = (*env)->GetIntArrayElements(env, buf, 0);
    LOGE("Bitmap Buffer %d %d", cbuf[0], cbuf[1]);

    int newSize = width * height;

    jint rbuf[newSize]; // 新图像像素值

    int count = 0;
    int preColor = 0;
    int prepreColor = 0;
    int color = 0;
    preColor = cbuf[0];

    int i = 0;
    int j = 0;
    for (i = 0; i < width; i++) {
        for (j = 0; j < height; j++) {
            int curr_color = cbuf[j * width + i];
            int r = red(curr_color) - red(prepreColor) + 128;
            int g = green(curr_color) - red(prepreColor) + 128;
            int b = green(curr_color) - blue(prepreColor) + 128;
            int a = alpha(curr_color);
            int modif_color = ARGB(a, r, g, b);
            rbuf[j * width + i] = modif_color;
            prepreColor = preColor;
            preColor = curr_color;
        }
    }
    jintArray result = (*env)->NewIntArray(env, newSize); // 新建一个jintArray
    (*env)->SetIntArrayRegion(env, result, 0, newSize, rbuf); // 将rbuf转存入result
    (*env)->ReleaseIntArrayElements(env, buf, cbuf, 0); // 释放int数组元素
    return result;
}

int alpha(int color) {
    return (color >> 24) & 0xFF;
}

int red(int color) {
    return (color >> 16) & 0xFF;
}

int green(int color) {
    return (color >> 8) & 0xFF;
}

int blue(int color) {
    return color & 0xFF;
}

int ARGB(int alpha, int red, int green, int blue) {
    return (alpha << 24) | (red << 16) | (green << 8) | blue;
}
