package com.example.hammadhanif.availabilityapplication.CustomViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hammadhanif.availabilityapplication.R;

public class Header extends ConstraintLayout {
    public Header(Context context) {
        super(context);
        this.init(context,null);
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context,attrs);
    }

    public Header(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context,attrs);
    }

    /*public Header(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(attrs);
    }*/
    private void init(Context context,@Nullable AttributeSet set){
        LayoutInflater.from(context).inflate(R.layout.header, this, true);
    }
    //https://www.youtube.com/watch?v=ZiU06u_vuG8
}
