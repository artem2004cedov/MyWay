package com.example.myway.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myway.Adapters.ToDoAdapterEat;
import com.example.myway.Adapters.ToDoAdapterNo;
import com.example.myway.Interf.DialogCloseListener;
import com.example.myway.Model.ToDoModel;
import com.example.myway.R;
import com.example.myway.SwipesActivity.RecyclerItemTouchHelperEat;
import com.example.myway.SwipesActivity.RecyclerItemTouchHelperNo;
import com.example.myway.Utils.DatabaseHandler;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ComparisonsActivity extends AppCompatActivity implements DialogCloseListener {
    private EditText eatEdit, noEdit;
    private String task;
    private int id;
    private DatabaseHandler db;
    private Button eatBtn, noBtn;

    private List<ToDoModel> modelList;
    private List<ToDoModel> modelListsSpareEat;

    private List<ToDoModel> modelListNo;
    private List<ToDoModel> modelListsSpareNo;

    private RecyclerView recyclerEAt;
    private RecyclerView recyclerNo;

    private ToDoAdapterEat toDoAdapterEat;
    private ToDoAdapterNo toDoAdapterNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparisons);
        init();
    }

    private void init() {
//        getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.black96));

        modelList = new ArrayList<>();
        modelListsSpareEat = new ArrayList<>();
        eatEdit = findViewById(R.id.eatEdit);
        recyclerEAt = findViewById(R.id.recyclerEAt);
        recyclerNo = findViewById(R.id.recyclerNo);
        eatBtn = findViewById(R.id.eatBtn);
        noEdit = findViewById(R.id.noEdit);
        noBtn = findViewById(R.id.noBtn);
        task = getIntent().getStringExtra("task");
        id = getIntent().getIntExtra("id", 0);
        eatEdit.setHint("Введите что у вас есть, для достижения " + "\"" + task + "\".");
        noEdit.setHint("Введите чего у вас нет, для достежения " + "\"" + task + "\".");

        db = new DatabaseHandler(ComparisonsActivity.this);
        db.openDatabase();

        setEat();
        setNo();
    }

    private void setNo() {
        recyclerNo.setLayoutManager(new LinearLayoutManager(this));
        recyclerNo.setHasFixedSize(true);
        modelListNo = new ArrayList<>();
        modelListsSpareNo = new ArrayList<>();

        toDoAdapterNo = new ToDoAdapterNo(modelListNo, db, ComparisonsActivity.this);
        recyclerNo.setAdapter(toDoAdapterNo);

        Context context = recyclerNo.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.loyout_to_up_recycler);
        recyclerNo.setLayoutAnimation(layoutAnimationController);
        recyclerNo.getAdapter().notifyDataSetChanged();
        recyclerNo.scheduleLayoutAnimation();

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelperNo(toDoAdapterNo));
        itemTouchHelper.attachToRecyclerView(recyclerNo);

        Collections.reverse(modelListNo);
        modelListNo = db.getAllNo();

        for (ToDoModel toDoModel : modelListNo) {
            if (toDoModel.getId_no() == id) {
                modelListsSpareNo.add(toDoModel);
                toDoAdapterNo.setTasksNo(modelListsSpareNo);
            }
        }

            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(noEdit.getText().toString())) {

                        ToDoModel task = new ToDoModel();
                        task.setNo(noEdit.getText().toString());
                        task.setStatus_no(0);
                        task.setDate(DateFormat.getDateInstance().format(new Date()));
                        task.setId_no(id);
                        db.insertNo(task);

                        noEdit.setText("");
                        modelListNo = db.getAllNo();

                        modelListsSpareNo.clear();
                        for (ToDoModel toDoModel : modelListNo) {
                            if (toDoModel.getId_no() == id) {
                                modelListsSpareNo.add(toDoModel);
                                toDoAdapterNo.setTasksNo(modelListsSpareNo);

                            }
                        }

                    }
                }
            });

    }

    private void setEat() {
        recyclerEAt.setLayoutManager(new LinearLayoutManager(this));
        recyclerEAt.setHasFixedSize(true);

        toDoAdapterEat = new ToDoAdapterEat(modelList, db, ComparisonsActivity.this);
        recyclerEAt.setAdapter(toDoAdapterEat);

        Context context = recyclerEAt.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.loyout_to_up_recycler);
        recyclerEAt.setLayoutAnimation(layoutAnimationController);
        recyclerEAt.getAdapter().notifyDataSetChanged();
        recyclerEAt.scheduleLayoutAnimation();

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelperEat(toDoAdapterEat));
        itemTouchHelper.attachToRecyclerView(recyclerEAt);

        Collections.reverse(modelList);
        modelList = db.getAllEat();

        for (ToDoModel toDoModel : modelList) {
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
                        modelList = db.getAllEat();

                        modelListsSpareEat.clear();
                        for (ToDoModel toDoModel : modelList) {
                            if (toDoModel.getId_eat() == id) {
                                modelListsSpareEat.add(toDoModel);
                                toDoAdapterEat.setTasksEat(modelListsSpareEat);

                            }
                        }

                    }
                }
            });

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {

        modelList = db.getAllEat();
        modelListsSpareEat.clear();
        for (ToDoModel toDoModel : modelList) {
            if (toDoModel.getId_eat() == id) {
                modelListsSpareEat.add(toDoModel);
                toDoAdapterEat.setTasksEat(modelListsSpareEat);

            }
        }
        toDoAdapterEat.notifyDataSetChanged();

        modelListNo = db.getAllNo();
        modelListsSpareNo.clear();
        for (ToDoModel toDoModel : modelListNo) {
            if (toDoModel.getId_no() == id) {
                modelListsSpareNo.add(toDoModel);
                toDoAdapterNo.setTasksNo(modelListsSpareNo);

            }
        }
        toDoAdapterNo.notifyDataSetChanged();

    }
}