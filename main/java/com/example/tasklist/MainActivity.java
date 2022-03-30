package com.example.tasklist;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklist.AdapterForTask.TaskListAdepter;
import com.example.tasklist.AdapterForTask.TouchEdit_Delete;
import com.example.tasklist.AdapterForTask.TouchHelper;
import com.example.tasklist.AddNewTask.AddTask;
import com.example.tasklist.AddNewTask.MyDandCListener;
import com.example.tasklist.DataBaseSQLite.SQLdbManager;
import com.example.tasklist.Model.MyTaskListModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements MyDandCListener  {

    // define some variable
//    private RecyclerView taskReView;
    ImageButton addTaskButton;

// sql data base - >
    private TextView t1;
    private TextView t2;
    private TextView t3;

    private SQLdbManager database;
    private RecyclerView tasksRecyclerView;

    // Build an object that connects the Task list to the view
    private TaskListAdepter taskListAdepter;
    private List<MyTaskListModel> taskListM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        database = new SQLdbManager(this);
        database = database.openDatabase();

        taskListM = new ArrayList<>();

        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.SwipeTextView);
        t3 = findViewById(R.id.editSwipeTextView);

        tasksRecyclerView = findViewById(R.id.taReView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskListAdepter = new TaskListAdepter(this, database);

        tasksRecyclerView.setAdapter(taskListAdepter);
        taskListM = database.getAllTasks();
        Collections.reverse(taskListM);


        taskListAdepter.setTask_set(taskListM);
        this.addTaskButton = findViewById(R.id.imageButton);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(taskListAdepter));
        touchHelper.attachToRecyclerView(tasksRecyclerView);


        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "TaskFolder";


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTask.newInstance().show(getSupportFragmentManager(), AddTask.TAG);
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void dialogClose(DialogInterface dandCListener) {
        taskListM = database.getAllTasks();
        Collections.reverse(taskListM);
        taskListAdepter.setTask_set(taskListM);
        taskListAdepter.notifyDataSetChanged();
    }
}