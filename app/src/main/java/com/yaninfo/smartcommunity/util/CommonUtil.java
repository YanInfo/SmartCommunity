package com.yaninfo.smartcommunity.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * @Author: zhangyan
 * @Date: 2019/4/30 14:47
 * @Description:
 * @Version: 1.0
 */
public class CommonUtil {

    /**
     * 根据路径转化为Bitmap对象
     *
     * @param pathString
     * @return
     */
    public static Bitmap getBitMap(String pathString) {
        Bitmap bitmap = null;
        File file = new File(pathString);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(pathString);
        }
        return bitmap;
    }
}
