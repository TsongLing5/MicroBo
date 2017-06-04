package com.example.tsongling5.microbo;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by TsongLing5 on 2017/2/12.
 */
public class CustomizeButtonLayout extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    public CustomizeButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.button_layout,this);


        imageView=(ImageView)findViewById(R.id.Customize_BImage);
        textView=(TextView)findViewById(R.id.Customize_BTextView);
    }
    public void setImage(int  resource){
        imageView.setImageResource(resource);

    }


    public void setAmount(String string){
        textView.setText(string);
    }

    public interface Amount {
        void setAmount();
    }
}
