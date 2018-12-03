package com.mcc.schoolmarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.mcc.adpter.GridImageAdapter;
import com.mcc.adpter.InfoReleasePopupAdapter;
import com.mcc.data.ItemConfigEntity;
import com.mcc.schoolmarket.databinding.ActivityReleaseGoodBinding;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.ReleaseGoodVM;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;
import com.yuyh.library.imgsel.common.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReleaseGoodActivity extends BaseActivity {
    private List<String> imgVid;//本地图片地址
    private GridImageAdapter adapter;
    private ActivityReleaseGoodBinding binding;
    private ReleaseGoodVM vm;
    private List<ItemConfigEntity> classes=new ArrayList<>();
    private Dialog dialog;
    private LayoutInflater inflater;
    private Window dialogwindow;
    private  WindowManager.LayoutParams lp;
    private View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_release_good);
        int good_id=getIntent().getIntExtra("good_id",0);
        imgVid = new ArrayList<>();
        imgVid.add("camera_default");
        adapter=new GridImageAdapter(this,(ArrayList<String>) imgVid);
        binding.gvAlbum.setAdapter(adapter);
        init();
        if(good_id!=0){
            vm = new ReleaseGoodVM(mContext,this,good_id);
        }else {
            vm = new ReleaseGoodVM(mContext, this);
        }
        binding.setVm(vm);
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom2));
        binding.gvAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if ("camera_default".equals(imgVid.get(position))) {
                    //选择图片
                    choosePhoto();
                }else {
                    builder.setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    if (Constant.imageList != null && Constant.imageList.size() != 0
                                            &&Constant.imageList.contains(imgVid.get(position))) {
                                        Constant.imageList.remove(position);
                                    }
                                    imgVid.remove(position);
                                    if (!imgVid.contains("camera_default")) {
                                        imgVid.add("camera_default");
                                    }
                                    adapter.notifyDataSetChanged();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }).show();
                }
            }
        });
        inflater=LayoutInflater.from(this);
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        dialogwindow = dialog.getWindow();
        dialogwindow.setGravity(Gravity.BOTTOM);
        lp = dialogwindow.getAttributes();
        v = inflater.inflate(R.layout.dialog_layout, null);
        dialog.setContentView(v);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogwindow.setAttributes(lp);
    }
    private void choosePhoto(){
        ImageLoader loader=new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
               /* Bitmap bitmap= MyUtil.getLoacalBitmap(path);
                imageView.setImageBitmap(bitmap);*/
                Glide.with(context).load(path).into(imageView);
            }
        };
        ImgSelConfig config=new ImgSelConfig.Builder(mContext,loader)
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(true)
                // “确定”按钮背景色
                //.btnBgColor(Color.RED)
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#3F51B5"))
                // 裁剪大小。needCrop为true的时候配置
                //.cropSize(1, 1, 1000, 1000)
                //.needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(6)
                .build();
        Constant.imageList.clear();
        List<String> newImageList = new ArrayList<>();
        newImageList.addAll(imgVid);
        if (newImageList.contains("camera_default")) {
            newImageList.remove("camera_default");
        }
        Constant.imageList.addAll(newImageList);
        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, 1);

    }
    public void addClasses(List<ItemConfigEntity> list){
        classes.addAll(list);
    }
    public void showClassWindow(){
        dialog.show();
        Button close = (Button) v.findViewById(R.id.close);
        ListView lv = (ListView) v.findViewById(R.id.lv_classify);
        InfoReleasePopupAdapter infoReleasePopupAdapter3 = new InfoReleasePopupAdapter(this, classes);
        lv.setAdapter(infoReleasePopupAdapter3);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vm.getGood().setClass_id(classes.get(position).getId());
                dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);

            imgVid.remove("camera_default");
            for (int i = 0; i < pathList.size(); i++) {
                if (!imgVid.contains(pathList.get(i))) {
                    imgVid.add(pathList.get(i));
                }
            }
            if (imgVid.size() < 6) {
                imgVid.add("camera_default");
            }
            adapter.setData((ArrayList<String>) imgVid);
            adapter.notifyDataSetChanged();
        }
    }

    public List<String> getImgVid() {
        return imgVid;
    }
    public void setImgVid(List<String> list){
        imgVid.clear();
        imgVid.addAll(list);
        if(imgVid.size()<6){
            imgVid.add("camera_default");
            adapter.setData((ArrayList<String>) imgVid);
            adapter.notifyDataSetChanged();
        }
    }
    public void finishOK(){
        setResult(RESULT_OK);
        finish();
    }

}
