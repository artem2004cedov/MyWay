package com.example.myway.Fragment;

import android.content.Context;
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
import com.example.myway.Adapters.ToDoAdapterNo;
import com.example.myway.Model.ToDoModel;
import com.example.myway.R;
import com.example.myway.SwipesActivity.RecyclerItemTouchHelperNo;
import com.example.myway.Utils.DatabaseHandler;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class NoFragment extends Fragment {
    private EditText noEdit;
    private String task;
    private int id;
    private DatabaseHandler db;
    private Button noBtn;

    private List<ToDoModel> modelListNo;
    private List<ToDoModel> modelListsSpareNo;
    private RecyclerView recyclerNo;
    private ToDoAdapterNo toDoAdapterNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no, container, false);
        init(view);
        return view;
    }

    private void init(View view) {


        recyclerNo = view.findViewById(R.id.recyclerNo);
        noEdit = view.findViewById(R.id.noEdit);
        noBtn = view.findViewById(R.id.noBtn);
        task = getArguments().getString("task");
        id = getArguments().getInt("num", 0);
        noEdit.setHint("Введите чего у вас нет, для достежения " + "\"" + task + "\".");

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        setNo();
    }

    private void setNo() {
        recyclerNo.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerNo.setHasFixedSize(true);
        modelListNo = new ArrayList<>();
        modelListsSpareNo = new ArrayList<>();

        toDoAdapterNo = new ToDoAdapterNo(modelListNo, db, (ComparisonsActivity) getActivity());
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
}