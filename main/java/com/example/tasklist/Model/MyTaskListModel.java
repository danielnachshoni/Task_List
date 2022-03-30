package com.example.tasklist.Model;

import java.util.Calendar;
import java.util.Date;

public class MyTaskListModel {
    private int taskID;
    private int taskStatus;
    private String date;
    private int importance;

    public Calendar getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(Calendar calendarDate) {
        this.calendarDate = calendarDate;
    }

    private Calendar calendarDate;
    private String stringTask;


    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getStringTask() {
        return stringTask;
    }

    public void setStringTask(String stringTask) {
        this.stringTask = stringTask;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}