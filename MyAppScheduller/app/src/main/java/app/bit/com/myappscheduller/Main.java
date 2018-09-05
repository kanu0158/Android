package app.bit.com.myappscheduller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context this__ = Main.this;
        class MyDate{
            String year,month,day;
        }
        TextView today = findViewById(R.id.today);
        CalendarView calender = findViewById(R.id.calender);
        TimePicker time = findViewById(R.id.time);
        TextView year = findViewById(R.id.year);
        TextView month = findViewById(R.id.month);
        TextView day = findViewById(R.id.day);
        TextView hour = findViewById(R.id.hour);
        TextView minute = findViewById(R.id.minute);
        calender.setVisibility(View.VISIBLE);
        time.setVisibility(View.INVISIBLE);
        final MyDate m = new MyDate();
        today.setText("Reservation \n"+new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초").format(new Date()));

        findViewById(R.id.rdoCalendar).setOnClickListener(
                (View v)->{
                    calender.setVisibility(View.VISIBLE);
                    time.setVisibility(View.INVISIBLE);
                }
        );
        findViewById(R.id.rdoTime).setOnClickListener(
                (View v)->{
                    calender.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.VISIBLE);
                }
        );
        findViewById(R.id.btnEnd).setOnClickListener(
                (View v)->{
                    Toast.makeText(this__,"btnEnd 클릭",Toast.LENGTH_LONG).show();
                    year.setText(m.year);
                    month.setText(m.month);
                    day.setText(m.day);
                    hour.setText(time.getHour()+"");
                    minute.setText(time.getMinute()+"");
                }
        );
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(this__,"year : "+year,Toast.LENGTH_SHORT).show();
                Toast.makeText(this__,"month : "+month,Toast.LENGTH_SHORT).show();
                Toast.makeText(this__,"dayOfMonth : "+dayOfMonth,Toast.LENGTH_SHORT).show();
                //date = year+" "+(month+1)+" "+dayOfMonth;
                m.year = year+"";
                m.month = (month+1) + "";
                m.day = dayOfMonth+"";
            }
        });
    }
}
