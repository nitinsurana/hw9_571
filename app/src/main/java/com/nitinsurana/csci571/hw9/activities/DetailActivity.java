package com.nitinsurana.csci571.hw9.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nitinsurana.csci571.hw9.FavoriteDAO;
import com.nitinsurana.csci571.hw9.beans.CommitteeBean;
import com.nitinsurana.csci571.hw9.fragments.BillDetailFragment;
import com.nitinsurana.csci571.hw9.fragments.BillFragment;
import com.nitinsurana.csci571.hw9.fragments.CommitteeDetailFragment;
import com.nitinsurana.csci571.hw9.fragments.LegislatorDetailFragment;
import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.Bean;
import com.nitinsurana.csci571.hw9.beans.BillBean;
import com.nitinsurana.csci571.hw9.beans.LegislatorBean;
import com.nitinsurana.csci571.hw9.fragments.LegislatorFragment;
import com.nitinsurana.csci571.hw9.fragments.OnFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

public class DetailActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Bean bean;
    public static Stack<Class<?>> parents = new Stack<Class<?>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parents.push(getClass());
        setContentView(R.layout.activity_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bean = (Bean) getIntent().getSerializableExtra("bean");
        if (bean instanceof LegislatorBean) {
            showLegislatorInfo();
        } else if (bean instanceof BillBean) {
            showBillInfo();
        } else if (bean instanceof CommitteeBean) {
            showCommitteeInfo();
        }
    }

    public void showCommitteeInfo() {
        setTitle("Committee Info");

        CommitteeBean bean = (CommitteeBean) this.bean;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, CommitteeDetailFragment.newInstance(bean), "CommitteeDetailFragment")
                .disallowAddToBackStack()
                .commit();
    }

    public void showBillInfo() {
        setTitle("Bill Info");

        BillBean bean = (BillBean) this.bean;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, BillDetailFragment.newInstance(bean), "BillDetailFragment")
                .disallowAddToBackStack()
                .commit();
    }

    public void showLegislatorInfo() {
        setTitle("Legislator Info");

        LegislatorBean bean = (LegislatorBean) this.bean;

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, LegislatorDetailFragment.newInstance(bean), "LegislatorDetailFragment")
                .disallowAddToBackStack()
                .commit();
    }

    public void openFacebook(View w) {
        LegislatorBean bean = (LegislatorBean) this.bean;
        if (StringUtils.isBlank(bean.getFacebook_id())) {
            Toast.makeText(getApplicationContext(), "Facebook is not available for this legislator", Toast.LENGTH_SHORT).show();
        } else {
            Uri uri = Uri.parse("https://www.facebook.com/" + bean.getFacebook_id());       // missing 'http://' will cause crash
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void openWebsite(View w) {
        LegislatorBean bean = (LegislatorBean) this.bean;
        if (StringUtils.isBlank(bean.getWebsite())) {
            Toast.makeText(getApplicationContext(), "Website is not available for this legislator", Toast.LENGTH_SHORT).show();
        } else {
            Uri uri = Uri.parse(bean.getWebsite());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void openTwitter(View w) {
        LegislatorBean bean = (LegislatorBean) this.bean;
        if (StringUtils.isBlank(bean.getTwitter_id())) {
            Toast.makeText(getApplicationContext(), "Twitter is not available for this legislator", Toast.LENGTH_SHORT).show();
        } else {
            Uri uri = Uri.parse("https://www.twitter.com/" + bean.getFacebook_id());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public void OnFragmentInteractionListener(Bean item) {

    }

    public void toggleFavorite(View view) {
        LegislatorDetailFragment legFragment = (LegislatorDetailFragment) getSupportFragmentManager().findFragmentByTag("LegislatorDetailFragment");
        if (legFragment != null && legFragment.isVisible()) {
            legFragment.toggleFavorite(view);
        }

        BillDetailFragment billFragment = (BillDetailFragment) getSupportFragmentManager().findFragmentByTag("BillDetailFragment");
        if (billFragment != null && billFragment.isVisible()) {
            billFragment.toggleFavorite(view);
        }

        CommitteeDetailFragment cFragment = (CommitteeDetailFragment) getSupportFragmentManager().findFragmentByTag("CommitteeDetailFragment");
        if (cFragment != null && cFragment.isVisible()) {
            cFragment.toggleFavorite(view);
        }
    }
}
