package com.zaid.notestoself;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaid on 26/9/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{


    ArrayList<String> notesTitles;
    List<Notes> notesList = new ArrayList<>();
    SQLiteDatabase database;
    FirstActivity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        public MyViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, NoteActivity.class);
                    intent.putExtra("title", titleText.getText().toString());
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in, R.anim.slide_out);
                    activity.startActivity(intent,activityOptions.toBundle());
                    activity.finish();
                }
            });
        }
        public void setup(Notes notes) {
            titleText.setText(notes.getTitle());
        }

    }
    public MyRecyclerAdapter(List<Notes> notesList, FirstActivity firstActivity) {
        this.notesList = notesList;
        this.activity = firstActivity;
    }



    @NonNull
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.MyViewHolder holder, int position) {
        Notes notes = notesList.get(position);
        holder.setup(notes);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }


}
