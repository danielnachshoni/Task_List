package com.example.tasklist.AdapterForTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklist.AddNewTask.AddTask;
import com.example.tasklist.DataBaseSQLite.SQLdbManager;
import com.example.tasklist.MainActivity;
import com.example.tasklist.Model.MyTaskListModel;
import com.example.tasklist.R;

import java.util.Calendar;
import java.util.List;

public class TaskListAdepter extends RecyclerView.Adapter<TaskListAdepter.ViewHolder> {
    private SQLdbManager database;
    private List<MyTaskListModel> taskList;
    private MainActivity activity;
    private int urgency;
    private TextView tvUrgency;

    public TaskListAdepter(MainActivity activity ,SQLdbManager database ){
        this.activity=activity;
        this.database=database;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox theTask;
        TextView tvUrgencyTemp;
        TextView dateTv;

        ViewHolder(View view){
            super(view);
            theTask = view.findViewById(R.id.todoCheckBox);
            tvUrgencyTemp = (TextView) view.findViewById(R.id.inTaskListUrgencyLevel);
            dateTv = (TextView) view.findViewById(R.id.dateText);
            
        }

    }

    public void onBindViewHolder(ViewHolder toHold, int currPosition){
       database= database.openDatabase();
        MyTaskListModel task=taskList.get(currPosition);
        toHold.theTask.setText(task.getStringTask());
        toHold.theTask.setChecked(convertToBoolean(task.getTaskStatus()));
        toHold.theTask.setText(task.getStringTask());
        toHold.dateTv.setText(task.getDate());

        if(task.getImportance()==0)
            toHold.tvUrgencyTemp.setText("Low urgency");
        if(task.getImportance()==1)
            toHold.tvUrgencyTemp.setText("Medium urgency");
        if(task.getImportance()==2)
            toHold.tvUrgencyTemp.setText("High urgency");

        toHold.theTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    database.updateTaskStatus(task.getTaskID(),1);
                }else {
                    database.updateTaskStatus(task.getTaskID(),0);
                }
            }
        });
    }
    public Context getContext() {
        return activity;
    }
    public void deleteItem(int index){
        MyTaskListModel item=taskList.get(index);
        database.deleteTask(item.getTaskID());
        taskList.remove(index);
        notifyItemRemoved(index);
    }

    // to edit the task - >
    public void editTask(int index){
        // check if something need to change
        MyTaskListModel item=taskList.get(index);
        Bundle b=new Bundle();
        b.putInt("id",item.getTaskID());
        b.putString("task",item.getStringTask());
        b.putString("date",item.getDate());
        b.putInt("Ur",item.getImportance());
      
        AddTask addTask=new AddTask();
        addTask.setArguments(b);
        addTask.show(activity.getSupportFragmentManager(),AddTask.TAG);

    }
    private boolean convertToBoolean(int i ){
        return i!=0;

    }
    public int getItemCount(){
        return taskList.size();
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.in_task_layout1,viewGroup,false);
        return new ViewHolder(itemView);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void  setTask_set(List<MyTaskListModel> toDoList){
        this.taskList=toDoList;
        notifyDataSetChanged();
    }

}
