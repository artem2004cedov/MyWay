package com.example.myway.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;

import com.example.myway.Activity.ComparisonsActivity;
import com.example.myway.Adapters.ToDoAdapterNecessary;
import com.example.myway.Adapters.ToDoAdapterNo;
import com.example.myway.Model.ToDoModel;
import com.example.myway.R;
import com.example.myway.SwipesActivity.RecyclerItemTouchHelperNo;
import com.example.myway.Utils.DatabaseHandler;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NecessaryFragment extends Fragment {

    private EditText necessaryEdit;
    private String task;
    private int id;
    private DatabaseHandler db;
    private Button necessaryBtn;

    private List<ToDoModel> modelListNecessary;
    private List<ToDoModel> modelListsSpareNecessary;
    private RecyclerView recyclerNecessary;
    private ToDoAdapterNecessary toDoAdapterNecessary;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_necessary, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerNecessary = view.findViewById(R.id.recyclerNecessary);
        necessaryEdit = view.findViewById(R.id.necessaryEdit);
        necessaryBtn = view.findViewById(R.id.necessaryBtn);
        task = getArguments().getString("task");
        id = getArguments().getInt("num", 0);
        necessaryEdit.setHint("Введите чего у вас нет, для достежения " + "\"" + task + "\".");

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        setNecessary();
    }

    private void setNecessary() {
        recyclerNecessary.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerNecessary.setHasFixedSize(true);
        modelListNecessary = new ArrayList<>();
        modelListsSpareNecessary = new ArrayList<>();

        toDoAdapterNecessary = new ToDoAdapterNecessary(modelListNecessary, db, (ComparisonsActivity) getActivity());
        recyclerNecessary.setAdapter(toDoAdapterNecessary);

        Context context = recyclerNecessary.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.loyout_to_up_recycler);
        recyclerNecessary.setLayoutAnimation(layoutAnimationController);
        recyclerNecessary.getAdapter().notifyDataSetChanged();
        recyclerNecessary.scheduleLayoutAnimation();

//        ItemTouchHelper itemTouchHelper = new
//                ItemTouchHelper(new RecyclerItemTouchHelperNo(toDoAdapterNo));
//        itemTouchHelper.attachToRecyclerView(recyclerNecessary);

        Collections.reverse(modelListNecessary);
        modelListNecessary = db.getAllNecessary();

        for (ToDoModel toDoModel : modelListNecessary) {
            if (toDoModel.getId_necessary() == id) {
                modelListsSpareNecessary.add(toDoModel);
                toDoAdapterNecessary.setTasksNecessary(modelListsSpareNecessary);
            }
        }


        necessaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(necessaryEdit.getText().toString())) {

                    ToDoModel task = new ToDoModel();
                    task.setNecessary(necessaryEdit.getText().toString());
                    task.setStatus_necessary(0);
                    task.setDate(DateFormat.getDateInstance().format(new Date()));
                    task.setId_necessary(id);
                    db.insertNecessary(task);

                    necessaryEdit.setText("");
                    modelListNecessary = db.getAllNecessary();

                    modelListsSpareNecessary.clear();
                    for (ToDoModel toDoModel : modelListNecessary) {
                        if (toDoModel.getId_necessary() == id) {
                            modelListsSpareNecessary.add(toDoModel);
                            toDoAdapterNecessary.setTasksNecessary(modelListsSpareNecessary);

                        }
                    }


                }
            }
        });

    }
}