package com.example.tasklist.AddNewTask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.tasklist.DataBaseSQLite.SQLdbManager;
import com.example.tasklist.Model.MyTaskListModel;
import com.example.tasklist.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class AddTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText TaskText;
    private CalendarView calendarView;
    private ImageButton saveButton;
    private int urgency = 0;
    private RadioGroup radioGroup;
    private RadioButton low;
    private RadioButton medium;
    private RadioButton high;
    private SQLdbManager sqldb;
    private Button dateButton;
    private int mYear, mMonth, mDay;/// to change !!!!!
    private String date;
    private TextView tvDate;/// to change !!!!!


    public static AddTask newInstance() {
        return new AddTask();
    }

    @Override
    // Use Template container bundle ;
    public void onCreate(Bundle toSave) {
        super.onCreate(toSave);
        setStyle(STYLE_NO_FRAME, R.style.AddStyle);
    }

    @Nullable
    @Override

    // LayoutInflater->> To adept ile into its corresponding View objects
    //View Group - special view that can contain other views (called children.)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle toSave) {
        View view = inflater.inflate(R.layout.task_add_layout, container, false);
//        View view =intent(R.layout.task_add_layout, container, false);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.main);
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle toSave) {
        super.onViewCreated(view, toSave);
        TaskText = requireView().findViewById(R.id.AddTask);
        tvDate = requireView().findViewById(R.id.TextDate);
        saveButton = requireView().findViewById(R.id.imageButton3);
        radioGroup = (RadioGroup) requireView().findViewById(R.id.groupUrgency);
        dateButton = (Button) requireView().findViewById(R.id.dateButton);
        Calendar c = Calendar.getInstance();
       this.mYear = c.get(Calendar.YEAR);
       this.mMonth = c.get(Calendar.MONTH);
       this.mDay = c.get(Calendar.DAY_OF_MONTH);

//    calendarView=requireView().findViewById(R.id.calendarView);
        saveButton.setEnabled(false);
        sqldb = new SQLdbManager(getActivity());
        sqldb = sqldb.openDatabase();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                low = (RadioButton) requireView().findViewById(R.id.rbLow);
                medium = (RadioButton) requireView().findViewById(R.id.rbMedium);
                high = (RadioButton) requireView().findViewById(R.id.rbHigh);
                if(low.isChecked())
                    urgency=0;
                else if(medium.isChecked())
                    urgency=1;
                else if(high.isChecked())
                    urgency=2;

            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            changeDateBtn_handle(v);// change name

            }
        });


        boolean isUpdate = false;
        // passing the data to the bundle
        final Bundle bundle = getArguments();
        // check if the Task was pass from the user
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            String date = bundle.getString("date");
            Integer ur=bundle.getInt("Ur");

            //set the text in the text editor after  getting that from the user
            tvDate.setText(date);
            TaskText.setText(task);

            assert task != null;
            // check the task is empty or not
            // only if teres a text its posible  to save
            if (task.length() > 0)
                saveButton.setEnabled(true);
        }


        // check if its pass the condition
        TaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    saveButton.setEnabled(false);
                } else {
                    saveButton.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        final boolean finalIsUpdate = isUpdate;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = TaskText.getText().toString();


                if (finalIsUpdate) {
                    Toast.makeText(v.getContext(), "Task has been edit.", Toast.LENGTH_SHORT).show();
                    sqldb.updateTaskDesc(bundle.getInt("id"), taskText);
//                    if(date.isEmpty()){
//                        Toast.makeText(v.getContext(), "Chose Date first", Toast.LENGTH_SHORT).show();
//                    }
                    String date12=tvDate.getText().toString();
                    sqldb.updateDate(bundle.getInt("id"),date12);
                    sqldb.updateTaskUrgency(bundle.getInt("id"),urgency);

                } else {
                    MyTaskListModel task = new MyTaskListModel();

                    task.setStringTask(taskText);
                    task.setTaskStatus(0);
                    task.setImportance(urgency);
                    task.setDate(date);
                    sqldb.insertAnewTask(task);
                }
                // use to update the recyclerview Withe either status of task
                dismiss();
            }
        });

    }
    public void click_urgency_btn(int u){
        low = (RadioButton) requireView().findViewById(R.id.rbLow);
        medium = (RadioButton) requireView().findViewById(R.id.rbMedium);
        high = (RadioButton) requireView().findViewById(R.id.rbHigh);
        // 3 case
        if(u==0){
            low.setChecked(false);
            medium.setChecked(false);
            high.setChecked(true);
        }
//        if(u==1){
//            low.setChecked(false);
//            medium.setChecked(true);
//            high.setChecked(false);
//        }
//        if(u==2){
//            low.setChecked(false);
//            medium.setChecked(false);
//            high.setChecked(true);
//        }

    }

    public void changeDateBtn_handle(View view) {
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String dateString = (String) DateFormat.format("MMM d, yyyy", newDate);
                        date=dateString;
                        tvDate.setText(dateString);
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
//    verify which radiobutton (for urgency level) was picked.
    public void onRadioButtonClicked(View view) {
        low = (RadioButton) requireView().findViewById(R.id.rbLow);
        medium = (RadioButton) requireView().findViewById(R.id.rbMedium);
        high = (RadioButton) requireView().findViewById(R.id.rbHigh);

//        boolean checked;
//        checked = ((RadioButton) view).isChecked();
        if(view.getId()==low.getId()) this.urgency=0;
        else if(view.getId()==medium.getId()) this.urgency=1;
        else if(view.getId()==high.getId()) this.urgency=2;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog_interface) {
        Activity activity = getActivity();
        // to update and refreshing the recyclerview
        if (activity instanceof MyDandCListener)
            ((MyDandCListener) activity).dialogClose(dialog_interface);
    }
}
