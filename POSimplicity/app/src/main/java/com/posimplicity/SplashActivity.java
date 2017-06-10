package com.posimplicity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.TextUtils;

import com.Database.POSDatabaseHandler;
import com.Dialogs.LoginPopUp;
import com.PosInterfaces.PrefrenceKeyConst;
import com.Utils.MyPreferences;
import com.Utils.StartAndroidActivity;

import org.json.JSONArray;


public class SplashActivity extends BaseActivity {

    private final String MINT_ID = "b5f9f859";
    private POSDatabaseHandler dataBaseHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, false, this);
        setContentView(R.layout.activity_splash);

        long hours = MyPreferences.getLongPreferenceWithDiffDefValue(PrefrenceKeyConst.HOURS_OF_DAY, this);
        long mint = MyPreferences.getLongPreferenceWithDiffDefValue(PrefrenceKeyConst.MINUTES, this);

        if (hours < 0)
            MyPreferences.setLongPreferences(PrefrenceKeyConst.HOURS_OF_DAY, 3, this);

        if (mint < 0)
            MyPreferences.setLongPreferences(PrefrenceKeyConst.MINUTES, 0, this);

        dataBaseHandler = new POSDatabaseHandler(mContext);
        dataBaseHandler.openReadableDataBase();
        onInitViews();
        onListenerRegister();

        if (TextUtils.isEmpty(MyPreferences.getMyPreference(ANDROID_DEVICE_ID, mContext))) {
            String androidId = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
            MyPreferences.setMyPreference(ANDROID_DEVICE_ID, androidId, mContext);
        }

        globalApp.onScreenSize();
        String baseUrl = MyPreferences.getMyPreference(BASE_URL, mContext);
        String setupTime = MyPreferences.getMyPreference(SETUP_TIME, mContext);

        if (TextUtils.isEmpty(baseUrl))
            new LoginPopUp(mContext).onLoginDialog();
        else if (TextUtils.isEmpty(setupTime))
            startActivity(new Intent(mContext, SetupActivity.class));
        else {
            //StartAndroidActivity.onActivityStart(true, mContext, HomeActivity.class);
            StartAndroidActivity.onActivityStart(true, mContext, SyncOptionActivity.class);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHandler != null) {
            dataBaseHandler.close();
        }
    }


    @Override
    public void onInitViews() {
    }

    @Override
    public void onListenerRegister() {
    }

    @Override
    public void onDataRecieved(JSONArray arry) {
    }

    @Override
    public void onSocketStateChanged(int state) {
    }
}
