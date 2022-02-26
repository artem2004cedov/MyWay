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

import com.example.myway.Activity.AddNewTaskEat;
import com.example.myway.Activity.ComparisonsActivity;
import com.example.myway.Model.ToDoModel;
import com.example.myway.R;
import com.example.myway.Utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapterEat extends RecyclerView.Adapter<ToDoAdapterEat.ViewHolder> {

    private List<ToDoModel> todoList;
    private DatabaseHandler db;
    private ComparisonsActivity context;

    public ToDoAdapterEat(List<ToDoModel> todoList, DatabaseHandler db, ComparisonsActivity context) {
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

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();
        final ToDoModel item = todoList.get(position);
        holder.textTaskEat.setText(item.getEat());
        holder.textTaskDateEat.setText(item.getDate());
        holder.todoCheckBoxEat.setChecked(toBoolean(item.getStatus_eat()));

        holder.todoCheckBoxEat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatusEat(item.getId_eat_table(), 1);
                } else {
                    db.updateStatusEat(item.getId_eat_table(), 0);
                }
            }
        });


    }

    public Context getContext() {
        return context;
    }
    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTasksEat(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItemEat(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTaskEat(item.getId_eat_table());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItemEat(final int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id_eat", item.getId_eat_table());
        bundle.putString("eat", item.getEat());
        AddNewTaskEat fragment = new AddNewTaskEat();
        fragment.setArguments(bundle);
        fragment.show(context.getSupportFragmentManager(), AddNewTaskEat.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox todoCheckBoxEat;
        TextView textTaskEat,textTaskDateEat;

        ViewHolder(View view) {
            super(view);
            todoCheckBoxEat = view.findViewById(R.id.todoCheckBoxEat);
            textTaskEat = view.findViewById(R.id.textTaskEat);
            textTaskDateEat = view.findViewById(R.id.textTaskDate);
        }
    }
}
