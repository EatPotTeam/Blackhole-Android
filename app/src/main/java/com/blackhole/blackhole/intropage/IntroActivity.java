package com.blackhole.blackhole.intropage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.blackhole.blackhole.R;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(
                getString(R.string.intro_title_1),
                getString(R.string.intro_description_1),
                R.drawable.intro_1,
                ContextCompat.getColor(this, R.color.colorPrimary)
        ));
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.intro_title_2),
                getString(R.string.intro_description_2),
                R.drawable.intro_2,
                ContextCompat.getColor(this, R.color.colorPrimary)
        ));
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.intro_title_3),
                getString(R.string.intro_description_3),
                R.drawable.intro_3,
                ContextCompat.getColor(this, R.color.colorPrimary)
        ));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
