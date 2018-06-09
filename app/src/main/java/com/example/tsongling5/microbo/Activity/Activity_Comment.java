package com.example.tsongling5.microbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tsongling5.microbo.R;
import com.example.tsongling5.microbo.Weibo;

public class Activity_Comment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__comment);
        Intent i=getIntent();
        Weibo weibo=(Weibo)i.getSerializableExtra("Weibo_data");
    }
}
