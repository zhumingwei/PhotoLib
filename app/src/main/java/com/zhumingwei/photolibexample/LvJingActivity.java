package com.zhumingwei.photolibexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhumingwei.photolib.ColorView;
import com.zhumingwei.photolib.util.ImageUtilEngine;

public class LvJingActivity extends AppCompatActivity {
    Bitmap bitmap;
    ColorView colorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv_jing);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.demo1);
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_1);
        colorView = (ColorView) findViewById(R.id.colorview);
        source(null);
    }

    public void source(View view) {
        colorView.reset();
        colorView.setBitmap(bitmap);
//        原图
    }

    public void baolilai(View view) {
        colorView.reset();
        colorView.setColorArray(ColorView.BAOLILAI);
        colorView.setBitmap(bitmap);
    }

    public void huaijiu(View view) {
        colorView.reset();
        colorView.setColorArray(ColorView.HUAIJIU);
        colorView.setBitmap(bitmap);
    }

    public void hong(View view) {
        colorView.reset();
        colorView.setColorArray(ColorView.FANHONG);
        colorView.setBitmap(bitmap);
    }

    public void lv(View view) {
        colorView.reset();
        colorView.setColorArray(ColorView.FANLV);
        colorView.setBitmap(bitmap);
    }

    public void lan(View view) {
        colorView.reset();
        colorView.setColorArray(ColorView.FANLAN);
        colorView.setBitmap(bitmap);
    }

    public void huang(View view) {
        colorView.reset();
        colorView.setColorArray(ColorView.FANHUANG);
        colorView.setBitmap(bitmap);
    }

    public void dipian(View view) {
        colorView.reset();
        colorView.setColorArray(ColorView.DIPIAN);
        colorView.setBitmap(bitmap);
    }

    public void heibai(View view) {
        colorView.reset();
        colorView.setColorArray(ColorView.HEIBAI);
        colorView.setBitmap(bitmap);
    }

    public void fudiao(View view) {
        int[] buf = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(buf, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int[] result=  new ImageUtilEngine().toFudiao(buf,bitmap.getWidth(),bitmap.getHeight());
        Bitmap tempbitmap = Bitmap.createBitmap(result,bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.RGB_565);
        colorView.reset();
        colorView.getMyColorMatrix().setSaturation(0);
        colorView.setBitmap(tempbitmap);
    }
}
