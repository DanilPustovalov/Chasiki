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
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Time time;
    Handler handler;
    Runnable r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time=new Time();
        r=new Runnable() {
            @Override
            public void run() {
                time.setToNow();
                DrawingView dv = new DrawingView(MainActivity.this,
                        time.hour,time.minute,time.second,time.weekDay,time.monthDay,getBatteryLevel()
                        );
                setContentView(dv);
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
    public class DrawingView extends View {
        //Typeface tf;
        Paint textPaint,textBackPaint;
        int hours,minutes,seconds,weekday, date;
        float battery;
        @ColorInt int color= Color.parseColor("#DDDDDD");
        @ColorInt int color1= Color.parseColor("#707070");
        public DrawingView(Context context,int hours, int minutes, int seconds,int weekday,int date,float battery) {
            super(context);
            textPaint=new Paint();
            textPaint.setColor(color);
            textPaint.setAntiAlias(true);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(getResources().getDimension(R.dimen.text_size));
            textBackPaint=new Paint();
            textBackPaint.setColor(color1);
            textBackPaint.setAntiAlias(true);
            textBackPaint.setTextAlign(Paint.Align.CENTER);
            textBackPaint.setTextSize(getResources().getDimension(R.dimen.text_size));
            this.hours=hours+3;
            this.minutes=minutes;
            this.seconds=seconds;
            this.weekday=weekday;
            this.date=date;
            this.battery=battery;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float width=canvas.getWidth();
            float height=canvas.getHeight();
            float centerX=width/2f;
            float centerY=height/2f;
            int cur_hour= hours;
            if(cur_hour>23){
                cur_hour=0;
            }
            String text=String.format("%02d:%02d:%02d",cur_hour,minutes,seconds);
            String[] day_of_week={"ПОН","ВТ","СР","ЧТ","ПТ","СБ","ВС"};
            String text2=String.format("ДАТА: %s %d",day_of_week[weekday],date);
            String batteryLevel = "БАТАРЕЯ: "+(int) battery+ "%";
            canvas.drawColor(Color.BLACK);
            textPaint.setColor(color1);
            textPaint.setTextSize(getResources().getDimension(R.dimen.text_size));
            canvas.drawText(text,centerX,centerY,textPaint);
            textPaint.setTextSize(getResources().getDimension(R.dimen.text_size_small));
            canvas.drawText(batteryLevel+" "+text2,centerX,centerY+getResources().getDimension(R.dimen.text_size_small),textPaint);

        }
    }
}