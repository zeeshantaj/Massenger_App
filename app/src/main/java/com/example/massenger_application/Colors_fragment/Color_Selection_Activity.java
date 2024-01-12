package com.example.massenger_application.Colors_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        Toast.makeText(this, "Selected color: " + color, Toast.LENGTH_SHORT).show();
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
                ContextCompat.getColor(this, R.color.card27)
        );
//        colorList.add(R.color.card1);
//        colorList.add(R.color.card2);
//        colorList.add(R.color.card2);
//        colorList.add(R.color.card4);
//        colorList.add(R.color.card5);
//        colorList.add(R.color.card6);
//        colorList.add(R.color.card7);
//        colorList.add(R.color.card8);
//        colorList.add(R.color.card9);
//        colorList.add(R.color.card10);
//        colorList.add(R.color.card11);
//        colorList.add(R.color.card12);
//        colorList.add(R.color.card13);
//        colorList.add(R.color.card14);
//        colorList.add(R.color.card15);
//        colorList.add(R.color.card16);
//        colorList.add(R.color.card17);
//        colorList.add(R.color.card18);
//        colorList.add(R.color.card19);
//        colorList.add(R.color.card20);
//        colorList.add(R.color.card21);
//        colorList.add(R.color.card22);
//        colorList.add(R.color.card23);
//        colorList.add(R.color.card24);
//        colorList.add(R.color.card25);
//        colorList.add(R.color.card26);
//        colorList.add(R.color.card27);

        ColorAdapter colorAdapter = new ColorAdapter(colorList, this);
        recyclerView.setAdapter(colorAdapter);
    }
}