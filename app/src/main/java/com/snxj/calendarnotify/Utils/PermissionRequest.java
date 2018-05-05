/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snxj.calendarnotify.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

public class PermissionRequest {

    private Context mContext;
    private PermissionCallback mCallback;
    public static final int REQUEST_SETTING_DIALOG_CODE = 300;
    private int mSettingDialogCode;
    private boolean isShowSettingDialog = true;

    public PermissionRequest(Context context, PermissionCallback callback) {
        this(context, true, REQUEST_SETTING_DIALOG_CODE, callback);
    }

    public PermissionRequest(Context context, boolean isShowSettingDialog, PermissionCallback callback) {
        this(context, isShowSettingDialog, REQUEST_SETTING_DIALOG_CODE, callback);
    }

    public PermissionRequest(Context context, int settingDialogCode, PermissionCallback callback) {
        this(context, true, settingDialogCode, callback);
    }

    public PermissionRequest(Context context, boolean isShowSettingDialog, int settingDialogCode, PermissionCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.isShowSettingDialog = isShowSettingDialog;
        this.mSettingDialogCode = settingDialogCode;
    }

    public void request(String... permission) {
        AndPermission.with(mContext)
                .requestCode(110)
                .permission(permission)
                .callback(this)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(mContext, rationale).show();
                    }
                })
                .start();
    }

    @PermissionYes(110)
    public void yes(List<String> permissions) {
        realCallBack(permissions);
    }

    @PermissionNo(110)
    public void no(List<String> permissions) {
        realCallBack(permissions);
    }

    private void realCallBack(List<String> permissions) {
        if (AndPermission.hasPermission(mContext, permissions)) {
            this.mCallback.onPermisstionSuccessful();
        } else {
            if (AndPermission.hasAlwaysDeniedPermission((Activity) mContext, permissions) && isShowSettingDialog) {
                showSettingDialog();
            } else {
                this.mCallback.onPermisstionFailure();
            }
        }
    }

    private void showSettingDialog() {
        final SettingService settingService = AndPermission.defineSettingDialog((Activity) mContext, mSettingDialogCode);
        new AlertDialog.Builder(mContext)
                .setTitle("权限申请失败")
                .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingService.execute();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settingService.cancel();
                mCallback.onPermisstionFailure();
            }
        }).show();
    }

    public interface PermissionCallback {
        void onPermisstionSuccessful();

        void onPermisstionFailure();
    }

}
