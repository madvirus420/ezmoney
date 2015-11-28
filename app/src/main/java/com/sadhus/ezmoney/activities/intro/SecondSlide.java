package com.sadhus.ezmoney.activities.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sadhus.ezmoney.R;

/**
 * Created by charlie on 28/11/15.
 */
public class SecondSlide extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.app_intro_2, container, false);
        return v;
    }
}
