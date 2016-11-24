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
import com.nitinsurana.csci571.hw9.FavoriteDAO;
import com.nitinsurana.csci571.hw9.MyBillRecyclerViewAdapter;
import com.nitinsurana.csci571.hw9.MyCallback;
import com.nitinsurana.csci571.hw9.MyHttpRequest;
import com.nitinsurana.csci571.hw9.MyLegislatorRecyclerViewAdapter;
import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.BillBean;
import com.nitinsurana.csci571.hw9.beans.CommitteeBean;
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
public class FavoriteFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private LinkedHashMap<String, Integer> mapLegislatorIndex = new LinkedHashMap<String, Integer>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        final TabHost tabHost = (TabHost) view.findViewById(R.id.tab_host);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("Legislators").setContent(R.id.tab_one_container).setIndicator("Legislators"));
        tabHost.addTab(tabHost.newTabSpec("Bills").setContent(R.id.tab_two_container).setIndicator("Bills"));
        tabHost.addTab(tabHost.newTabSpec("Committees").setContent(R.id.tab_three_container).setIndicator("Committees"));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                AppUtils.boldTabHost(tabHost);
            }
        });
        AppUtils.boldTabHost(tabHost);

        // Set the adapter
        Context context = view.getContext();
        //Legislators
        ArrayList<LegislatorBean> lstLegislators = new ArrayList<>(FavoriteDAO.legislatorBeanMap.values());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.legislator_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyLegislatorRecyclerViewAdapter(lstLegislators, mListener));
        displayIndex(view, getIndexList(mapLegislatorIndex, lstLegislators), R.id.side_index);
        //Bills
        ArrayList<BillBean> lstBills = new ArrayList<>(FavoriteDAO.billBeanMap.values());
        recyclerView = (RecyclerView) view.findViewById(R.id.bill_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyBillRecyclerViewAdapter(lstBills, mListener));
        //Committees
        ArrayList<CommitteeBean> lstCommittees = new ArrayList<>(FavoriteDAO.committeeBeanMap.values());
        recyclerView = (RecyclerView) view.findViewById(R.id.committee_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyCommitteeRecyclerViewAdapter(lstCommittees, mListener));
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

    private LinkedHashMap<String, Integer> getIndexList(LinkedHashMap<String, Integer> mapIndex, ArrayList<LegislatorBean> lst) {
        for (int i = 0; i < lst.size(); i++) {
            LegislatorBean bean = lst.get(i);
            String index = bean.getLast_name().substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
        return mapIndex;
    }

    private void displayIndex(View view, LinkedHashMap<String, Integer> map, int side_index_id) {
        LinearLayout indexLayout = (LinearLayout) view.findViewById(side_index_id);

        TextView textView;
        TreeSet<String> indexList = new TreeSet<>(map.keySet());
        for (String index : indexList) {
            textView = (TextView) getActivity().getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            textView.setTag(side_index_id);
            indexLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        switch (Integer.valueOf(view.getTag().toString())) {
            case R.id.side_index:
                ((RecyclerView) getView().findViewById(R.id.legislator_list)).scrollToPosition(mapLegislatorIndex.get(selectedIndex.getText()));
                break;
        }
    }
}
