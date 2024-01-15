package com.example.massenger_application.Settings;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.massenger_application.FragmentUtil.FragmentUtils;
import com.example.massenger_application.Interfaces.ImageUrlCallback;
import com.example.massenger_application.R;
import com.example.massenger_application.Utils.FirebaseUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings_Home_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.setting_home_fragment, container, false);
    }

    private ConstraintLayout profileBtn;
    private CircleImageView circleImageView;
    private TextView nameStr,statusStr;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileBtn = view.findViewById(R.id.settingProfileLay);
        circleImageView = view.findViewById(R.id.circleImageView);
        nameStr = view.findViewById(R.id.settingUserName);
        statusStr = view.findViewById(R.id.settingStatusTxt);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.setFragment(fragmentManager,R.id.settingsFrameLay,new Setting_Profile_Fragment());
            }
        });
        setProfileInfo(getActivity(),circleImageView,nameStr,statusStr);
    }
    public static void setProfileInfo(Context context, CircleImageView imageView, TextView nameStr, TextView statusStr){

        FirebaseUtils.getFirebaseName(new ImageUrlCallback() {
            @Override
            public void onImageUrlReady(String imageUrl) {
                Glide.with(context)
                        .load(imageUrl)
                        .into(imageView);
            }

            @Override
            public void onNameReady(String name) {
                nameStr.setText(name);
            }

            @Override
            public void onStatusReady(String status) {
                statusStr.setText(status);
            }
        });
    }
}
