package com.zaid.notestoself;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {

    EditText editText, editTitle;
    Button submitButton, backButton;

    @Override
    public void onBackPressed() {
        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.slide_right_in, R.anim.slide_right_out);
        startActivity(new Intent(getApplicationContext(), NoteActivity.class).putExtra("title", editTitle.getText().toString()), activityOptions.toBundle());
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.editNote);
        editTitle = findViewById(R.id.editTitle);
        submitButton = findViewById(R.id.sybmitButton);
        backButton = findViewById(R.id.backButton);




        //if we are opening a pre-saved note to edit, load the note and title into MainActivity()
        String title = getIntent().getStringExtra("title");
        final DBHelper helper = new DBHelper(this);
        final SQLiteDatabase db = DBHelper.database;
        final Cursor cur = db.rawQuery("select title, note from Notes WHERE title = \"" +title+ "\";", null);
        while(cur.moveToNext()){
            editText.setText(cur.getString(1));
            editTitle.setText(cur.getString(0));
        }
        //if we are opening a pre-saved Note, it wouldn't allow to change title
        if (!editTitle.getText().toString().equals("")){
            editTitle.setInputType(InputType.TYPE_NULL);
        }



        //to Customize the KeyBoard type
        //text should automatically move to next line.
        //Remove the 'Carriage Return' Button and place a 'Done' Button instead
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeActionLabel("DONE",EditorInfo.IME_ACTION_DONE);              //Set Return Carriage as "DONE"
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (event == null) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // Capture soft enters in a singleLine EditText that is the last EditText
                        // This one is useful for the new list case, when there are no existing ListItems
                        editText.clearFocus();
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }
                else if (actionId == EditorInfo.IME_NULL) {
                    // Capture most soft enters in multi-line EditTexts and all hard enters;
                    // They supply a zero actionId and a valid keyEvent rather than
                    // a non-zero actionId and a null event like the previous cases.
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        // We capture the event when the key is first pressed.
                    } else {
                        // We consume the event when the key is released.
                        return true;
                    }
                }
                else {
                    // We let the system handle it when the listener is triggered by something that
                    // wasn't an enter.
                    return false;
                }
                return true;
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                String note = editText.getText().toString();
                String title = editTitle.getText().toString();

                //Only save the note when both title and note are not empty
                if (!note.equals("") && !title.equals("")){
                    Cursor curs = db.rawQuery("select title from Notes WHERE title = \"" +title+ "\";", null);
                    if(!curs.moveToNext()){                 //if note with the same title does not exists, create a new Note
                        helper.addNote(title, note, db);
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                    else{                                   //if not with same title exists, update that Note
                        helper.updateNote(title, note, db);
                        Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                    curs.close();
                    goBack();
                }
                //if user press save Button with empty fields set the colour of hint of respective field to red
                else{
                    if (title.equals("")){
                        editTitle.setHintTextColor(ColorStateList.valueOf(RED));
                    }
                    else if (note.equals("")){
                        editText.setHintTextColor(ColorStateList.valueOf(RED));
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        cur.close();
    }

    private void goBack() {
        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.slide_right_in, R.anim.slide_right_out);
        startActivity(intent, activityOptions.toBundle());
        finish();
    }
}
