package com.example.myrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder>{

    private List<Integer> numbers;
    private Context context;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION; // 초기에는 선택된 아이템이 없음을 의미합니다.


    public RvAdapter(Context context, List<Integer> numbers, OnItemClickListener listener) {
        this.context = context;
        this.numbers = numbers;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);

        return new RvAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.MyViewHolder holder, int position) {
        holder.numText.setText(String.valueOf(numbers.get(position)));
        holder.itemView.setBackgroundColor(selectedPosition == position ? Color.LTGRAY : Color.TRANSPARENT); // 선택된 아이템의 배경색 변경

        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedPosition); // 이전 선택 해제
            selectedPosition = position; // 선택 위치 업데이트
            notifyItemChanged(selectedPosition); // 새 선택 적용
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView numText;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            numText = itemView.findViewById(R.id.numText);

        }
    }
}
