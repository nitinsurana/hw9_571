package com.nitinsurana.csci571.hw9.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nitinsurana.csci571.hw9.AppUtils;
import com.nitinsurana.csci571.hw9.MyCallback;
import com.nitinsurana.csci571.hw9.MyHttpRequest;
import com.nitinsurana.csci571.hw9.MyLegislatorRecyclerViewAdapter;
import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.CommitteeBean;
import com.nitinsurana.csci571.hw9.beans.CommitteeJson;
import com.nitinsurana.csci571.hw9.beans.LegislatorBean;
import com.nitinsurana.csci571.hw9.beans.LegislatorsJson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeSet;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CommitteeFragment extends Fragment implements MyCallback {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommitteeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_committee_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        //State
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.house_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //House
        recyclerView = (RecyclerView) view.findViewById(R.id.senate_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //Senate
        recyclerView = (RecyclerView) view.findViewById(R.id.joint_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final TabHost tabHost = (TabHost) view.findViewById(R.id.tab_host);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("House").setContent(R.id.tab_one_container).setIndicator("House"));
        tabHost.addTab(tabHost.newTabSpec("Senate").setContent(R.id.tab_two_container).setIndicator("Senate"));
        tabHost.addTab(tabHost.newTabSpec("Joint").setContent(R.id.tab_three_container).setIndicator("Joint"));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                AppUtils.boldTabHost(tabHost);
            }
        });
        AppUtils.boldTabHost(tabHost);

        MyHttpRequest myHttpRequest = new MyHttpRequest(this);
        myHttpRequest.execute("http://hw8-env.5mmquaicpi.us-west-2.elasticbeanstalk.com/api.php?submit=true&db=Committees&keyword=&chamber=&page=undefined");
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void done(String result) {
        Gson gson = new Gson();
        CommitteeJson lj = gson.fromJson(result, CommitteeJson.class);
        //State
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.house_list);
        ArrayList<CommitteeBean> lst = lj.byHouse();
        recyclerView.setAdapter(new MyCommitteeRecyclerViewAdapter(lst, mListener));
        //House
        recyclerView = (RecyclerView) getView().findViewById(R.id.senate_list);
        lst = lj.bySenate();
        recyclerView.setAdapter(new MyCommitteeRecyclerViewAdapter(lst, mListener));
        //Senate
        recyclerView = (RecyclerView) getView().findViewById(R.id.joint_list);
        lst = lj.byJoint();
        recyclerView.setAdapter(new MyCommitteeRecyclerViewAdapter(lst, mListener));
    }
}
