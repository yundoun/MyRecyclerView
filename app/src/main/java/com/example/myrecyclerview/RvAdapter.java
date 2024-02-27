package com.example.myrecyclerview;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder>{

    private List<Integer> numbers;
    private Context context;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION; // 초기에는 선택된 항목이 없으므로 RecyclerView.NO_POSITION 사용


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

        return new RvAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.MyViewHolder holder, int position) {
        holder.numText.setText(String.valueOf(numbers.get(position)));

        // 선택된 항목에 따라 CardView의 배경색을 변경합니다.
        holder.cardView.setCardBackgroundColor(selectedPosition == position ? Color.LTGRAY : Color.WHITE);

        holder.itemView.setOnClickListener(v -> {
            // 현재 아이템의 위치를 동적으로 조회
            int currentPosition = holder.getBindingAdapterPosition();

            if (currentPosition != RecyclerView.NO_POSITION) {
                if (selectedPosition == currentPosition) {
                    // 같은 아이템을 다시 클릭했을 경우 선택 해제
                    selectedPosition = RecyclerView.NO_POSITION;
                } else {
                    // 새로운 아이템을 선택
                    selectedPosition = currentPosition;
                }
                notifyDataSetChanged(); // 전체 데이터셋이 변경되었다고 알립니다. 개별 아이템 변경으로 최적화 가능
                listener.onItemClick(currentPosition);
            }
        });
    }


    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView numText;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numText = itemView.findViewById(R.id.numText);
            cardView =  itemView.findViewById(R.id.cardView);
        }
    }
}
