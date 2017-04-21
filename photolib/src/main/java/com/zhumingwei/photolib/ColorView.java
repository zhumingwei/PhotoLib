package com.zhumingwei.photolib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Arrays;

/*用来演示用颜色矩阵设置滤镜的效果*/
public class ColorView extends ImageView {

    /**
     R’ = a*R + b*G + c*B + d*A + e;
     G’ = f*R + g*G + h*B + i*A + j;
     B’ = k*R + l*G + m*B + n*A + o;
     A’ = p*R + q*G + r*B + s*A + t;
     */
    private Paint myPaint = null;
    private Bitmap bitmap = null;
    private ColorMatrix myColorMatrix = null;
    private float[] colorArray = {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

    public ColorView(Context c){
        super( c);

    }
    public ColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        colorArray = BAOLILAI;
        myColorMatrix = new ColorMatrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int size;
        if(width>height){
            size = height;
        }else{
            size = width;
        }
        setMeasuredDimension(size,size);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap==null){
            return;
        }
        //新建画笔对象  
        myPaint = new Paint();
        //描画（原始图片）      
//        canvas.drawBitmap(bitmap, 0, 0, myPaint);
        //新建颜色矩阵对象      

        //设置颜色矩阵的值  
        myColorMatrix.set(colorArray);

        //设置画笔颜色过滤器
        myPaint.setColorFilter(new ColorMatrixColorFilter(myColorMatrix));
        //描画（处理后的图片）
        Rect rectF = new Rect(0,0,getWidth(),getHeight());

//        Matrix matrix = new Matrix();
//        matrix.postScale(getWidth()/(float)bitmap.getWidth(),getWidth()/(float)bitmap.getWidth());
//        canvas.drawBitmap(bitmap,matrix,myPaint);
//        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),rectF, myPaint);
        canvas.drawBitmap(bitmap,0,0,myPaint);
    }
    public void reset(){
        colorArray = YUANTU;
        myColorMatrix.reset();
    }

    //设置颜色数值  
    public void setColorArray(float[] colorArray) {
        this.colorArray = colorArray;
    }

    public ColorMatrix getMyColorMatrix() {
        return myColorMatrix;
    }

    //设置图片
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    public static final float[] YUANTU = {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

    public static final float[] BAOLILAI = {1.438f, -0.062f, -0.062f, 0, 0,
            -0.122f, 1.378f, -0.122f, 0, 0,
            -0.016f, -0.016f, 1.483f, 0, 0,
            -0.03f, 0.05f, -0.02f, 1, 0};

    public static final float[] HUAIJIU = {0.393f,0.769f,0.189f,0,0,
            0.349f,0.686f,0.168f,0,0,
            0.272f,0.534f,0.131f,0,0,
            0,0,0,1,0};

    //鲜艳红
    public static final float[] FANHONG ={2,0,0,0,0,
            0,1,0,0,0,
            0,0,1,0,0,
            0,0,0,1,0 };

    //荧光绿
    public static final float[] FANLV ={
            1,0,0,0,0,
            0,1.4F,0,0,0,
            0,0,1,0,0,
            0,0,0,1,0
    };
    //宝石蓝
    public static final float[] FANLAN ={
            1,0,0,0,0,
            0,1,0,0,0,
            0,0,1.6F,0,0,
            0,0,0,1,0
    };
    //泛黄
    public static final float[] FANHUANG = {
            1,0,0,0,50,
            0,1,0,0,50,
            0,0,1,0,0,
            0,0,0,1,0
    };
    //底片
    public static final float[] DIPIAN = {
            -1, 0, 0, 0, 255,
            0, -1, 0, 0, 255,
            0, 0, -1, 0, 255,
            0, 0, 0, 1, 0
    };
    //黑白
    public static final float[] HEIBAI = {
            0.3F, 0.59F, 0.11F, 0, 0,
            0.3F, 0.59F, 0.11F, 0, 0,
            0.3F, 0.59F, 0.11F, 0, 0,
            0, 0, 0, 1, 0

    };
}  