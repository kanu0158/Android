package app.bit.com.contactsapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class PhoneUtil {
    private Context _this;
    private Activity act;
    private String phoneNum;

    public PhoneUtil(Context _this, Activity act) {
        this._this = _this;
        this.act = act;
    }
    public void dial(){
        _this.startActivity(
                new Intent(
                        Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNum)));
    }
    public void call(){
        Log.d("===============================1-1","=======================");
        Log.d("===============================1-2","=======================");
        if (ActivityCompat.checkSelfPermission(
                _this,
                Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
            Log.d("===============================1-3","=======================");
                ActivityCompat.requestPermissions(
                        act, new String[]{Manifest.permission.CALL_PHONE},
                        2);
            Log.d("===============================1-4","=======================");
        }
        _this.startActivity(
                 new Intent(
                        Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum)));

    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
