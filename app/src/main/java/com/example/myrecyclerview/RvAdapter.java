package com.example.myrecyclerview;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
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

import com.example.myrecyclerview.databinding.RowBinding;

import java.util.List;

public class RvAdapter extends ListAdapter<Integer, RvAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;
    // 초기에는 선택된 항목이 없으므로 RecyclerView.NO_POSITION 사용

    public RvAdapter(Context context, OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Integer> DIFF_CALLBACK = new DiffUtil.ItemCallback<Integer>() {
        @Override
        public boolean areItemsTheSame(@NonNull Integer oldItem, @NonNull Integer newItem) {
            // 같은 아이템인지 확인
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Integer oldItem, @NonNull Integer newItem) {
            // 아이템의 내용이 같은지 확인
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowBinding binding = RowBinding.inflate(inflater, parent, false);
        return new RvAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.MyViewHolder holder, int position) {
        boolean isSelected = selectedPosition == position;
        holder.bind(getItem(position), isSelected);

        holder.itemView.setOnClickListener(v -> {
            // 현재 아이템의 위치를 동적으로 조회
            int currentPosition = holder.getBindingAdapterPosition();

            if (currentPosition != RecyclerView.NO_POSITION){
                notifyItemChanged(selectedPosition); // 이전 선택 해제
                // 배경색 변경하기 위한 아이템의 변경 알림
                if (selectedPosition == currentPosition){
                    selectedPosition = RecyclerView.NO_POSITION;
                    listener.onItemClick(-1);
                }else{
                    selectedPosition = currentPosition;
                    notifyItemChanged(selectedPosition);
                    listener.onItemClick(currentPosition);
                }
            }

        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private RowBinding binding;

        public MyViewHolder(RowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Integer number, boolean isSelected){
            binding.numText.setText(String.valueOf(number));
            binding.cardView.setCardBackgroundColor(isSelected ? Color.LTGRAY : Color.WHITE);
        }

    }
}
