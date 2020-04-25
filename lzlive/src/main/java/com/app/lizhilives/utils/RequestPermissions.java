package com.app.lizhilives.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;


import com.app.lizhilives.R;

import java.util.Arrays;
import java.util.List;


/**
 * Created by admin on 2016/8/18.
 */
public class RequestPermissions {
    public static int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 读写权限申请 true 有权限  false 无权限
     *
     * @param mContext
     */
    public static void writeExternalStorage(final Context mContext, String[] PERMISSIONS_STORAGE, OnPermissionCallBack mCallBack) {
        RequestPermissions.PERMISSIONS_STORAGE = PERMISSIONS_STORAGE;

        int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        (Activity) mContext,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            else {
                mCallBack.setOnPermissionListener(false);
            }

        } else {
            mCallBack.setOnPermissionListener(true);
        }
    }

    /**
     * 读写权限申请 true 有权限  false 无权限
     *
     * @param mContext
     */
    public static void writeExternalStorage(final Context mContext, String[] PERMISSIONS_STORAGE, OnPermissionCallBack mCallBack, int requestCode) {
        RequestPermissions.PERMISSIONS_STORAGE = PERMISSIONS_STORAGE;
        int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ActivityCompat.requestPermissions(
                        (Activity) mContext,
                        PERMISSIONS_STORAGE,
                        requestCode
                );
            else {
                mCallBack.setOnPermissionListener(false);
            }

        } else {
            mCallBack.setOnPermissionListener(true);
        }
    }

    /**
     * 权限申请回调，需要在你调用的Activity中onRequestPermissionsResult调用该方法   true 申请权限成功  false申请权限失败
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, OnPermissionCallBack mCallBack) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCallBack.setOnPermissionListener(true);
            } else {
                mCallBack.setOnPermissionListener(false);
            }
            return;
        }
    }

    /**
     * 6.0以下申请打开权限管理
     *
     * @param mContext
     */
    public static void openPre(final Context mContext) {
//        RemindDialogUtil.showRemindDialog(mContext, "當前無權限，將無法進行QQ分享和應用升級~,是否打开设置？", new RemindDialogUtil.DialogCallBack() {
//            @Override
//            public void clickYes() {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
//                mContext.startActivity(intent);
//                RemindDialogUtil.hideRemindDialog();
//            }
//
//            @Override
//            public void clickCancel() {
//                RemindDialogUtil.hideRemindDialog();
//            }
//        });
        checkDeniedPermissionsNeverAskAgain(mContext,
                mContext.getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, Arrays.asList(PERMISSIONS_STORAGE));
    }

    public static boolean checkDeniedPermissionsNeverAskAgain(final Object object,
                                                              String rationale,
                                                              @StringRes int positiveButton,
                                                              @StringRes int negativeButton,
                                                              @Nullable DialogInterface.OnClickListener negativeButtonOnClickListener,
                                                              List<String> deniedPerms) {
        boolean shouldShowRationale;
        for (String perm : deniedPerms) {
            shouldShowRationale = shouldShowRequestPermissionRationale(object, perm);
            if (!shouldShowRationale) {
                final Activity activity = getActivity(object);
                if (null == activity) {
                    return true;
                }

                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setMessage(rationale)
                        .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                intent.setData(uri);
                                startAppSettingsScreen(object, intent);
                            }
                        })
                        .setNegativeButton(negativeButton, negativeButtonOnClickListener)
                        .create();
                dialog.show();

                return true;
            }
        }

        return false;
    }

    @TargetApi(11)
    private static void startAppSettingsScreen(Object object,
                                               Intent intent) {
        if (object instanceof Activity) {
            ((Activity) object).startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);
        } else if (object instanceof Fragment) {
            ((Fragment) object).startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @TargetApi(11)
    private static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    @TargetApi(23)
    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    public interface OnPermissionCallBack {
        void setOnPermissionListener(Boolean bo);
    }
}
