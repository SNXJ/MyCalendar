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
import android.widget.Toast;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
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
//        showGudie1();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGudie1();
            }
        });


    }

    private void showGudie1() {

        NewbieGuide.with(this)
                .setLabel("guide01").alwaysShow(true)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tv_add).setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.guide_layout_01).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                Toast.makeText(MainActivity.this, "渲染完成1", Toast.LENGTH_SHORT).show();
                                Log.i("", "渲染完成1");
                                TextView tv_text = view.findViewById(R.id.tv_text);
                                tv_text.setText("这里添加日历");
                            }
                        }))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tv_del, HighLight.Shape.CIRCLE, 10).setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.guide_layout_01).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                Log.i("", "渲染完成2");
                                TextView tv_text = view.findViewById(R.id.tv_text);
                                tv_text.setText("这里删除日历");
                            }
                        }))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tv_update, HighLight.Shape.RECTANGLE).setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.guide_layout_01).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                Log.i("", "渲染完成3");
                                TextView tv_text = view.findViewById(R.id.tv_text);
                                tv_text.setText("这里更新日历");
                            }
                        }))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tv_query, HighLight.Shape.OVAL).setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.guide_layout_01).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                Log.i("", "渲染完成4");
                                TextView tv_text = view.findViewById(R.id.tv_text);
                                tv_text.setText("这里查询日历");
                            }
                        }))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tv_del_all).setEverywhereCancelable(false)
                        .setLayoutRes(R.layout.guide_layout_01, R.id.tv_text_close).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                Log.i("", "渲染完成5");
                                TextView tv_text = view.findViewById(R.id.tv_text);
                                TextView tv_text_close = view.findViewById(R.id.tv_text_close);
                                tv_text_close.setVisibility(View.VISIBLE);
                                tv_text.setText("这里全部删除");
                            }
                        })).setOnGuideChangedListener(new OnGuideChangedListener() {
            @Override
            public void onShowed(Controller controller) {
                Log.i("", "蒙版出现");
                Toast.makeText(MainActivity.this, "引导层出现", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRemoved(Controller controller) {
                Log.i("", "蒙版消失");
                Toast.makeText(MainActivity.this, "引导层消失", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    int addTemp = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                addTemp++;//测试写法
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
        new PermissionRequest(MainActivity.this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onPermisstionSuccessful() {
                String time = DateFormatUtil.parseTime("2018-05-29  15:20");
                EventModel eventModel = new EventModel();
                eventModel.setContent("更新提醒内容*********************xxx*************");
                eventModel.setId(5 + "");
                eventModel.setTime(time);
                CalendarEvent.updateEvent(eventModel);
            }

            @Override
            public void onPermisstionFailure() {
                Log.i("+++++++++++++++++++++", "_++++++++++++onPermisstionFailure++++++++++++++++++");
            }
        }).request(MyPermission.CALENDAR);

    }

    private void queryCalendar() {
        new PermissionRequest(MainActivity.this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onPermisstionSuccessful() {
                List<EventModel> list = CalendarEvent.queryEvents();
                if (null == list) return;
                if (list.size() <= 0) return;
                for (int i = 0; i < list.size(); i++) {
                    Log.i("+++++++++++++++++++++", list.get(i).getId() + "=id+++++++++++提醒的内容++++++++++++++++++==" + list.get(i).getContent());

                }
            }

            @Override
            public void onPermisstionFailure() {
                Log.i("+++++++++++++++++++++", "_++++++++++++onPermisstionFailure++++++++++++++++++");
            }
        }).request(MyPermission.CALENDAR);

    }

    private void delAllCalendar() {
        new PermissionRequest(MainActivity.this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onPermisstionSuccessful() {
                CalendarEvent.deleteAllEvent();
            }

            @Override
            public void onPermisstionFailure() {
                Log.i("+++++++++++++++++++++", "_++++++++++++onPermisstionFailure++++++++++++++++++");
            }
        }).request(MyPermission.CALENDAR);


    }

    private void delCalendar() {
        new PermissionRequest(MainActivity.this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onPermisstionSuccessful() {
                CalendarEvent.deleteEvent("5");
            }

            @Override
            public void onPermisstionFailure() {
                Log.i("+++++++++++++++++++++", "_++++++++++++onPermisstionFailure++++++++++++++++++");
            }
        }).request(MyPermission.CALENDAR);


    }

    private void addCalendar(final String date) {
        new PermissionRequest(MainActivity.this, new PermissionRequest.PermissionCallback() {
            @Override
            public void onPermisstionSuccessful() {
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
