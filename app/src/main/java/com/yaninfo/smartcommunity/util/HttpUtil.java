package com.yaninfo.smartcommunity.util;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaninfo.smartcommunity.uploadEvent.BitmapUtils;
import com.yaninfo.smartcommunity.uploadEvent.MyStringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangyan
 * @Date: 2019/4/25 10:06
 * @Description: 这里是利用Okhttp来向服务器发送数据，当然此项目中用的是socket通信
 * @Version: 1.0
 */
public class HttpUtil {

    private PostFormBuilder mPost;
    private GetBuilder mGet;

    public HttpUtil() {
        OkHttpUtils.getInstance().getOkHttpClient().newBuilder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(15 * 1000L, TimeUnit.MILLISECONDS)
                .build();

        mPost = OkHttpUtils.post();
        mGet = OkHttpUtils.get();
    }

    //封装请求
    public void postRequest(String url, Map<String, String> params, MyStringCallBack callback) {
        mPost.url(url)
                .params(params)
                .build()
                .execute(callback);
    }

    //上传文件
    public void postFileRequest(String url, Map<String, String> params, ArrayList<ImageItem> pathList, MyStringCallBack callback) {

        Map<String,File> files = new HashMap<>();
        for (int i = 0; i < pathList.size(); i++) {
            String newPath = BitmapUtils.compressImageUpload(pathList.get(i).path);
            files.put(pathList.get(i).name+i,new File(newPath));
        }

        // 打印缓存之后存放的文件夹
        System.out.println("############"+files);

        mPost.url(url)
                .files("files",files)
                .build()
                .execute(callback);


    }
}


