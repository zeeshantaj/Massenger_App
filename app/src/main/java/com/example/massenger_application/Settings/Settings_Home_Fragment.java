package com.example.massenger_application.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.massenger_application.FragmentUtil.FragmentUtils;
import com.example.massenger_application.R;

public class Settings_Home_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.setting_home_fragment, container, false);
    }

    private ConstraintLayout profileBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileBtn = view.findViewById(R.id.settingProfileLay);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.setFragment(fragmentManager,R.id.settingsFrameLay,new Setting_Profile_Fragment());
            }
        });
    }
}
