package com.example.tsongling5.microbo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by TsongLing5 on 2016/12/16.
 */
public class StartActivity extends AppCompatActivity {

    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle saveInstanceState){
            super.onCreate(saveInstanceState);

        setContentView(R.layout.start_activity_layout);



        mAccessToken=AccessTokenKeeper.readAccessToken(this);
//        Intent loginActivity=new Intent(StartActivity.this,AuthActivity.class);
//        startActivity(loginActivity);
        if(!mAccessToken.getUid().isEmpty()) {
            Toast.makeText(StartActivity.this, mAccessToken.getUid(), Toast.LENGTH_SHORT).show();


            Intent mainActivity=new Intent(StartActivity.this,MainActivity.class);
            startActivity(mainActivity);

        }else{

            Intent authActivity=new Intent(StartActivity.this,AuthActivity.class);
            startActivity(authActivity);




//            Toast.makeText(StartActivity.this, "UnAutho", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();

    }


}
