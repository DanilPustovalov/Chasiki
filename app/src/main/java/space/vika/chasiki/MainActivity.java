package space.vika.chasiki;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Time time;
    Handler handler;
    Runnable r;
    Sensor light;
    SensorManager sensorManager;
    SensorEventListener sensorEventListener;

    int hours,minutes,seconds,weekday, date;
    float battery;
    @ColorInt int color= Color.parseColor("#DDDDDD");
    @ColorInt int color1= Color.parseColor("#707070");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time=new Time();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(sensorEventListener, light, sensorManager.SENSOR_DELAY_NORMAL);

        TextView textView = findViewById(R.id.Time);
        TextView textView2 = findViewById(R.id.Date);
        TextView textView3 = findViewById(R.id.Battery);
        r=new Runnable() {
            @Override
            public void run() {
                time.setToNow();

                hours=time.hour+3;
                minutes=time.minute;
                seconds = time.second;
                weekday = time.weekDay;
                date= time.monthDay;
                battery=getBatteryLevel();

                String text=String.format("%02d:%02d:%02d",hours,minutes,seconds);
                String[] day_of_week={"ПОН","ВТ","СР","ЧТ","ПТ","СБ","ВС"};
                String text2=String.format("ДАТА: %s %d",day_of_week[weekday],date);
                String batteryLevel = "БАТАРЕЯ: "+(int) battery+ "%";

                textView.setTextColor(color);
                textView.setTextSize(36);
                textView.setText(text);
                textView2.setTextColor(color);
                textView2.setTextSize(24);
                textView2.setText(text2);
                textView3.setTextColor(color);
                textView3.setTextSize(24);
                textView3.setText(batteryLevel);
                handler.postDelayed(r,1000);
            }
        };
        handler= new Handler();
        handler.postDelayed(r,1000);
    }
    public float getBatteryLevel(){
        Intent batteryIntent=registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level=batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale=batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        if(level==-1 || scale==-1){
            return 50.0f;
        }
        return ((float) level/(float) scale)* 100.0f;
    }

}