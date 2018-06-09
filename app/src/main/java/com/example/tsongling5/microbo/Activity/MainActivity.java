package com.example.tsongling5.microbo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsongling5.microbo.AccessTokenKeeper;
import com.example.tsongling5.microbo.Constants;
import com.example.tsongling5.microbo.R;
import com.example.tsongling5.microbo.Status;
import com.example.tsongling5.microbo.StatusList;
import com.example.tsongling5.microbo.StatusesAPI;
import com.example.tsongling5.microbo.User;
import com.example.tsongling5.microbo.UsersAPI;
import com.example.tsongling5.microbo.Weibo;
import com.example.tsongling5.microbo.WeiboAdapter;
import com.example.tsongling5.microbo.models.ErrorInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;
import java.util.List;

//import com.example.tsongling5.microbo.Activity.Message;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    /** UI 元素：ListView */
    private ListView mFuncListView;
    /** 功能列表 */
    private String[] mFuncList;
    /** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;
    /** 用于获取微博信息流等操作的API */
    private StatusesAPI mStatusesAPI;

    /** 用户信息接口 */
    private UsersAPI mUsersAPI;


    private List<Weibo> weiboList= new ArrayList<>();

    private ListView weiboListView;
    private WeiboAdapter weiboAdapter;
    private View loadMore_View;
    private int loop=0;

    static private int weiboMounts=20;

    private String lastWeiboFlag;
    private FloatingActionButton fab;


    private TextView nav_name,nav_shortSummary;
    private ImageView portrait;
    private NavigationView navigationView;
    private View View;


    private User user;


    private DrawerLayout drawer;



    //Drawer Nagivation
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private ImageLoader imageLoader = ImageLoader.getInstance();


    private Handler privateMessage=new Handler() {

        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case 0x1:
                    nav_shortSummary.setText("个人简介："+user.description);
                    nav_name.setText(user.screen_name);
                    imageLoader.displayImage(user.avatar_large,portrait);
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        weiboAdapter=new WeiboAdapter(MainActivity.this,R.layout.weibolis_item,weiboList);

//        weiboAdapter=new WeiboAdapter(MainActivity.this,R.layout.weibolis_item,weiboList);





//        mDrawerLayout.openDrawer(Gravity.LEFT);

        /*
        * 加入加载更多的检测
        * */
        weiboListView=(ListView)findViewById(R.id.weiboListView);
        weiboListView.getDivider().isVisible();
        loadMore_View=LayoutInflater.from(this).inflate(R.layout.loadmore_layout,null);
        weiboListView.addFooterView(loadMore_View);
//        weiboListView.removeFooterView(loadMore_View);
        weiboListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        weiboListView.addFooterView(loadMore_View);
//                        loadData();
                        fab.setVisibility(View.INVISIBLE);
                        mStatusesAPI.friendsTimeline(0L, Long.valueOf(lastWeiboFlag).longValue()-1, 20, 1, false, 0, false, mListener);
                        fab.setVisibility(View.VISIBLE);


                        weiboListView.removeFooterView(loadMore_View);
                        Toast.makeText(MainActivity.this,"加载成功！",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

//        //获取功能列表
//        mFuncList = getResources().getStringArray(R.array.statuses_func_list);
//        // 初始化功能列表 ListView
//        mFuncListView = (ListView)findViewById(R.id.weiboListView);
//        mFuncListView.setAdapter(new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, mFuncList));
//        mFuncListView.setOnItemClickListener(this);
//        mFuncListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        // 对statusAPI实例化
        mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, mAccessToken);

        mStatusesAPI.friendsTimeline(0L, 0L, weiboMounts, 1, false, 0, false, mListener);

        //mUsersAPI = new UsersAPI(this, Constants.APP_KEY, mAccessToken);


        // 获取用户信息接口
        mUsersAPI = new UsersAPI(this, Constants.APP_KEY, mAccessToken);
        long uid = Long.parseLong(mAccessToken.getUid());
        mUsersAPI.show(uid, mListener_User);




//        /**
//         * @see {@link AdapterView.OnItemClickListener#onItemClick}
//         */
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            if (view instanceof TextView) {
//                if (mAccessToken != null && mAccessToken.isSessionValid()) {
//                    switch (position) {
//                        case 0:
//                            mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false, mListener);
//                            break;
//
//                        case 1:
//                            mStatusesAPI.mentions(0L, 0L, 10, 1, 0, 0, 0, false, mListener);
//                            break;
//
//                        case 2:
//                            mStatusesAPI.update("发送一条纯文字微博", null, null, mListener);
//                            break;
//
//                        case 3:
////                            Drawable drawable = getResources().getDrawable(R.drawable.ic_com_sina_weibo_sdk_logo);
////                            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
////                            mStatusesAPI.upload("发送一条带本地图片的微博", bitmap, null, null, mListener);
//
//                            /** 可以自行拼接参数，异步请求数据 */
//                    /*
//                    WeiboParameters wbparams = new WeiboParameters();
//                    wbparams.put("access_token", mAccessToken.getToken());
//                    wbparams.put("status",       "通过API发送微博-upload");
//                    wbparams.put("visible",      "0");
//                    wbparams.put("list_id",      "");
//                    wbparams.put("pic",          bitmap);
//                    wbparams.put("lat",          "14.5");
//                    wbparams.put("long",         "23.0");
//                    wbparams.put("annotations",  "");
//
//                    AsyncWeiboRunner.requestAsync(
//                            "https://api.weibo.com/2/statuses/upload.json",
//                            wbparams,
//                            "POST",
//                            mListener);*/
//                            break;
//
//                        case 4:
//                            String photoURL = "http://hiphotos.baidu.com/lvpics/pic/item/b25aae51bc7a3474377abe75.jpg";
//                            // 请注意：该接口暂不支持发布多图，即参数pic_id不可用（只能通过BD合作接入，不对个人申请）
//                            mStatusesAPI.uploadUrlText("发送一条带网络图片的微博", photoURL, null, null, null, mListener);
//                            break;
//
//                        default:
//                            break;
//                    }
//                } else {
////                    Toast.makeText(WBStatusAPIActivity.this,
////                            R.string.weibosdk_demo_access_token_is_empty,
////                            Toast.LENGTH_LONG).show();
//                }
//            }
//        }




/*
* 刷新的实现
* 使用FloatingActionButton实现刷新功能
* */

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                loop=0;


//                listmap.removeAll(listmap);//清空数据集
//                adapter.notifyDataSetChanged();//通知下观察者我更改了数据
//                listView.setAdapter(adapter);//重新设置adapter

                weiboListView.removeFooterView(loadMore_View);

                weiboList.clear();
                weiboAdapter.notifyDataSetChanged();
                weiboListView.setAdapter(weiboAdapter);

                mStatusesAPI.friendsTimeline(0L, 0L, 20, 1, false, 0, false, mListener);
                weiboListView.addFooterView(loadMore_View);



                Snackbar.make(view, "刷新成功！", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



//        NavigationView Drawer code
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        nav_name=(TextView)headerView.findViewById(R.id.nickname);
        nav_shortSummary=(TextView)headerView.findViewById(R.id.summary);
        portrait=(ImageView)headerView.findViewById(R.id.portrait) ;
//        nav_name.setText("hahaaaaa");


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_message) {

            Intent message=new Intent(MainActivity.this,Message.class);
//            startActivity(message);
            Toast.makeText(MainActivity.this,"消息",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_publish) {
                Toast.makeText(MainActivity.this,"发布",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_discovery) {
                 Toast.makeText(MainActivity.this,"发现",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_personal) {
            Intent personal=new Intent(MainActivity.this, PersonalActivity.class);
            personal.putExtra("User_data", user);
            startActivity(personal);
                  Toast.makeText(MainActivity.this,"个人",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this,"分享",Toast.LENGTH_SHORT).show();
        } else if(id == R.id.nav_about){

            Intent i=new Intent(MainActivity.this,Activity_About.class);
            startActivity(i);
            Toast.makeText(MainActivity.this,"关于",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_setting) {
            Toast.makeText(MainActivity.this,"设置",Toast.LENGTH_SHORT).show();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 微博 OpenAPI 回调接口。
     */
    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
//                LogUtil.i(TAG, response);
                if (response.startsWith("{\"statuses\"")) {
                    // 调用 StatusList#parse 解析字符串成微博列表对象
                    StatusList statuses = StatusList.parse(response);


//                    Weibo weibo=new Weibo(status.thumbnail_pic,status.user.screen_name,status.created_at,status.source,status.text,status.pic_urls.get(0),status.attitudes_count,status.comments_count,status.reposts_count);


                    for(int i=0;i<statuses.statusList.size();i++) {

                        Status status = statuses.statusList.get(i);

//                        if(!status.pic_urls.isEmpty()) {
//                            for (int j = 0; j < status.pic_urls.size(); j++) {
//                                status.pic_urls.get(i).replace("thumbnail", "bmiddle");
//                            }
//                        }
                       Weibo weibo = new Weibo(status);
                        weiboList.add(weibo);
                        if(i==statuses.statusList.size()-1) {
                            lastWeiboFlag = status.id;
                        }
                    }



                    if(loop==0) {


                        weiboListView.setAdapter(weiboAdapter);
                        loop=1;
                    }else{
                        weiboAdapter.notifyDataSetChanged();
                    }

                    if (statuses != null && statuses.total_number > 0) {
                        Toast.makeText(MainActivity.this,
                                "获取微博信息流成功, 条数: " + statuses.statusList.size(),
                                Toast.LENGTH_LONG).show();
                    }
                } else if (response.startsWith("{\"created_at\"")) {
                    // 调用 Status#parse 解析字符串成微博对象
                    Status status = Status.parse(response);
                    Toast.makeText(MainActivity.this,
                            "发送一送微博成功, id = " + status.id,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
//            LogUtil.e(TAG, e.getMessage());
//            ErrorInfo info = ErrorInfo.parse(e.getMessage());
//            Toast.makeText(MainActivity.this, info.toString(), Toast.LENGTH_LONG).show();
        }
    };


    /**
     * 微博 OpenAPI 回调接口。获取到User数据
     */
    private RequestListener mListener_User = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                //LogUtil.i(TAG, response);
                // 调用 User#parse 将JSON串解析成User对象
                 user = User.parse(response);
                if (user != null) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message=new Message();
                            message.what=1;
                            privateMessage.sendMessage(message);
                        }
                    }).start();




//                    shortSummary.setText(user.description);
//                    name.setText(user.screen_name);
                    Toast.makeText(MainActivity.this,
                            "获取User信息成功，用户昵称：" + user.name,
                            Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(WBUserAPIActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }
            @Override
            public void onWeiboException(WeiboException e) {
                //LogUtil.e(TAG, e.getMessage());
                ErrorInfo info = ErrorInfo.parse(e.getMessage());
                //Toast.makeText(WBUserAPIActivity.this, info.toString(), Toast.LENGTH_LONG).show();
            }
        };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
