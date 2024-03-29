package com.example.massenger_application.VoiceCall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.massenger_application.R;
import com.zegocloud.uikit.ZegoUIKit;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

import timber.log.Timber;

public class Voice_Call_Activity extends AppCompatActivity {

    private ImageView speaker,mic,callEndedBtn;
    private boolean isMicEnabled= true;
    private boolean isSpeakerEnabled= true;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_call);

        speaker = findViewById(R.id.voice_call_speaker);
        mic = findViewById(R.id.voice_call_mic);
        callEndedBtn = findViewById(R.id.voice_call_endedBtn);

        mic.setOnClickListener(v -> {
            if (isMicEnabled){
                //mic is on
                mic.setImageDrawable(getDrawable(R.drawable.baseline_mic_24));
                isMicEnabled = false;
                Toast.makeText(this, "Mic Off", Toast.LENGTH_SHORT).show();
            }else {
                // mic is off
                mic.setImageDrawable(getDrawable(R.drawable.baseline_mic_off_24));
                isMicEnabled = true;
                Toast.makeText(this, "Mic On", Toast.LENGTH_SHORT).show();
            }
        });
        speaker.setOnClickListener(v -> {
            if (isSpeakerEnabled){
                // speaker is on
                isSpeakerEnabled = false;
                speaker.setBackgroundResource(R.drawable.mic_round_color_bg);
                Toast.makeText(this, "Speaker On", Toast.LENGTH_SHORT).show();
            }else {
                // speaker is off
                Toast.makeText(this, "Speaker Off", Toast.LENGTH_SHORT).show();
                speaker.setBackgroundResource(R.drawable.mic_transparent_bg);
                isSpeakerEnabled = true;
            }
        });
        callEndedBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Call Ended", Toast.LENGTH_SHORT).show();
            finish();
        });

//        Intent intent = getIntent();
//        String targetedId = intent.getStringExtra("otherUserId");
//        Timber.tag("MyApp").e("uid" + targetedId);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }
}