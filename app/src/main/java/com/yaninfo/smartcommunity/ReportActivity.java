package com.yaninfo.smartcommunity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yaninfo.smartcommunity.adapter.ImagePickerAdapter;
import com.yaninfo.smartcommunity.uploadEvent.BitmapUtils;
import com.yaninfo.smartcommunity.uploadEvent.GlideImageLoader;
import com.yaninfo.smartcommunity.uploadEvent.SelectDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 事件上报
 */
public class ReportActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    // 当前选择的所有图片
    private ArrayList<ImageItem> selImageList;
    private EditText editText;
    // 允许选择图片最大数
    private int maxImgCount = 8;
    private int imageNum = 0;
    private int uploadImage = 0;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                uploadImage++;
                if (imageNum <= uploadImage) {
                    uploadMessage();
                }
            }
            return false;
        }
    });

    private void uploadMessage() {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_report);

        editText = findViewById(R.id.editText);
        String getEditText = editText.getText().toString();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                uploadImage(selImageList);

            }
        });

        //最好放到 Application oncreate执行
        initImagePicker();
        initWidget();

    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   // 设置图片加载器
        imagePicker.setShowCamera(true);                      // 显示拍照按钮
        imagePicker.setCrop(true);                            // 允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   // 是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              // 选中数量限制
        imagePicker.setMultiMode(false);                      // 多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
        imagePicker.setFocusWidth(800);                       // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         // 保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         // 保存文件的高度。单位像素
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(ReportActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                // 打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(ReportActivity.this, ImageGridActivity.class);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }
                    }
                }, names);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    private void uploadImage(ArrayList<ImageItem> pathList) {

        Map<String, File> files = new HashMap<>();
        for (int i = 0; i < pathList.size(); i++) {
            String newPath = BitmapUtils.compressImageUpload(pathList.get(i).path);
            files.put(pathList.get(i).name + i, new File(newPath));
            bitmap = getBitMap(newPath);
            new MyThread().start();
        }

        // 打印缓存之后存放的文件夹
        System.out.println("############" + files);

    }

    Bitmap bitmap = null;

    /**
     * 上传线程
     */
    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Socket s = new Socket("192.168.94.110", 30003);
                OutputStream os = s.getOutputStream();
                //将图片bitmap转换成字节数组
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                os.write(data);
                //刷新缓冲区
                os.flush();
                os.close();
                handler.sendEmptyMessage(1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据路径转化为Bitmap对象
     *
     * @param pathString
     * @return
     */
    private Bitmap getBitMap(String pathString) {
        Bitmap bitmap = null;
        File file = new File(pathString);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(pathString);
        }
        return bitmap;
    }


}
