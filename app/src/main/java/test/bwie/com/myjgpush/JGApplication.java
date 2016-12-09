package test.bwie.com.myjgpush;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.jpush.android.api.JPushInterface;

public class JGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID+"=584a6eef");
    }
}
