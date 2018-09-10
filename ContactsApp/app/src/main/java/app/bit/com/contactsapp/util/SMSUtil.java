package app.bit.com.contactsapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SMSUtil {
    private Context _this;
    private Activity act;
    private String phoneNum;

    public SMSUtil(Context _this, Activity act) {
        this._this = _this;
        this.act = act;
    }
    public void sms(){

    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
