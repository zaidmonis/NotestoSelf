package com.zaid.notestoself;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView;
    List<Notes> notesList = new ArrayList<>();
    private SQLiteDatabase database;
    private boolean doubleBackToExitPressedOnce;


    //implementing 'double press back to exit application' feature
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        imageView = findViewById(R.id.firstImage);
        doubleBackToExitPressedOnce = false;
        DBHelper helper = new DBHelper(this);
        database = DBHelper.database;


        Cursor cursor = database.rawQuery("select title from Notes;", null);
        prepare(cursor);            //getting all titles into a List
        recyclerView = findViewById(R.id.myRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(notesList,this);
        recyclerView.setAdapter(adapter);

        //if there is at least one Note, hide the '+' sign from the middle of the screen. otherwise show a plus icon to add notes.
        if(adapter.getItemCount()!=0){
            imageView.setVisibility(View.INVISIBLE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEditScreen();               //add a note
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:{
                callEditScreen();
            }
        }
        return true;
    }

    private void callEditScreen() {
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in, R.anim.slide_out);
        startActivity(intent, activityOptions.toBundle());
        finish();
    }

    void prepare(Cursor cursor){
        while(cursor.moveToNext()){
            notesList.add(new Notes(cursor.getString(0)));
        }

    }
}
