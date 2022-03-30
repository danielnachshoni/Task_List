package com.example.tasklist.AdapterForTask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklist.R;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {
    private TaskListAdepter adepter;
    Button editButton;
    ImageButton deleteButton;
    public TouchHelper(TaskListAdepter adepter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adepter = adepter;

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int index = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder alatText = new AlertDialog.Builder(adepter.getContext());
            alatText.setTitle("--Delete Task--");

            alatText.setMessage("You sure ?");
            alatText.setPositiveButton("Yes,delete",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adepter.deleteItem(index);
                        }
                    });
            alatText.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adepter.notifyItemChanged(viewHolder.getLayoutPosition());
                }
            });
            AlertDialog alertDialog = alatText.create();
            alertDialog.show();
        } else {
            Toast.makeText(adepter.getContext(), "Edit Task Mode.", Toast.LENGTH_SHORT).show();
            adepter.editTask(index);
        }

    }

}