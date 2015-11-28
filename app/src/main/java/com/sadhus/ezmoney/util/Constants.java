package com.sadhus.ezmoney.util;


import android.util.Base64;

import java.util.regex.Pattern;

/**
 * Created by charlie on 28/11/15.
 */
public class Constants {

    public static final String ERROR_MANDATORY = "This field is mandatory";
    public static final String ERROR_INVALID = "This entered data is invalid";
    public static final String ERROR_OOPS = "Oops. Something went wrong. Contact Support.";

    public static final String SERVER_URL = "https://api.sit.modjadji.org:8243/TipsGo/v1.0.0/api/";
    public static final String API_REGISTER_WALLET = "Wallet/RegisterWallet";


    public final static String API_KEY = "_gZLgUuKtMfLvAzLfm3fBvYZ7wsa";
    public final static String API_SECRET = "lidep6lFCwRnvlVmCVAPDsaSfH8a";
    public final static String BASIC_TOKEN = Base64.encodeToString((API_KEY + ":" + API_SECRET).getBytes(), Base64.DEFAULT);

    //public final static String ACCESS_TOKEN = "cf817f6f23e08723b2fc48a29cf8a14";
    public final static String ACCESS_TOKEN = "xlda4X4f_jlGHD5b0FgHxxEc6hQa";

    public final static String AUTHORIZATION_HEADER = "Bearer " + ACCESS_TOKEN;


    public final static String CONSUMER_WALLET_TYPE = "Consumer";
    public final static String API_SUCCESS_CODE = "000";
    public final static String DEFAULT_PAYMENT_PIN = "123456";
    public final static String APP_ID = "SmartPayAndroid";

    public static final String PAYMENT_NOTIFICATION_RECEIVED = "paymentNotificationReceived";
    public static final String PAYMENT_MESSAGE = "paymentMessage";
    public static final String PAYMENT_TOKEN = "paymentToken";
    public static final String PAYMENT_CONFIRMATION = "paymentConfirmation";
    public static final String PAYMENT_CONFIRMATION_RESULT = "paymentConfirmationResult";


    public static final Pattern EMAIL_REGEX = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


}
