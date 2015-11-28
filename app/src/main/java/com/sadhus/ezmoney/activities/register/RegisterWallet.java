package com.sadhus.ezmoney.activities.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.JsonObject;
import com.sadhus.ezmoney.R;
import com.sadhus.ezmoney.activities.core.MainActivity;
import com.sadhus.ezmoney.util.Constants;
import com.sadhus.ezmoney.util.ModjadjiClientUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlie on 28/11/15.
 */
public class RegisterWallet extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "RegWallet";

    private EditText mFname;
    private EditText mLname;
    private EditText mDOB;
    private String mGender;
    private EditText mEmail;
    private EditText mDisplayName;
    private String mCustomerType;
    private Button registerButton;
    private View mProgressView;
    private View mLoginFormView;
    private UserSignupTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register_wallet);

        mFname = (EditText) findViewById(R.id.register_wallet_fname);
        mLname = (EditText) findViewById(R.id.register_wallet_lname);
        mDOB = (EditText) findViewById(R.id.register_wallet_dob);
        mEmail = (EditText) findViewById(R.id.register_wallet_email);
        mDisplayName = (EditText) findViewById(R.id.register_wallet_display_name);
        registerButton = (Button) findViewById(R.id.register_wallet_register_button);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });


    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.register_wallet_gender_male:
                if (checked) {
                    mGender = "Male";
                }
                break;
            case R.id.register_wallet_gender_female:
                if (checked) {
                    mGender = "Female";
                }
                break;
            case R.id.register_wallet_gender_others:
                if (checked) {
                    mGender = "Others";
                }
                break;
            case R.id.register_wallet_user_customer:
                if (checked) {
                    mCustomerType = "consumer";
                }
                break;
            case R.id.register_wallet_user_merchant:
                if (checked) {
                    mCustomerType = "merchant";
                }
                break;
        }
    }

    private boolean isEmailValid(String email) {
        return Constants.EMAIL_REGEX.matcher(email).matches();
    }


    private void attemptRegister() {

        if (mAuthTask != null) {
            return;
        }

        mEmail.setError(null);

        String fName = mFname.getText().toString();
        String lName = mLname.getText().toString();
        String email = mEmail.getText().toString();
        String dob = mDOB.getText().toString();
        String displayName = mDisplayName.getText().toString();
        String gender = mGender;
        String customerType = mCustomerType;
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(fName)) {
            mFname.setError(Constants.ERROR_MANDATORY);
            focusView = mFname;
            cancel = true;
        }

        if (TextUtils.isEmpty(lName)) {
            mLname.setError(Constants.ERROR_MANDATORY);
            focusView = mLname;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmail.setError(Constants.ERROR_MANDATORY);
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(Constants.ERROR_INVALID);
            focusView = mEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(dob)) {
            mDOB.setError(Constants.ERROR_MANDATORY);
            focusView = mDOB;
            cancel = true;
        }

        if (TextUtils.isEmpty(displayName)) {
            mDisplayName.setError(Constants.ERROR_MANDATORY);
            focusView = mDisplayName;
            cancel = true;
        }


        String pushId = "";
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            pushId = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(TAG, "GCM Registration Token: " + pushId);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_OOPS, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserSignupTask(fName, lName, dob, gender, displayName, email, pushId, customerType);
            mAuthTask.execute((Void) null);
        }


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        /*while ( !cursor.isAfterLast() ) {
            emails.add( cursor.getString( ProfileQuery.ADDRESS ) );
            cursor.moveToNext();
        }

        addEmailsToAutoComplete( emails );*/

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    public class UserSignupTask extends AsyncTask<Void, Void, Boolean> {

        private final String mFname;
        private final String mLname;
        private final String mDob;
        private final String mGender;
        private final String mDisplayName;
        private final String mEmail;
        private final String mPushId;
        private final String mWalletType;

        private SharedPreferences prefs = getSharedPreferences("ezMoneyPreferences", MODE_PRIVATE);

        //fName, lName, dob,  gender, displayName, email, pushId, customerType
        UserSignupTask(String fname, String lName, String dob, String gender, String displayName, String email, String pushId, String customerType) {
            mFname = fname;
            mLname = lName;
            mEmail = email;
            mDob = dob;
            mGender = gender;
            mDisplayName = displayName;
            mPushId = pushId;
            mWalletType = customerType;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JsonObject json = new JsonObject();
                json.addProperty("FirstName", mFname);
                json.addProperty("Surname", mLname);
                json.addProperty("DateOfBirth", mDob);
                json.addProperty("Gender", mGender);
                json.addProperty("DisplayName", mDisplayName);
                json.addProperty("Email", mEmail);
                json.addProperty("PushID", mPushId);
                json.addProperty("WalletType", mWalletType);

                ModjadjiClientUtil client = new ModjadjiClientUtil();
                String deviceId = ModjadjiClientUtil.getDeviceId();


                Log.i(TAG, "json: " + json + "");

                HttpURLConnection conn = null;
                if (isNetworkAvailable()) {
                    try {
                        URL url = new URL(Constants.SERVER_URL + Constants.SERVER_URL + Constants.API_REGISTER_WALLET);
                        Log.i(TAG, "Posting to " + url);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setRequestProperty("DeviceId", deviceId);
                        conn.setRequestProperty("AppId", Constants.APP_ID);
                        conn.setRequestProperty("Authorization", Constants.AUTHORIZATION_HEADER);
                        OutputStream out = conn.getOutputStream();
                        out.write(json.toString().getBytes());
                        out.close();
                        int status = conn.getResponseCode();
                        if (status != 200) {
                            throw new IOException("POST failed with error code " + status);
                        } else {
                            // Get the server response
                            BufferedReader reader;
                            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line = null;

                            while ((line = reader.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            Log.d(TAG, "POST passed" + status + "");
                            Log.d(TAG, "HTTP RESPONSE" + sb.toString());

                            try {
                                JSONObject jsonResponse = new JSONObject(sb.toString());
                                if (Integer.parseInt(jsonResponse.getString("Status")) == 999) {
                                    return false;
                                } else if (Integer.parseInt(jsonResponse.getString("Status")) == 0) {
                                    SharedPreferences.Editor editor = prefs.edit();
                                    String installationId = jsonResponse.getJSONObject("DataObject").getString("InstallationID");
                                    Log.i(TAG, "Installation ID rcvd: " + installationId);
                                    editor.putString("InstallationID", installationId);
                                    return true;
                                }

                            } catch (JSONException jExcept) {
                                jExcept.printStackTrace();
                                return false;
                            }
                        }
                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return false;


        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

                // TODO: Consider shifting to the DB for Person Details
                // FIXME: Encrypt strings
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("fname", mFname);
                editor.putString("lname", mLname);
                editor.putString("email", mEmail);

                editor.putBoolean("isOtpVerified", false);

                //editor.putBoolean( "isLoggedIn", true );
                editor.apply();
                //Toast.makeText( getApplicationContext(), "Signed in as " + mEmail, Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), Constants.ERROR_OOPS, Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }



}
