package com.example.massenger_application.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import com.example.massenger_application.MainActivity;
import com.example.massenger_application.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    private static Fragment currentFragment;

    private static Button num, otp, setup;
    private int backPressCount;
    private SharedViewModel sharedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        num = findViewById(R.id.numberBtn);
        otp = findViewById(R.id.otpBtn);
        setup = findViewById(R.id.setupBtn);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(this, MainActivity.class));
        }
        else {
            fragmentManager = getSupportFragmentManager();
            currentFragment = fragmentManager.findFragmentById(R.id.login_frameLayout);
            currentFragment = new Number_Verification_Fragment();
            setFragment(currentFragment, this);
        }
    }
    public static void setColor(Context context) {

        Drawable drawableNumber = ContextCompat.getDrawable(context, R.drawable.number).mutate();
        Drawable drawableOTP = ContextCompat.getDrawable(context, R.drawable.otp).mutate();
        Drawable drawableSetup = ContextCompat.getDrawable(context, R.drawable.setup).mutate();

        int colorActive = ContextCompat.getColor(context, R.color.blue);
        int colorDefault = ContextCompat.getColor(context, R.color.black);

        if (currentFragment instanceof Number_Verification_Fragment) {
            setButtonDrawableTint(num, drawableNumber, colorActive);
            setButtonDrawableTint(otp, drawableOTP, colorDefault);
            setButtonDrawableTint(setup, drawableSetup, colorDefault);
        } else if (currentFragment instanceof OTP_Fragment) {
            setButtonDrawableTint(num, drawableNumber, colorDefault);
            setButtonDrawableTint(otp, drawableOTP, colorActive);
            setButtonDrawableTint(setup, drawableSetup, colorDefault);
        } else if (currentFragment instanceof Setup_Profile_Fragment) {
            setButtonDrawableTint(num, drawableNumber, colorDefault);
            setButtonDrawableTint(otp, drawableOTP, colorDefault);
            setButtonDrawableTint(setup, drawableSetup, colorActive);
        }
    }
    public static void setFragment(Fragment fragment, Context context) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.login_frameLayout, fragment);
        if (!(fragment instanceof Number_Verification_Fragment)) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
        currentFragment = fragment;
        setColor(context);
    }

    private static void setButtonDrawableTint(Button button, Drawable drawable, int color) {
        DrawableCompat.setTint(drawable, color);
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();

            // Increment back press count
            backPressCount++;

            if (backPressCount % 2 == 0) {
                currentFragment = new Number_Verification_Fragment();
            } else {
                currentFragment = new OTP_Fragment();
            }

            setFragment(currentFragment, this);
            setColor(this);
        } else {
            super.onBackPressed();
        }
    }
}