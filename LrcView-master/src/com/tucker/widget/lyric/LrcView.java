package com.tucker.widget.lyric;


import android.content.Context;
import android.view.View;

import com.zhouq.widget.gradient.GradientTextView;

/**
 * Created by Administrator on 2015/4/10.
 */
public class LrcView {

    private LrcOtherView otherView;

    private GradientTextView highLightText;

    public LrcView() {
        super();
    }

    public class LrcOtherView extends View{

        public LrcOtherView(Context context) {
            super(context);
        }
    }

}
