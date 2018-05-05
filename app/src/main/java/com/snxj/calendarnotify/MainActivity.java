package com.snxj.calendarnotify;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.snxj.calendarnotify.Model.EventModel;
import com.snxj.calendarnotify.Utils.DateFormatUtil;
import com.snxj.calendarnotify.Utils.MyPermission;
import com.snxj.calendarnotify.Utils.PermissionRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PermissionRequest(MainActivity.this, new PermissionRequest.PermissionCallback() {
                    @Override
                    public void onPermisstionSuccessful() {
                        Log.i("+++++++++++++++++++++", "_++++++++++++add++++++++++++++++++");

                        String time = DateFormatUtil.parseTime("2018-05-07  10:20");
                        EventModel eventModel = new EventModel();
                        eventModel.setContent("添加的提醒内容");
                        eventModel.setId("89");
                        eventModel.setTime(time);
                        CalendarEvent.insertEvent(eventModel);
                        //TODO 删除

                    }

                    @Override
                    public void onPermisstionFailure() {
                        Log.i("+++++++++++++++++++++", "_++++++++++++onPermisstionFailure++++++++++++++++++");
                    }
                }).request(MyPermission.CALENDAR);


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
