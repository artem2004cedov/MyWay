package com.example.myway.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myway.Activity.AddNewTaskNo;
import com.example.myway.Activity.ComparisonsActivity;
import com.example.myway.Model.ToDoModel;
import com.example.myway.R;
import com.example.myway.Utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapterNecessary extends RecyclerView.Adapter<ToDoAdapterNecessary.ViewHolder> {

    private List<ToDoModel> todoList;
    private DatabaseHandler db;
    private ComparisonsActivity context;

    public ToDoAdapterNecessary(List<ToDoModel> todoList, DatabaseHandler db, ComparisonsActivity context) {
        this.todoList = todoList;
        this.db = db;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_eat, parent, false);
        return new ViewHolder(itemView);
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();
        final ToDoModel item = todoList.get(position);
        holder.textTaskEat.setText(item.getNecessary());
        holder.textTaskDate.setText(item.getDate());
        holder.todoCheckBoxEat.setChecked(toBoolean(item.getStatus_necessary()));

        holder.todoCheckBoxEat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatusNo(item.getId_no_table(), 1);
//                    holder.textTask.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.ANTI_ALIAS_FLAG);
                } else {
                    db.updateStatusNo(item.getId_no_table(), 0);
//                    holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.ANTI_ALIAS_FLAG);
                }
            }
        });


    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTasksNecessary(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItemNo(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTaskENo(item.getId_no_table());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItemNo(final int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id_no", item.getId_no_table());
        bundle.putString("no", item.getNo());
        AddNewTaskNo fragment = new AddNewTaskNo();
        fragment.setArguments(bundle);
        fragment.show(context.getSupportFragmentManager(), AddNewTaskNo.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox todoCheckBoxEat;
        TextView textTaskEat,textTaskDate;

        ViewHolder(View view) {
            super(view);
            todoCheckBoxEat = view.findViewById(R.id.todoCheckBoxEat);
            textTaskEat = view.findViewById(R.id.textTaskEat);
            textTaskDate = view.findViewById(R.id.textTaskDate);
        }
    }
}
