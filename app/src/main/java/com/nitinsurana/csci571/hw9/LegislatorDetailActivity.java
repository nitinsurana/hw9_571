package com.nitinsurana.csci571.hw9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nitinsurana.csci571.hw9.beans.LegislatorBean;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class LegislatorDetailActivity extends AppCompatActivity {

    private LegislatorBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // To retrieve object in second Activity
        bean = (LegislatorBean) getIntent().getSerializableExtra("legislator");

        if (StringUtils.isBlank(bean.getFacebook_id())) {
            ImageView i = (ImageView) findViewById(R.id.social_f);
            i.setVisibility(View.INVISIBLE);
        }
        if (StringUtils.isBlank(bean.getTwitter_id())) {
            ImageView i = (ImageView) findViewById(R.id.social_t);
            i.setVisibility(View.INVISIBLE);
        }
        if (StringUtils.isBlank(bean.getWebsite())) {
            ImageView i = (ImageView) findViewById(R.id.social_w);
            i.setVisibility(View.INVISIBLE);
        }

        ImageView img = (ImageView) findViewById(R.id.img);
        Picasso.with(img.getContext())
                .load(bean.getImageUrl())
                .resize(180, 200)
                .centerCrop()
                .into(img);

        img = (ImageView) findViewById(R.id.party_img);
        TextView txt = (TextView) findViewById(R.id.party_title);
        if (bean.getParty().equalsIgnoreCase("r")) {
            Picasso.with(img.getContext()).load(R.drawable.r).resize(30,30).into(img);
            txt.setText(" Republican");
        } else if (bean.getParty().equalsIgnoreCase("d")) {
            Picasso.with(img.getContext()).load(R.drawable.d).resize(30,30).into(img);
            txt.setText(" Democrat");
        } else {
            Picasso.with(img.getContext()).load(R.drawable.i).resize(30,30).into(img);
            txt.setText(" Independent");
        }

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(bean.getFullname());

        txt = (TextView) findViewById(R.id.email);
        txt.setText(bean.getOc_email());

        txt = (TextView) findViewById(R.id.chamber);
        txt.setText(bean.getChamber());

        txt = (TextView) findViewById(R.id.contact);
        txt.setText(bean.getPhone());

        txt = (TextView) findViewById(R.id.start_term);
        String term = DateFormatUtils.format(bean.getTerm_start(), "MMM dd, yyyy");
        txt.setText(term);

        txt = (TextView) findViewById(R.id.end_term);
        term = DateFormatUtils.format(bean.getTerm_end(), "MMM dd, yyyy");
        txt.setText(term);
    }

    public void openFacebook(View w) {
        Uri uri = Uri.parse("https://www.facebook.com/" + bean.getFacebook_id());       // missing 'http://' will cause crash
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openWebsite(View w) {
        Uri uri = Uri.parse(bean.getWebsite());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openTwitter(View w) {
        Uri uri = Uri.parse("https://www.twitter.com/" + bean.getFacebook_id());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
