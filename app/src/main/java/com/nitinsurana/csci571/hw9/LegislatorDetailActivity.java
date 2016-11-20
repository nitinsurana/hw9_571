package com.nitinsurana.csci571.hw9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitinsurana.csci571.hw9.beans.LegislatorBean;
import com.squareup.picasso.Picasso;

public class LegislatorDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // To retrieve object in second Activity
        LegislatorBean l = (LegislatorBean) getIntent().getSerializableExtra("legislator");

        ImageView img = (ImageView) findViewById(R.id.img);
        Picasso.with(img.getContext())
                .load(l.getImageUrl())
                .resize(200, 300)
                .centerCrop()
                .into(img);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(l.getFullname());
    }
}
