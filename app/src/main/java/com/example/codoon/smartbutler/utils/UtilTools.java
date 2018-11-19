package com.example.codoon.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.utils
 * 文件名：UtilTools
 * 创建时间：2018/10/8 上午10:59
 * 描述：工具的统一类
 */

public class UtilTools {
    //设置字体
    public static void setFont(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }
    //保存图片到ShareUtils
    public static void putImagetoShare(Context context,ImageView circleImageView){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) circleImageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        //第一步.将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //转化成输出流
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步.利用Base64将我们的字节数组输出流转化成String
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        //第三部.将string保存到shareUtils
        ShareUtil.putString(context, "image_title", imageString);
    }
    //拿到保存的图片
    public static void getImagetoShare(Context context,ImageView circleImageView){
        //1.拿到string
        String imgString = ShareUtil.getString(context, "image_title", "");
        if (!imgString.isEmpty()) {
            //2.利用Base64将我们的string转化
            byte[] bytes = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            circleImageView.setImageBitmap(bitmap);
        }
    }
}
