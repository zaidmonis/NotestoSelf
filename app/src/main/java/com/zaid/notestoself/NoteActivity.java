package com.zaid.notestoself;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    TextView noteText;
    private SQLiteDatabase db;
    private DBHelper helper;
    String title;

    @Override
    public void onBackPressed() {
        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.slide_right_in, R.anim.slide_right_out);
        startActivity(new Intent(getApplicationContext(), FirstActivity.class), activityOptions.toBundle());
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        String title = getIntent().getStringExtra("title");

        noteText = findViewById(R.id.noteText);


        helper = new DBHelper(this);
        db = helper.database;



        Cursor cur = db.rawQuery("select title, note from Notes WHERE title = \"" +title+ "\";", null);
        while(cur.moveToNext()){
            noteText.setText(cur.getString(1).toString());
            this.setTitle(cur.getString(0).toString());
            title = cur.getString(0).toString();
        }
        if (title.equals("")) {
            onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:{
                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                intent.putExtra("title", this.getTitle().toString());
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in, R.anim.slide_out);
                startActivity(intent, activityOptions.toBundle());
                finish();
                return true;
            }
            case R.id.action_delete:{

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Delete this note?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteNote(getTitle().toString(), db);
                        Toast.makeText(NoteActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();


                return true;
            }
        }
        return false;
    }
}
