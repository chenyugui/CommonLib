package com.taichuan.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by gui on 2017/7/21.
 * 文件操作工具类
 */
public class FileUtil {
    private static final String TAG = "FileUtil";
    private static final String SDCARD_DIR =
            Environment.getExternalStorageDirectory().getPath();

    private static final String DEFAULT_DIR = SDCARD_DIR + "/downloads/";

    /**
     * 根据扩展名生成File对象，例如（PNG_yyyy_MM_dd_HH_mm_ss)
     *
     * @param dir       文件目录
     * @param extension 文件扩展名，可为空
     */
    private static File createFileByTime(String dir, String extension) {
        // 去掉点
        if (extension != null && extension.indexOf(".") == 0) {
            extension = extension.substring(1, extension.length());
        }
        StringBuilder sb = new StringBuilder();
        if (extension == null || extension.isEmpty()) {
            sb.append("FILE")// 前缀
                    .append("_")
                    .append(TimeUtil.dateToyyyyMMdd_HHmmss(new Date()));
        } else {
            sb.append(extension.toUpperCase())// 前缀
                    .append("_")
                    .append(TimeUtil.dateToyyyyMMdd_HHmmss(new Date()))
                    .append(".")
                    .append(extension);// 后缀
        }
        return new File(dir, sb.toString());
    }

    /**
     * 检查目录是否存在，不存在则创建
     *
     * @return 是否创建成功
     */
    @SuppressWarnings("SimplifiableIfStatement")

    public static boolean createDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return true;
        }
    }

    /**
     * 获取文件的后缀名
     */
    public static String getExtension(String filePath) {
        String suffix = "";
        final File file = new File(filePath);
        final String name = file.getName();
        final int idx = name.lastIndexOf('.');
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    /**
     * 通过输入流，写入文件到磁盘
     *
     * @param is         输入流
     * @param dir        文件保存路径，为空则使用默认的目录
     * @param extension  文件扩展名，如果{@param #fileName}不为空
     * @param fileName   文件全名，为空则自动使用" 扩展名_yyyyMMdd_HHmmss.扩展名" 的形式命名
     * @param fileLength 文件大小
     */
    public static File writeToDisk(InputStream is, String dir, String extension, String fileName, long fileLength) {
        if (is == null)
            return null;
        if (dir == null || dir.isEmpty()) {
            Log.w(TAG, "writeToDisk: dir is empty, use default dir");
            dir = DEFAULT_DIR;
        }
        createDir(dir);
        File file;
        if (fileName == null || fileName.isEmpty()) {// 文件名为空，使用扩展名_yyyyMMdd_HHmmss.扩展名的形式
            file = createFileByTime(dir, extension);
        } else {
            file = new File(dir, fileName);
        }
        return writeToDisk(is, file, fileLength);
    }

    @SuppressWarnings("WeakerAccess")
    public static File writeToDisk(InputStream is, File file, long fileLength) {
        if (is == null || file == null)
            return null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte data[] = new byte[1024 * 4];

            int length;
            double receivedAllLength = 0;//已接收的大小
            while ((length = bis.read(data)) != -1) {
                bos.write(data, 0, length);
                receivedAllLength = receivedAllLength + length;
                if (fileLength > 0) {
                    Log.d(TAG, "writeToDisk: " + (int) (receivedAllLength / fileLength * 100) + "%");
                }
            }
            bos.flush();
            fos.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
