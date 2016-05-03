package com.mayapp.mungkeul;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 4000);
    }

    private class splashhandler implements  Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), CalendarActivity.class));
            MainActivity.this.finish();
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
