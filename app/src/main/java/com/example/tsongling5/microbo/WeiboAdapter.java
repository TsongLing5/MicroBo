package com.example.tsongling5.microbo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsongling5.microbo.utils.ScreenTools;
import com.example.tsongling5.microbo.view.NineGridTestLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TsongLing5 on 2016/12/26.
 */
public class WeiboAdapter extends ArrayAdapter<Weibo>{
    private int resourceId;
    private  Context context;
    private PopupWindow popupWindow;
    private View moreView;

    private TextView favorited,cancelFollow,report;


    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_download)
            .showImageOnFail(R.drawable.ic_error)
            .showImageForEmptyUri(R.drawable.ic_help)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build();


    public WeiboAdapter(Context context, int resource, List<Weibo> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.context=context;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Weibo weibo=getItem(position);
        View view;
        ViewHolder viewHolder;


        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder =new ViewHolder();

            viewHolder.userImage=(ImageView)view.findViewById(R.id.imageView_User);
            viewHolder.userName=(TextView)view.findViewById(R.id.textView_UserName);
            viewHolder.time=(TextView)view.findViewById(R.id.textView_Time);
            viewHolder.source=(TextView)view.findViewById(R.id.textView_Source);
            viewHolder.content=(TextView)view.findViewById(R.id.textView_Content);
            viewHolder.userName=(TextView)view.findViewById(R.id.textView_UserName);
            viewHolder.attitude=(TextView)view.findViewById(R.id.attitudes_count);
            viewHolder.repost=(TextView)view.findViewById(R.id.share_count);
            viewHolder.more=(TextView)view.findViewById(R.id.textView_More) ;
            viewHolder.comment=(TextView)view.findViewById(R.id.comments_count);
            viewHolder.repostLayout=(RelativeLayout)view.findViewById(R.id.shareComponent);
            viewHolder.commentLayout=(RelativeLayout)view.findViewById(R.id.commentComponent);
            viewHolder.repostLayout=(RelativeLayout)view.findViewById(R.id.likeComponent);
            viewHolder.layout=(NineGridTestLayout) view.findViewById(R.id.ninegridLayout);
//            viewHolder.nineGridLayout=( NineGridTestModel)convertView.findViewById(R.id.ninegridLayout);



            view.setTag(viewHolder);
        }else{
            view =convertView;
            viewHolder=(ViewHolder)view.getTag();
        }



//        viewHolder.layout.setIsShowAll(false);
//        if(weibo.getPicUrl() != null && !weibo.getPicUrl().isEmpty()) {
////            viewHolder.layout.setUrlList(weibo.getPicUrl());
//        }




//        ImageView i=(ImageView)view.findViewById(R.id.item_comment);
//        i.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "点击了评论", Toast.LENGTH_LONG).show();
//            }
//        });


//        LinearLayout linear=(LinearLayout)findViewByID(R.id.reposts_layou);

        viewHolder.userName.setText(weibo.getUserName());
        viewHolder.content.setText(weibo.getWeiboContent());
        viewHolder.time.setText(weibo.getPublishTime());
        viewHolder.source.setText("来自于火星的 "+weibo.getPublishSource().substring(weibo.getPublishSource().indexOf(">")+1).replace("</a>",""));//删除无关字符
        viewHolder.attitude.setText(String.valueOf(weibo.getAttitudeCount()));
        viewHolder.repost.setText(String.valueOf(weibo.getRepostCount()));
        viewHolder.comment.setText(String.valueOf(weibo.getCommentCount()));
        viewHolder.repostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "转发", Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.commentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "评论", Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.repostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "赞赞赞", Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "more", Toast.LENGTH_LONG).show();
                showMoreOption(v,weibo);
            }
        });




        imageLoader.displayImage(weibo.getUserImage(), viewHolder.userImage, options);


        viewHolder.layout.setIsShowAll(false);
        viewHolder.layout.setSpacing(8);
//        ArrayList<String> BPic=getBPics(weibo.getPicUrl());
        viewHolder.layout.setUrlList(getBPics(weibo.getPicUrl()));
//        Integer.toString(i)
        return view;




    }



    private void handlerOneImage(ViewHolder viewHolder, Image image) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(context);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = viewHolder.ivOne.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        viewHolder.ivOne.setLayoutParams(layoutparams);
        viewHolder.ivOne.setClickable(true);
        viewHolder.ivOne.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        viewHolder.ivOne.setImageUrl(image.getUrl());

    }

    class ViewHolder{
        ImageView userImage;
        TextView userName;
        TextView time;
        TextView source;
        TextView content;
        TextView attitude;
        TextView comment;
        TextView repost;
        TextView more;
        RelativeLayout likeLayout;
        RelativeLayout commentLayout;
        RelativeLayout repostLayout;

        NineGridTestLayout layout;
//        public ViewHolder(View view) {
//            layout = (NineGridTestLayout) view.findViewById(R.id.layout_nine_grid);
//        }
//        NineGridTestModel nineGridLayout;
//        public NineGridlayout ivMore;
        public CustomImageView ivOne;
    }

    private ArrayList<String> getBPics(ArrayList<String> picsUrl){
        String BPictemp;
        ArrayList<String> BPics = new ArrayList<>();
//        if(picsUrl != null && picsUrl.isEmpty()) {
        if(picsUrl != null) {
//        if(picsUrl.size()>0) {
                for (int i = 0; i < picsUrl.size(); i++) {
                    BPictemp=picsUrl.get(i).replace("thumbnail", "bmiddle");
//                picsUrl.get(i).replace("thumbnail", "bmiddle");
                    BPics.add(BPictemp);
                }

        }

        return BPics;
    }

    private void showMoreOption(View parent,Weibo data){

        if(popupWindow == null){
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            moreView=layoutInflater.inflate(R.layout.activity_more_list,null);

            favorited =(TextView)moreView.findViewById(R.id.favorited);
            cancelFollow =(TextView)moreView.findViewById(R.id.cancelFollow);
            report=(TextView)moreView.findViewById(R.id.report);

            popupWindow=new PopupWindow(moreView,450,400);
        }

        favorited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "收藏", Toast.LENGTH_LONG).show();
            }
        });








        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(null);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
//        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
//                - popupWindow.getWidth() / 4;


//        int xPos=100;
//        Log.i("coder", "windowManager.getDefaultDisplay().getWidth()/2:"
//                + windowManager.getDefaultDisplay().getWidth() / 2);
//        //
//        Log.i("coder", "popupWindow.getWidth()/2:" + popupWindow.getWidth() / 2);
//
//        Log.i("coder", "xPos:" + xPos);

        popupWindow.showAsDropDown(parent, -410,20);

    }


}
