package com.example.user.mycalendarinput;

import android.content.DialogInterface;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Detail extends AppCompatActivity implements OnClickListener {
    MyDBHelper mDBHelper;
    int mId;
    String today;
    EditText editDate, editTitle, editTime, editMemo, editPlace;

    public boolean onCreateOptionsMenu(Menu menu) {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("일정 추가/수정");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



//현재 시간을 디폴트로 잡아줌


        String today = null;
        String today1 = null;
        String moon = "　~　";
        Date date2 = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        today =  sdformat.format(cal.getTime());
        cal.add(Calendar.HOUR, +1);
        today1  = sdformat.format(cal.getTime());
        String str_date =today +moon +   today1;

        EditText editText1 = (EditText) findViewById(R.id.edittime) ;
        editText1.setText(str_date) ;

        editDate = (EditText) findViewById(R.id.editdate);
        editTitle = (EditText) findViewById(R.id.edittitle);
        editTime = (EditText) findViewById(R.id.edittime);
        editMemo = (EditText) findViewById(R.id.editmemo);
        editPlace = (EditText) findViewById(R.id.editplace);
        Intent intent = getIntent();
        mId = intent.getIntExtra("ParamID", -1);
        today = intent.getStringExtra("ParamDate");

        mDBHelper = new MyDBHelper(this, "Today.db", null, 1);

        if (mId == -1) {
            editDate.setText(today);
        } else {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM today WHERE _id='" + mId + "'", null);

            if (cursor.moveToNext()) {
                editTitle.setText(cursor.getString(1));
                editDate.setText(cursor.getString(2));
                editTime.setText(cursor.getString(3));
                editPlace.setText(cursor.getString(4));
                editMemo.setText(cursor.getString(5));
            }
            mDBHelper.close();
        }

        Button btn1 = (Button) findViewById(R.id.btnsave);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.btndel);
        btn2.setOnClickListener(this);
        Button btn3 = (Button) findViewById(R.id.btncancel);
        btn3.setOnClickListener(this);


        if (mId == -1) {
            btn2.setVisibility(View.INVISIBLE);
        }
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (v.getId() == R.id.btnsave) {
            if (mId != -1) {
                db.execSQL("UPDATE today SET title='"
                        + editTitle.getText().toString() + "',date='"
                        + editDate.getText().toString() + "', time='"
                        + editTime.getText().toString() + "', place='"
                        + editPlace.getText().toString() + "', memo='"
                        + editMemo.getText().toString() + "' WHERE _id='" + mId
                        + "';");
            } else {
                db.execSQL("INSERT INTO today VALUES(null, '"
                        + editTitle.getText().toString() + "', '"
                        + editDate.getText().toString() + "' , '"
                        + editTime.getText().toString() + "', '"
                        + editPlace.getText().toString() + "', '"
                        + editMemo.getText().toString() + "');");
            }
            mDBHelper.close();
            setResult(RESULT_OK);
            finish();
        }

        else if(v.getId()== R.id.btndel){
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("알림!");
            alert.setMessage("정말 삭제 하시겠습니까?");

            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mId != -1) {
                        db.execSQL("DELETE FROM today WHERE _id='" + mId + "';");
                        mDBHelper.close();
                    }
                    setResult(RESULT_OK);
                    dialog.dismiss();
                    finish();
                }
            });
            alert.setIcon(R.mipmap.ic_launcher);
            alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }

        else if(v.getId()==R.id.btncancel) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
