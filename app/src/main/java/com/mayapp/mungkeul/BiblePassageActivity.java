package com.mayapp.mungkeul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class BiblePassageActivity extends AppCompatActivity {

    private String passageUrl = null;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible_passage);

        loadingThreadDialog();
        try {
            loadingThreadPassage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void loadingThreadPassage() {
        Intent intent = getIntent();
        String date = intent.getStringExtra("userSelectDate");
        String passageUrl = "https://dl.dropboxusercontent.com/s/pd41dah2sjjzv1t/"+date+".png";

        Log.d("passageUrl", passageUrl);

        ImageView ivExample = (ImageView)findViewById(R.id.imgViewer);
        ImageLoaderTask imageLoaderTask = new ImageLoaderTask(
                ivExample,
                passageUrl
        );
        imageLoaderTask.execute();
    }

    void loadingThreadDialog() {
        //Toast.makeText(getApplicationContext(), "오늘도 말씀을 통해 은혜받으세요.", Toast.LENGTH_LONG).show();

        dialog = ProgressDialog.show(BiblePassageActivity.this, "", "오늘도 말씀을 통해 은혜받으세요.", true, false);
        Thread thread = new Thread(new Runnable() {
            private static final int LOADING_TIME = 5000;
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(0, LOADING_TIME);
            }
        });
        thread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dialog.dismiss(); // 다이얼로그 삭제
        }
    };

}