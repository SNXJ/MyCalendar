package com.snxj.calendarnotify;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.snxj.calendarnotify.Model.EventModel;
import com.snxj.calendarnotify.Utils.DateFormatUtil;
import com.snxj.calendarnotify.Utils.MyPermission;
import com.snxj.calendarnotify.Utils.PermissionRequest;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_add, tv_del, tv_del_all, tv_query, tv_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_del = (TextView) findViewById(R.id.tv_del);
        tv_del_all = (TextView) findViewById(R.id.tv_del_all);
        tv_query = (TextView) findViewById(R.id.tv_query);
        tv_update = (TextView) findViewById(R.id.tv_update);
        tv_add.setOnClickListener(this);
        tv_del.setOnClickListener(this);
        tv_del_all.setOnClickListener(this);
        tv_query.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new PermissionRequest(MainActivity.this, new PermissionRequest.PermissionCallback() {
//                    @Override
//                    public void onPermisstionSuccessful() {
//                        Log.i("+++++++++++++++++++++", "_++++++++++++add++++++++++++++++++");
//
//                        String time = DateFormatUtil.parseTime("2018-05-24  13:10");
//                        EventModel eventModel = new EventModel();
//                        eventModel.setContent("提醒内容");
//                        eventModel.setId("89");
//                        eventModel.setTime(time);
////                        CalendarEvent.insertEvent(eventModel);
//
//                        CalendarManager.addCalendarEvent(MainActivity.this, "标题", "内容", Long.parseLong(time));
//                        //TODO 删除
//                    }
//
//                    @Override
//                    public void onPermisstionFailure() {
//                        Log.i("+++++++++++++++++++++", "_++++++++++++onPermisstionFailure++++++++++++++++++");
//                    }
//                }).request(MyPermission.CALENDAR);


            }
        });


    }

    int addTemp = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                addTemp++;
                addCalendar("2018-05-2" + addTemp + "  15:20");
                break;
            case R.id.tv_del:
                delCalendar();
                break;
            case R.id.tv_del_all:
                delAllCalendar();
                break;
            case R.id.tv_query:
                queryCalendar();
                break;
            case R.id.tv_update:
                upDateCalendar();
                break;
        }

    }

    private void upDateCalendar() {
        String time = DateFormatUtil.parseTime("2018-05-29  15:20");
        EventModel eventModel = new EventModel();
        eventModel.setContent("更新提醒内容*********************xxx*************");
        eventModel.setId(5 + "");
        eventModel.setTime(time);
        CalendarEvent.updateEvent(eventModel);
    }

    private void queryCalendar() {
        List<EventModel> list = CalendarEvent.queryEvents();
        if (null == list) return;
        if (list.size() <= 0) return;
        for (int i = 0; i < list.size(); i++) {
            Log.i("+++++++++++++++++++++", list.get(i).getId() + "=id+++++++++++提醒的内容++++++++++++++++++==" + list.get(i).getContent());

        }
    }

    private void delAllCalendar() {
        CalendarEvent.deleteAllEvent();
    }

    private void delCalendar() {
        CalendarEvent.deleteEvent("5");
        Log.i("+++++++++++++++++++++", "_++++++++++++del++++++++++++++++++" + 5);
    }

    private void addCalendar(final String date) {
        new PermissionRequest(MainActivity.this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onPermisstionSuccessful() {
                Log.i("+++++++++++++++++++++", "_++++++++++++add++++++++++++++++++" + addTemp);
                String time = DateFormatUtil.parseTime(date);
                EventModel eventModel = new EventModel();
                eventModel.setContent("提醒内容**********************************" + addTemp);
                eventModel.setId(addTemp + "");
                eventModel.setTime(time);
                CalendarEvent.insertEvent(eventModel);
            }

            @Override
            public void onPermisstionFailure() {
                Log.i("+++++++++++++++++++++", "_++++++++++++onPermisstionFailure++++++++++++++++++");
            }
        }).request(MyPermission.CALENDAR);

    }


}
