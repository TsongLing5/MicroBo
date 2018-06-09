package com.example.tsongling5.microbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsongling5.microbo.R;
import com.example.tsongling5.microbo.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import static com.example.tsongling5.microbo.R.id.NameLayoutImageView;

/**
 * Created by TsongLing5 on 2017/2/12.
 */
public class PersonalActivity extends AppCompatActivity{


    private ImageLoader imageLoader = ImageLoader.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mine_activity_layout);

        Intent intent=getIntent();
        User user_data= (User)intent.getSerializableExtra("User_data");


        ImageView portrait=(ImageView)findViewById(NameLayoutImageView);
        imageLoader.displayImage(user_data.avatar_large,portrait);

        TextView name=(TextView)findViewById(R.id.NameLayoutNameTextView);
        name.setText(user_data.name);


        TextView sumary=(TextView)findViewById(R.id.NameLayoutSummary);
        sumary.setText(user_data.description);

        TextView location=(TextView)findViewById(R.id.NameLayoutLocationTextView);
        location.setText(user_data.location);



        TextView  tstatuses_count=(TextView)findViewById(R.id.statuses_count);
//        Toast.makeText(this, user_data.location, Toast.LENGTH_SHORT).show();          //For debug
        tstatuses_count.setText(String.valueOf(user_data.statuses_count));



        TextView friends_count=(TextView)findViewById(R.id.follow_count);
        friends_count.setText(String.valueOf(user_data.friends_count));

        TextView follows_count=(TextView)findViewById(R.id.fans_count);
        follows_count.setText(String.valueOf(user_data.followers_count));


        LinearLayout l=(LinearLayout)findViewById(R.id.Linearlayout_blog_count);
        l.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalActivity.this,"666666",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
