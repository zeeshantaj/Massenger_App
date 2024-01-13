package com.example.massenger_application.Colors_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.massenger_application.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Color_Selection_Activity extends AppCompatActivity implements ColorAdapter.OnColorItemClickListener{
    private RecyclerView recyclerView;
    private List<Integer> colorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_selection);

        recyclerView = findViewById(R.id.colorRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        setRecyclerView();
    }
    @Override
    public void onColorItemClick(int color) {
        setChatColor(color);
        Toast.makeText(this, "Color Selected : ", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void setRecyclerView(){
        colorList = Arrays.asList(
                ContextCompat.getColor(this, R.color.card1),
                ContextCompat.getColor(this, R.color.card2),
                ContextCompat.getColor(this, R.color.card3),
                ContextCompat.getColor(this, R.color.card4),
                ContextCompat.getColor(this, R.color.card5),
                ContextCompat.getColor(this, R.color.card6),
                ContextCompat.getColor(this, R.color.card7),
                ContextCompat.getColor(this, R.color.card8),
                ContextCompat.getColor(this, R.color.card9),
                ContextCompat.getColor(this, R.color.card10),
                ContextCompat.getColor(this, R.color.card11),
                ContextCompat.getColor(this, R.color.card12),
                ContextCompat.getColor(this, R.color.card13),
                ContextCompat.getColor(this, R.color.card14),
                ContextCompat.getColor(this, R.color.card15),
                ContextCompat.getColor(this, R.color.card16),
                ContextCompat.getColor(this, R.color.card17),
                ContextCompat.getColor(this, R.color.card18),
                ContextCompat.getColor(this, R.color.card19),
                ContextCompat.getColor(this, R.color.card20),
                ContextCompat.getColor(this, R.color.card21),
                ContextCompat.getColor(this, R.color.card22),
                ContextCompat.getColor(this, R.color.card23),
                ContextCompat.getColor(this, R.color.card24),
                ContextCompat.getColor(this, R.color.card25),
                ContextCompat.getColor(this, R.color.card26),
                ContextCompat.getColor(this, R.color.card27),
                ContextCompat.getColor(this, R.color.white)
        );
        ColorAdapter colorAdapter = new ColorAdapter(colorList, this);
        recyclerView.setAdapter(colorAdapter);
    }

    private void setChatColor(int color){
        Intent intent = getIntent();
        String chatId = intent.getStringExtra("chatId");
        SharedPreferences sharedPreferences = getSharedPreferences("colorChangerPreference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("chatId",chatId);
        editor.putInt("color",color);
        editor.apply();
    }
}