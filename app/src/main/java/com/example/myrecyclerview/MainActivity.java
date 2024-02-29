package com.example.myrecyclerview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        RvAdapter adapter = new RvAdapter(this, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TextView tvChecked = findViewById(R.id.tvChecked);
                tvChecked.setText("체크된 항목 : " + (position + 1));
            }
        });

        recyclerView.setAdapter(adapter);

        List<Integer> numbers = new ArrayList<>();

        for(int i=1; i<=500; i++){
            numbers.add(i);
        }
        adapter.submitList(numbers);
    }
}