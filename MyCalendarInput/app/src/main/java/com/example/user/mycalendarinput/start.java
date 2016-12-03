package com.example.user.mycalendarinput;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class start extends AppCompatActivity {
    final String TAG = "AnimationTest";
    ImageView logo;
    int mScreenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        intent.getStringArrayExtra("forcheck");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        logo = (ImageView) this.findViewById(R.id.logo);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;
        startLogoAnimation();

    }

    private void startLogoAnimation() {
        Animation logo_anim = AnimationUtils.loadAnimation(this, R.anim.logo);
        logo.startAnimation(logo_anim);
        logo_anim.setAnimationListener(animationListener);
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Log.i(TAG, "onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.i(TAG, "onAnimationEnd");
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.i(TAG, "onAnimationRepeat");
        }

    };


}
