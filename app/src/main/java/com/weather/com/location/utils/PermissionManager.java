package com.weather.com.location.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;



public class PermissionManager {

    private static final int REQUEST_PERMISSIONS = 1337;

    public interface PermissionsResultCallback {
        void onPermissionGranted(List<String> permissions);

        void onPermissionDenied(List<String> permissions);

        void onPermissionAvailable(List<String> permissions);
    }

    private Activity activity;
    private PermissionsResultCallback callback;

    public PermissionManager(Activity activity, PermissionsResultCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void requestPermissions(String...permissions) {
        List<String> availablePermissions = new ArrayList<>();
        List<String> unavailablePermissions = new ArrayList<>();

        for(String permission : permissions) {
            if(ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                unavailablePermissions.add(permission);
            } else {
                availablePermissions.add(permission);
            }
        }

        if(unavailablePermissions.size() >0) {
            String[] permissionsArr = unavailablePermissions.toArray(new String[unavailablePermissions.size()]);
            ActivityCompat.requestPermissions(activity, permissionsArr, REQUEST_PERMISSIONS);
        }

        if(availablePermissions.size() >0) {
            callback.onPermissionAvailable(availablePermissions);
        }
    }

    public void requestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSIONS) {
            List<String> grantedPermissions = new ArrayList<>();
            List<String> deniedPermissions = new ArrayList<>();

            for(int i=0; i < permissions.length; i++) {
                String permission = permissions[i];

                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permission);
                }else {
                    deniedPermissions.add(permission);
                }
            }
            callback.onPermissionGranted(grantedPermissions);
            if(deniedPermissions.size()>0) {
                callback.onPermissionDenied(deniedPermissions);
            }
        }

    }

    public static boolean isPermissionAvailable(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

}
