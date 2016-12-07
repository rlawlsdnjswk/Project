package com.example.user.mycalendarinput;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.support.v7.app.AlertDialog;

import org.w3c.dom.Text;

public class DetailShow extends AppCompatActivity  {


    MyDBHelper mDBHelper;
    String today;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    int mId;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_show);

        TextView date =(TextView) findViewById(R.id.detaildate);
        TextView title =(TextView) findViewById(R.id.detailtitle);
        TextView time =(TextView) findViewById(R.id.detailtime);
        TextView place =(TextView) findViewById(R.id.detailplace);
        TextView memo =(TextView) findViewById(R.id.detailmemo);

        Intent intent = getIntent();
        today = intent.getStringExtra("Param1");
        mId = intent.getIntExtra("ParamID", -1);

        mDBHelper = new MyDBHelper(this, "Today.db", null, 1);
        final SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM today WHERE _id='" + mId + "'", null);

        if (cursor.moveToNext()) {
            title.setText(cursor.getString(1));
            date.setText(cursor.getString(2));
            time.setText(cursor.getString(3));
            place.setText(cursor.getString(4));
            memo.setText(cursor.getString(5));
        }

        Button btn = (Button) findViewById(R.id.edit);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Detail.class);
                intent.putExtra("ParamID", mId);
                startActivityForResult(intent, 1);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        // super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
            case 1:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("상세보기");
        return super.onCreateOptionsMenu(menu);
    }
}
