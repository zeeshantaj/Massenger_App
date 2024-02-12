package com.example.massenger_application.VoiceCall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.massenger_application.R;

public class Voice_Call_Activity extends AppCompatActivity {

    private ImageView speaker,mic,callEndedBtn;
    private boolean isMicEnabled= true;
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
                mic.setImageDrawable(getDrawable(R.drawable.baseline_mic_24));
                Log.e("MyApp","mic "+isMicEnabled);
                isMicEnabled = false;
            }else {
                mic.setImageDrawable(getDrawable(R.drawable.baseline_mic_off_24));
                Log.e("MyApp","mic "+isMicEnabled);
                isMicEnabled = true;
            }
        });
        callEndedBtn.setOnClickListener(v -> {
            finish();
        });
    }
}