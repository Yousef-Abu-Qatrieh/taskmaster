package com.example.firsttry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerView extends RecyclerView.Adapter<CustomRecyclerView.CustomViewHolder> {
    List<Task> dataList;
    CustomClickListener listener;

    public CustomRecyclerView(List<Task> dataList,CustomClickListener listener) {
        this.dataList = dataList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView=layoutInflater.inflate(R.layout.activity_task,parent,false);
        return new CustomViewHolder(listItemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
holder.taskTitle.setText(dataList.get(position).getTitle());
holder.taskBody.setText(dataList.get(position).getBody());
holder.taskStatus.setText(dataList.get(position).getState());
    }

    @Override
    public int getItemCount() {
       return dataList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitle ;
        TextView taskBody ;
        TextView taskStatus;
        CustomClickListener listener;
        public CustomViewHolder(@NonNull View itemView,CustomClickListener listener) {
            super(itemView);
            this.listener=listener;
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskBody = itemView.findViewById(R.id.taskBody);
            taskStatus = itemView.findViewById(R.id.taskStatus);
            itemView.setOnClickListener(view -> listener.onWeatherItemClicked(getAdapterPosition()));
        }
    }
    public interface CustomClickListener {
        void onWeatherItemClicked(int position);
    }
}


