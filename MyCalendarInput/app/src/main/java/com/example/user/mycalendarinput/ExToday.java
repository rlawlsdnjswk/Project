package com.example.user.mycalendarinput;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


    public class ExToday extends AppCompatActivity implements AdapterView.OnItemClickListener, OnClickListener {
        MyDBHelper mDBHelper;
        String today;
        Cursor cursor;
        SimpleCursorAdapter adapter;

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ex_today);

            Intent intent = getIntent();
            today = intent.getStringExtra("Param1");

            mDBHelper = new MyDBHelper(this, "Today.db", null, 1);
            SQLiteDatabase db = mDBHelper.getReadableDatabase();

            cursor = db.rawQuery(
                    "SELECT * FROM today WHERE date = '" + today + "'", null);

            adapter = new SimpleCursorAdapter(this,
                    R.layout.item, cursor, new String[] {
                    "title", "time", "place", "memo"}, new int[] { R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4
            });

            ListView list = (ListView) findViewById(R.id.list1);
            list.setAdapter(adapter);
            list.setOnItemClickListener(this);

            mDBHelper.close();

            Button btn = (Button) findViewById(R.id.btnadd);
            btn.setOnClickListener(this);

        }


        public boolean onCreateOptionsMenu(Menu menu) {
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle(today);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId()==R.id.action_monthview){
                finish();
            }
            else if(item.getItemId()==R.id.action_weekview){

            }
            else if(item.getItemId()==R.id.action_dayview){

            }
            else if(item.getItemId()==R.id.action_add) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("일정 추가하기");
                alert.setMessage("어느 날짜에 추가할까요?");
                final EditText editAlertDate = new EditText(this);
                alert.setView(editAlertDate);
                editAlertDate.setText(today);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = editAlertDate.getText().toString();
                        Intent intentD = new Intent(getApplicationContext(), Detail.class);
                        intentD.putExtra("ParamDate", value);
                        startActivityForResult(intentD, 1);
                        dialog.dismiss();
                        finish();
                    }
                });
                alert.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });
                alert.show();
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(this, DetailShow.class);
            intent.putExtra("ParamID", cursor.getInt(0));
            startActivityForResult(intent, 1);
        }

        @Override

        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(this, Detail.class);
            intent.putExtra("ParamDate", today);
            startActivityForResult(intent, 1);
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub
            // super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case 0:
                case 1:
                    if (resultCode == RESULT_OK) {
                        adapter.notifyDataSetChanged();
                        SQLiteDatabase db = mDBHelper.getWritableDatabase();
                        cursor = db.rawQuery("SELECT * FROM today WHERE date = '" + today + "'", null);
                        adapter.changeCursor(cursor);
                        mDBHelper.close();
                    }
                    break;
            }
        }


    }