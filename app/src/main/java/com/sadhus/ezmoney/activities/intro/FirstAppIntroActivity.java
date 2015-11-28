package com.sadhus.ezmoney.activities.intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.sadhus.ezmoney.R;

/**
 * Created by charlie on 28/11/15.
 */
public class FirstAppIntroActivity extends AppIntro {


    @Override
    public void init(Bundle bundle) {
        addSlide(AppIntroFragment.newInstance("ezMoney",
                "Powerd by Salesforce",
                R.drawable.page1, Color.parseColor("#3F51B5")));

        addSlide(AppIntroFragment.newInstance("Transfer money across",
                "Powerd by Salesforce",
                R.drawable.page1, Color.parseColor("#3F51B5")));
    }


    @Override
    public void onSkipPressed() {
        Intent doneSlide = new Intent(getApplicationContext(), DoneSlide.class);
        doneSlide.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(doneSlide);
        finish();

    }

    @Override
    public void onDonePressed() {
        Intent doneSlide = new Intent(getApplicationContext(), DoneSlide.class);
        doneSlide.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(doneSlide);
        finish();

    }

}
