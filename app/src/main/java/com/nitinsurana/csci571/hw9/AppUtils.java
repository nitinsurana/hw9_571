package com.nitinsurana.csci571.hw9;

import android.graphics.Typeface;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by coding_idiot on 23/11/16.
 */

public class AppUtils {

    static public void boldTabHost(TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTypeface(Typeface.DEFAULT);
//            tv.setBackgroundResource(R.drawable.transparent);
        }
        TextView textView = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
                .findViewById(android.R.id.title);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        tabHost.getTabWidget().setStripEnabled(false);
//        tabHost.getTabContentView().setSelectedTabIndicatorHeight();
//        tabHost.getTabWidget().setDividerDrawable(R.drawable.logo);
    }
}
