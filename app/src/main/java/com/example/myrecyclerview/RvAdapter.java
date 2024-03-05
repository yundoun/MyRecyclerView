package com.example.myrecyclerview;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    private final OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public RvAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.d("RvAdapter", "onViewAttachedToWindow: position = " + holder.getBindingAdapterPosition());
    }
    @Override
    public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("RvAdapter", "onViewDetachedFromWindow: position = " + holder.getBindingAdapterPosition());
    }
    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
        Log.d("RvAdapter", "onViewRecycled: position = " + holder.getBindingAdapterPosition());
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d("RvAdapter", "onAttachedToRecyclerView: position");
    }
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.d("RvAdapter", "onDetachedFromRecyclerView: position");
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
        RowBinding binding = RowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Log.d("RvAdapter", "onCreateViewHolder" );
        return new RvAdapter.MyViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull RvAdapter.MyViewHolder holder, int position) {
        boolean isSelected = selectedPosition == position;
        holder.bind(getItem(position), isSelected);

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getBindingAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                if (selectedPosition == currentPosition) {
                    // 현재 선택된 아이템을 다시 클릭했을 경우, 선택 해제 로직
                    notifyItemChanged(selectedPosition); // 이전 상태를 업데이트 (선택 해제를 위해)
                    selectedPosition = RecyclerView.NO_POSITION; // 선택 해제
                    listener.onItemClick(-1); // 선택 해제 상태를 외부로 알림
                } else {
                    // 새로운 아이템을 선택하는 경우
                    int previousSelectedPosition = selectedPosition;
                    selectedPosition = currentPosition;
                    // 이전 선택된 아이템과 새로 선택된 아이템을 업데이트
                    if (previousSelectedPosition >= 0) {
                        notifyItemChanged(previousSelectedPosition); // 이전 선택 해제
                    }
                    notifyItemChanged(selectedPosition); // 새로운 선택 표시
                    listener.onItemClick(currentPosition); // 새로운 선택 상태를 외부로 알림
                }
            }
        });
        Log.d("RvAdapter", "onBindViewHolder" );
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final RowBinding binding;

        public MyViewHolder(RowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Integer number, boolean isSelected) {
            binding.numText.setText(String.valueOf(number));
            binding.cardView.setCardBackgroundColor(isSelected ? Color.LTGRAY : Color.WHITE);
        }

    }
}
