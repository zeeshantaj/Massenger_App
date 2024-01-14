package com.example.massenger_application.FragmentUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {
    public static void setFragment(FragmentManager fragmentManager, int fragmentContainer, Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainer,fragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }
}
