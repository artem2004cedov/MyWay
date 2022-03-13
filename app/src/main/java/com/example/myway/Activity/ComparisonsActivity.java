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
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.example.myway.Adapters.ToDoAdapterEat;
import com.example.myway.Adapters.ToDoAdapterNo;
import com.example.myway.Fragment.FragmentAdapter;
import com.example.myway.Interf.DialogCloseListener;
import com.example.myway.Model.ToDoModel;
import com.example.myway.R;
import com.example.myway.SwipesActivity.RecyclerItemTouchHelperEat;
import com.example.myway.SwipesActivity.RecyclerItemTouchHelperNo;
import com.example.myway.Utils.DatabaseHandler;
import com.google.android.material.tabs.TabLayout;

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
    private TabLayout tableLayot;
    private ViewPager Viper_layout;

    private List<ToDoModel> modelListEat;
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
        tableLayot = findViewById(R.id.tableLayot);
        Viper_layout = findViewById(R.id.Viper_layout);
        init();
    }



    private void init() {

//        getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.black96));

        modelListEat = new ArrayList<>();
        modelListsSpareEat = new ArrayList<>();
        eatEdit = findViewById(R.id.eatEdit);
        recyclerEAt = findViewById(R.id.recyclerEAt);
        recyclerNo = findViewById(R.id.recyclerNo);
        eatBtn = findViewById(R.id.eatBtn);
        noEdit = findViewById(R.id.noEdit);
        noBtn = findViewById(R.id.noBtn);
        task = getIntent().getStringExtra("task");
        id = getIntent().getIntExtra("id", 0);

        db = new DatabaseHandler(ComparisonsActivity.this);
        db.openDatabase();

        Viper_layout.setAdapter(new FragmentAdapter(getSupportFragmentManager(),id,task));
        tableLayot.setupWithViewPager(Viper_layout);


//        setEat();
//        setNo();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", 10);
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

        toDoAdapterEat = new ToDoAdapterEat(modelListEat, db, ComparisonsActivity.this);
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

    @Override
    public void handleDialogClose(DialogInterface dialog) {

        modelListEat = db.getAllEat();
        modelListsSpareEat.clear();
        for (ToDoModel toDoModel : modelListEat) {
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