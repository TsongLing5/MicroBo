package com.example.tsongling5.microbo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsongling5.microbo.R;

/**
 * Created by TsongLing5 on 2017/2/12.
 */
public class PersonalActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mine_activity_layout);
        TextView  t=(TextView)findViewById(R.id.blog_count);
        t.setText("777");
        LinearLayout l=(LinearLayout)findViewById(R.id.Linearlayout_blog_count);
        l.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalActivity.this,"666666",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
