package com.example.myway.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;

import com.example.myway.Activity.ComparisonsActivity;
import com.example.myway.Adapters.ToDoAdapterEat;
import com.example.myway.Adapters.ToDoAdapterNo;
import com.example.myway.Model.ToDoModel;
import com.example.myway.R;
import com.example.myway.SwipesActivity.RecyclerItemTouchHelperEat;
import com.example.myway.Utils.DatabaseHandler;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EatFragment extends Fragment {

    private EditText eatEdit;
    private String task;
    private int id;
    private DatabaseHandler db;
    private Button eatBtn;

    private List<ToDoModel> modelListEat;
    private List<ToDoModel> modelListsSpareEat;
    private RecyclerView recyclerEAt;
    private ToDoAdapterEat toDoAdapterEat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eat, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        modelListEat = new ArrayList<>();
        modelListsSpareEat = new ArrayList<>();
        eatEdit = view.findViewById(R.id.eatEdit);
        recyclerEAt = view.findViewById(R.id.recyclerEAt);;
        eatBtn = view.findViewById(R.id.eatBtn);
        task = getArguments().getString("task");
        id = getArguments().getInt("num",0);
        eatEdit.setHint("Введите что у вас есть, для достижения " + "\"" + task + "\".");

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        setEat();
    }

    private void setEat() {
        recyclerEAt.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerEAt.setHasFixedSize(true);

        toDoAdapterEat = new ToDoAdapterEat(modelListEat, db, (ComparisonsActivity) getActivity());
        recyclerEAt.setAdapter(toDoAdapterEat);

        Context context = recyclerEAt.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.loyout_to_up_recycler);
        recyclerEAt.setLayoutAnimation(layoutAnimationController);
        recyclerEAt.getAdapter().notifyDataSetChanged();
        recyclerEAt.scheduleLayoutAnimation();

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelperEat(toDoAdapterEat));
        itemTouchHelper.attachToRecyclerView(recyclerEAt);

        Collections.reverse(modelListEat);
        modelListEat = db.getAllEat();

        for (ToDoModel toDoModel : modelListEat) {
            if (toDoModel.getId_eat() == id) {
                modelListsSpareEat.add(toDoModel);
                toDoAdapterEat.setTasksEat(modelListsSpareEat);

            }
        }


        eatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(eatEdit.getText().toString())) {
                    ToDoModel task = new ToDoModel();
                    task.setEat(eatEdit.getText().toString());
                    task.setStatus_eat(0);
                    task.setDate(DateFormat.getDateInstance().format(new Date()));
                    task.setId_eat(id);
                    db.insertEat(task);

                    eatEdit.setText("");
                    modelListEat = db.getAllEat();

                    modelListsSpareEat.clear();
                    for (ToDoModel toDoModel : modelListEat) {
                        if (toDoModel.getId_eat() == id) {
                            modelListsSpareEat.add(toDoModel);
                            toDoAdapterEat.setTasksEat(modelListsSpareEat);

                        }
                    }

                }
            }
        });

    }




//    @Override
//    public void handleDialogClose(DialogInterface dialog) {
//
//        modelListEat = db.getAllEat();
//        modelListsSpareEat.clear();
//        for (ToDoModel toDoModel : modelListEat) {
//            if (toDoModel.getId_eat() == id) {
//                modelListsSpareEat.add(toDoModel);
//                toDoAdapterEat.setTasksEat(modelListsSpareEat);
//
//            }
//        }
//        toDoAdapterEat.notifyDataSetChanged();
//
//        modelListNo = db.getAllNo();
//        modelListsSpareNo.clear();
//        for (ToDoModel toDoModel : modelListNo) {
//            if (toDoModel.getId_no() == id) {
//                modelListsSpareNo.add(toDoModel);
//                toDoAdapterNo.setTasksNo(modelListsSpareNo);
//
//            }
//        }
//        toDoAdapterNo.notifyDataSetChanged();
//
//    }
}