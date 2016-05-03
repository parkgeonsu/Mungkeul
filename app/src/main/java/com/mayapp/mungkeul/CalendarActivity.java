package com.mayapp.mungkeul;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.marcohc.robotocalendar.RobotoCalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CalendarActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {

    private Context context;
    private RobotoCalendarView robotoCalendarView;
    private int currentMonthIndex;
    private Calendar currentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        // Gets the calendar from the view
        robotoCalendarView = (RobotoCalendarView) findViewById(R.id.robotoCalendarPicker);

        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        // Initialize the RobotoCalendarPicker with the current index and date
        currentMonthIndex = 0;
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        // Mark current day
        robotoCalendarView.markDayAsCurrentDay(currentCalendar.getTime());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDateSelected(Date date) {

        // Mark calendar day
        robotoCalendarView.markDayAsSelectedDay(date);

        // Mark that day with random colors
        final Random random = new Random(System.currentTimeMillis());
        final int style = random.nextInt(3);
        switch (style) {
            case 0:
                robotoCalendarView.markFirstUnderlineWithStyle(RobotoCalendarView.BLUE_COLOR, date);
                break;
            case 1:
                robotoCalendarView.markSecondUnderlineWithStyle(RobotoCalendarView.GREEN_COLOR, date);
                break;
            case 2:
                robotoCalendarView.markFirstUnderlineWithStyle(RobotoCalendarView.RED_COLOR, date);
                break;
            default:
                break;
        }

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            Intent intent = new Intent(CalendarActivity.this, BiblePassageActivity.class);

            String month = "";
            String day = "";
            String dayOfMonth = "";

            month = String.valueOf(date.getMonth()+1);
            day = String.valueOf(date.getDate());
            Log.d("userSelectDate1 : ", month+day);
            if(Integer.valueOf(month) < 10) month = "0"+month;
            if(Integer.valueOf(day) < 10) day = "0"+day;

            dayOfMonth = month+day;
            Log.d("userSelectDate : ", dayOfMonth);

            intent.putExtra("userSelectDate", dayOfMonth);
            startActivity(intent);
        } else {
            Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        updateCalendar();
    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        updateCalendar();
    }

    private void updateCalendar() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        robotoCalendarView.initializeCalendar(currentCalendar);
    }

}
