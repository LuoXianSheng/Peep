package com.github.lxs.peep.utils;

import android.graphics.Bitmap;

import com.github.lxs.peep.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by cl on 2017/4/10.
 */

public class DownloadUtil {

    public static String saveImage(Bitmap bitmap) {
        File filePath = new File(Constants.DOWNLOAD_PATH);
        if (!filePath.exists()) filePath.mkdirs();
        FileOutputStream fos = null;
        try {
            File file = new File(Constants.DOWNLOAD_PATH, System.currentTimeMillis() + ".jpg");
            file.createNewFile();
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            return "下载成功！";
        } catch (IOException e) {
            e.printStackTrace();
            return "下载失败！";
        } finally {
            if (fos != null) try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
