package kz.kkurtukov.facebookuserlistexample;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.facebook.FacebookSdk;

/**
 * Created by kkurtukov on 10.12.2015.
 */
public class ApplicationLoader extends Application{

    public static volatile Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        initFacebookSdk();
        initActiveAndroid();
    }

    private void initFacebookSdk(){
        FacebookSdk.sdkInitialize(this);
    }

    private void initActiveAndroid(){
        ActiveAndroid.initialize(this);
    }
}
