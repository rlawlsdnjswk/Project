package com.example.user.mycalendarinput;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.user.mycalendarinput.R.id.time;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemClickListener {
    ArrayList<String> mItems;
    ArrayAdapter<String> adapter;
    TextView textYear;
    TextView textMon;
    int year;
    int mon;






        /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        do {
            Intent intent = new Intent(getApplicationContext(), start.class);
            startActivity(intent);
        }
        while(false);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //액션바를 강제로 캘린더로 네이밍함, 아마 월,주별 보기 프레이먼트에서도 각각 지정해줘야할 듯
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("캘린더");


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //액션바 메뉴 구현 부분
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_monthview:
        //        startActivity(new Intent(this, SubActivity.class));
                return true;
            case R.id.action_weekview:
        //        startActivity(new Intent(this, NavDrawerActivity.class));
                 return true;

            case R.id.action_dayview:
                //        startActivity(new Intent(this, NavDrawerActivity.class));
                return true;
            case R.id.action_add:
                //        startActivity(new Intent(this, NavDrawerActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_main);

        textYear = (TextView) this.findViewById(R.id.yearedit);
        textMon = (TextView) this.findViewById(R.id.monthedit);
        mItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mItems);

        GridView gird = (GridView) this.findViewById(R.id.grid1);
        gird.setAdapter(adapter);
        gird.setOnItemClickListener(this);

        Date date = new Date();// 오늘에 날짜를 세팅 해준다.
        int year = date.getYear() + 1900;
        int mon = date.getMonth() + 1;
        textYear.setText(year + "");
        textMon.setText(mon + "");
        fillDate(year, mon);
        Button btnmove = (Button) this.findViewById(R.id.gotodate);
        Button next = (Button) this.findViewById(R.id.next);
        Button priv = (Button) this.findViewById(R.id.priv);

        this.year = Integer.parseInt(textYear.getText().toString());
        this.mon = Integer.parseInt(textMon.getText().toString());

        btnmove.setOnClickListener(this);
        next.setOnClickListener(this);
        priv.setOnClickListener(this);


    }






    @Override
    public void onClick(View arg0) {
// TODO Auto-generated method stub
        if (arg0.getId() == R.id.gotodate) {
            year = Integer.parseInt(textYear.getText().toString());
            mon = Integer.parseInt(textMon.getText().toString());
            fillDate(year, mon);
        }
        else if(arg0.getId() == R.id.priv){
            mon -= 1;
            if(mon<=0){
                year -= 1;
                mon=12;
            }
            fillDate(year, mon);
            textYear.setText(Integer.toString(year));
            textMon.setText(Integer.toString(mon));
        }
        else if(arg0.getId() == R.id.next){
            mon += 1;
            if(mon>=13){
                year += 1;
                mon=1;
            }
            fillDate(year, mon);
            textYear.setText(Integer.toString(year));
            textMon.setText(Integer.toString(mon));
        }

    }

    private void fillDate(int year, int mon) {
        mItems.clear();
        mItems.add("일");
        mItems.add("월");
        mItems.add("화");
        mItems.add("수");
        mItems.add("목");
        mItems.add("금");
        mItems.add("토");
        Date current = new Date(year - 1900, mon - 1, 1);
        int day = current.getDay(); // 요일도 int로 저장.
        for (int i = 0; i < day; i++) {
            mItems.add("");
        }
        current.setDate(32);// 32일까지 입력하면 1일로 바꿔준다.
        int last = 32 - current.getDate();
        for (int i = 1; i <= last; i++) {
            mItems.add(i + "");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
// TODO Auto-generated method stub
        if (mItems.get(arg2).equals("")) {
            ;
        } else {
            Intent intent = new Intent(this, ExToday.class);//해당 일을 눌렸을때
            intent.putExtra("Param1", textYear.getText().toString() + "/"
                    + textMon.getText().toString() + "/" + mItems.get(arg2));
            startActivity(intent);
        }
    }
}