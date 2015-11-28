package com.sadhus.ezmoney.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.sadhus.ezmoney.activities.core.MainActivity;

/**
 * Created by charlie on 29/11/15.
 */
public class ModjadjiClientUtil {

    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) MainActivity.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();

        if (TextUtils.isEmpty(deviceId) || deviceId.equals("000000000000000")) {
            //first two case(deviceId null or length), but if it does we fallback to android id
            //last case of 000000000000000 happens when you run the app via a simulator
            deviceId = Settings.Secure.getString(MainActivity.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return deviceId;
    }
}
