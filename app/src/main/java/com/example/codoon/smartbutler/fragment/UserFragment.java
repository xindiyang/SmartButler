package com.example.codoon.smartbutler.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codoon.smartbutler.R;
import com.example.codoon.smartbutler.entity.MyUser;
import com.example.codoon.smartbutler.ui.CourierACtivity;
import com.example.codoon.smartbutler.ui.LoginActivity;
import com.example.codoon.smartbutler.utils.L;
import com.example.codoon.smartbutler.utils.UtilTools;
import com.example.codoon.smartbutler.view.CustomDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.fragment
 * 文件名：UserFragment
 * 创建时间：2018/10/8 上午11:38
 * 描述：TODO
 */

public class UserFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private Button editor_btn, change_btn, login_out;
    private EditText name, sex, age, desc;
    private TextView logistics, loaction;
    //圆形头像
    private CircleImageView circleImageView;
    //弹出对话框
    private CustomDialog customDialog;
    private Button btn_camera, btn_picture, btn_cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        editor_btn = view.findViewById(R.id.editor_btn);
        change_btn = view.findViewById(R.id.change_btn);
        login_out = view.findViewById(R.id.login_out);
        circleImageView = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.name);
        sex = view.findViewById(R.id.sexs);
        age = view.findViewById(R.id.ages);
        desc = view.findViewById(R.id.introductions);
        logistics = view.findViewById(R.id.logistics_query);
        loaction = view.findViewById(R.id.loaction);
        logistics.setOnClickListener(this);
        loaction.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
        //拿到保存的图片，并读取图片
        UtilTools.getImagetoShare(getActivity(),circleImageView);
        editor_btn.setOnClickListener(this);
        customDialog = new CustomDialog(getActivity(), 100, 100,
                R.layout.dialog_photo, R.style.Theme_dialog, Gravity.BOTTOM, R.style.pop_anim_style);
        //屏幕外点击无效
        customDialog.setCancelable(false);
        btn_camera = customDialog.findViewById(R.id.taking_pictures);
        btn_picture = customDialog.findViewById(R.id.gallery);
        btn_cancel = customDialog.findViewById(R.id.cancel);
        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        //默认是不可点击的/不可输入
        setEnableFalse();
        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        name.setText(userInfo.getUsername());
        //三目运算
        sex.setText(userInfo.isSex() ? "男" : "女");
        age.setText(userInfo.getAge() + "");
        desc.setText(userInfo.getDesc());
        change_btn.setOnClickListener(this);
        login_out.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
                //头像选择
                customDialog.show();
                break;
            case R.id.cancel:
                customDialog.dismiss();
                break;
            case R.id.taking_pictures:
                toCamera();
                break;
            case R.id.gallery:
                toGallery();
                break;
            case R.id.login_out:
                //退出登录
                MyUser.logOut();   //清除缓存用户对象
                BmobUser myUser = MyUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.editor_btn:
                //编辑资料
                setEnableTrue();
                change_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.change_btn:
                //确定修改
                enterChange();
                break;
            case R.id.logistics_query:
                Intent intent = new Intent(getActivity(),CourierACtivity.class);
                startActivity(intent);
            default:
        }
    }

    /**
     * 跳转拍照
     */
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;

    private File tempFile = null;

    private void toCamera() {
        File apkFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "app_sample.apk");
        Uri apkUri = FileProvider.getUriForFile(getActivity(),
                "com.example.codoon.smartbutler.sunday", apkFile);
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        startActivity(installIntent);

        String filePath = Environment.getExternalStorageDirectory() + "/images/" + System.currentTimeMillis() + ".jpg";
        File outputFile = new File(filePath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdir();
        }
        Uri contentUri = FileProvider.getUriForFile(getActivity(),
                "com.example.codoon.smartbutler.sunday", outputFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        customDialog.dismiss();
    }

    /**
     * 跳转图库
     */
    private void toGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        customDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相册的返回数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机的返回数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), "images");
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍去
                    if (data != null) {
                        //拿到图片设置
                        setImagetoView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    } else {

                    }

                    break;
                default:
            }
        }
    }

    /**
     * 设置图片
     */
    private void setImagetoView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            circleImageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 裁剪图片
     */
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        } else {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            //设置裁剪
            intent.putExtra("crop", "true");
            //裁剪宽高
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //裁剪图片的分辨率
            intent.putExtra("outputX", 320);
            intent.putExtra("outputY", 320);
            //发送数据
            intent.putExtra("return-data", true);
            startActivityForResult(intent, RESULT_REQUEST_CODE);
        }
    }

    /**
     * 确定更改信息
     */
    private void enterChange() {
        //1.拿到输入框的值
        setEnableFalse();
        String username = name.getText().toString().trim();
        String sexs = sex.getText().toString();
        String ages = age.getText().toString();
        String descs = desc.getText().toString();
        //2.判断是否为空
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(sexs)) {
            //3.更新属性
            MyUser user = new MyUser();
            user.setUsername(username);
            if (sexs.equals("男")) {
                user.setSex(true);
            } else {
                user.setSex(false);
            }
            user.setAge(Integer.parseInt(ages));
            if (!descs.isEmpty()) {
                user.setDesc(descs);
            } else {
                user.setDesc("这个人很懒，什么都没有留下！");
            }

            BmobUser bmobUser = BmobUser.getCurrentUser();
            user.update(bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        //修改成功
                        setEnableFalse();
                        change_btn.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "修改失败!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), "姓名或性别输入框不能为空!", Toast.LENGTH_SHORT).show();
        }
    }

    //禁止点击(焦点)
    private void setEnableFalse() {
        name.setEnabled(false);
        sex.setEnabled(false);
        age.setEnabled(false);
        desc.setEnabled(false);
    }

    //允许点击(焦点)
    private void setEnableTrue() {
        name.setEnabled(true);
        sex.setEnabled(true);
        age.setEnabled(true);
        desc.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImagetoShare(getActivity(),circleImageView);
    }

}
