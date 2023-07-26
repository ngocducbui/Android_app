package com.example.prm_final.Convert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverters;

import java.io.ByteArrayOutputStream;

public class ImageBitMapString {
    @TypeConverters
    public static byte[] getStringFromBitMap(Bitmap bitmanPicture){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmanPicture.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
        byte[] b= byteArrayOutputStream.toByteArray();
        return b;
    }
    @TypeConverters
    public static Bitmap getBitMapFromStr(byte [] arr){
        return BitmapFactory.decodeByteArray(arr,0, arr.length);
    }
}
