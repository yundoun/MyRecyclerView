package com.example.myrecyclerview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRecyclerView();

    }

    private void setRecyclerView() {
        RvAdapter adapter = new RvAdapter(position -> {
            String text = position == -1 ? "" : "체크된 항목 : " + (position + 1);
            binding.tvChecked.setText(text);
        });

        binding.myRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.myRecyclerView.setAdapter(adapter);

        List<Integer> numbers;
        numbers = integerList();
        adapter.submitList(numbers);
    }

    private List<Integer> integerList() {
        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 500; i++) {
            numbers.add(i);
        }
        return numbers;
    }
}