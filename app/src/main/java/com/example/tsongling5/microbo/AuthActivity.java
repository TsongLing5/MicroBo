package com.example.tsongling5.microbo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by TsongLing5 on 2016/8/2.
 */
public class AuthActivity extends AppCompatActivity {
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Button mLoginButton;
    private Oauth2AccessToken mAccessToken;
    private Context mContext;
//        private WeiboAuthListener mAuthListener;
    private AuthListener mLoginListener = new AuthListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authlogin_layout);


        mContext = MyApplication.getContext();
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);


        if (null == mSsoHandler && mAuthInfo != null) {
            mSsoHandler = new SsoHandler(AuthActivity.this, mAuthInfo);
            Toast.makeText(AuthActivity.this, " SsoHolder is  null", Toast.LENGTH_SHORT).show();
        }


        if (mSsoHandler != null) {
            mSsoHandler.authorize(new AuthListener());
            Toast.makeText(AuthActivity.this, " SsoHolder is  innull", Toast.LENGTH_SHORT).show();
        }



    }
    /**
     * 登入按钮的监听器，接收授权结果。
     */
    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);

            if (accessToken.isSessionValid()) {

                AccessTokenKeeper.writeAccessToken(AuthActivity.this, accessToken);
                Intent mainActivity=new Intent(AuthActivity.this,MainActivity.class);
                startActivity(mainActivity);
                Toast.makeText(AuthActivity.this, "Auth successful",Toast.LENGTH_LONG).show();
            } else{
                String code=values.getString("code","");
                Toast.makeText(AuthActivity.this, "Erroe sign",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onWeiboException(WeiboException e) {
//            Toast.makeText(WBLoginLogoutActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(AuthActivity.this,
                    "Exit", Toast.LENGTH_SHORT).show();
        }


    }



    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }


    }
}
