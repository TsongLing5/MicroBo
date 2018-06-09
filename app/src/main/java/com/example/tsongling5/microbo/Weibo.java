package com.example.tsongling5.microbo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by TsongLing5 on 2016/12/24.
 */
public class Weibo implements Serializable{

    private String weiboId;                  //微博ID
    private String userImage;             //博主头像
    private String userName;              //博主
    private String publishTime;          //微博发布时间
    private String publishSource;        //微博发布源
    private String content;              //微博文本
    private String imageUrl;             //微博图片
    private int attitudeCount;           //赞数
    private int commentCount;            //评论数
    private int repostCount;             //转发数
    private ArrayList<String> picUrl =  new ArrayList<>();


    public Weibo(String userImage,String userName,String publishTime,String publishSource,String content,String imageUrl,int attitudeCount,int commentCount,int repostCount){
        this.userImage=userImage;
        this.userName=userName;
        this.publishTime=publishTime;
        this.publishSource=publishSource;
        this.content=content;
        this.imageUrl=imageUrl;
        this.attitudeCount=attitudeCount;
        this.commentCount=commentCount;
        this.repostCount=repostCount;

    }

    public Weibo( Status status) {
        this.weiboId=status.id;
        this.userImage=status.user.avatar_large;
        this.userName=status.user.screen_name;
        this.publishTime=status.created_at;
        this.publishSource=status.source;
        this.content=status.text;
//        this.imageUrl=status.pic_urls.get(0);
        this.attitudeCount=status.attitudes_count;
        this.commentCount=status.comments_count;
        this.repostCount=status.reposts_count;
        this.picUrl=status.pic_urls;


    }

//    public Weibo(int weiboId,String userImage,String userName,String publishTime,String publishSource,String content,String imageUrl,int attitudeCount,int commentCount,int repostCount){
//
//    }


    public void setUserImage(String userImage){
        this.userImage=userImage;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }

    public void setPublishTime(String publishTime){
        this.publishTime=publishTime;
    }

    public void setPublishSource(String publishSource){
        this.publishSource=publishSource;
    }

    public void setWeiboContent(String content){
        this.content=content;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }

    public void setAttitudeCount(int attitudeCount){
        this.attitudeCount=attitudeCount;
    }

    public void setCommentCount(int commentCount){
        this.commentCount=commentCount;
    }

    public void setRepostCount(int repostCount){
        this.repostCount=repostCount;
    }
/*
*
* the method of get
*
* */
    public String getWeiboId(){
    return this.weiboId;

}


    public String getUserImage(){
        return this.userImage;

    }

    public String getUserName(){
        return this.userName;
    }

    public String getPublishTime(){
        return this.publishTime;
    }

    public String getPublishSource(){
        return this.publishSource;
    }

    public String getWeiboContent(){
        return this.content;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }


    public ArrayList<String> getPicUrl(){return this.picUrl;}

    public int getAttitudeCount(){
        return this.attitudeCount;
    }

    public int getCommentCount(){
        return this.commentCount;
    }

    public int getRepostCount(){
        return this.repostCount;
    }




    private ArrayList<String> changePic2Bmiddle(ArrayList<String> pics){

        ArrayList<String> newPic = null;
        String temp;
        for(int i=0;i<pics.size();i++)
        {
            temp=pics.get(i);
            temp=temp.replace("thumbnail","bmiddle");
//        pics.set(i,pics.get(i).replace("thumbnail","bmiddle"));
            newPic.set(i,temp);

        }
        return newPic;
    }

}
