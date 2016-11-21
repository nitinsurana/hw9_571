package com.nitinsurana.csci571.hw9.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nitinsurana.csci571.hw9.MyCallback;
import com.nitinsurana.csci571.hw9.MyHttpRequest;
import com.nitinsurana.csci571.hw9.MyLegislatorRecyclerViewAdapter;
import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.Bean;
import com.nitinsurana.csci571.hw9.beans.LegislatorBean;
import com.nitinsurana.csci571.hw9.beans.LegislatorsJson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class LegislatorFragment extends Fragment implements MyCallback, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnFragmentInteractionListener mListener;

    private LinkedHashMap<String, Integer> mapStateIndex = new LinkedHashMap<String, Integer>();
    private LinkedHashMap<String, Integer> mapHouseIndex = new LinkedHashMap<String, Integer>();
    private LinkedHashMap<String, Integer> mapSenateIndex = new LinkedHashMap<String, Integer>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LegislatorFragment() {
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
        View view = inflater.inflate(R.layout.fragment_legislator_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        //State
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.state_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //House
        recyclerView = (RecyclerView) view.findViewById(R.id.house_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //Senate
        recyclerView = (RecyclerView) view.findViewById(R.id.senate_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        TabHost tabHost = (TabHost) view.findViewById(R.id.tab_host);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("By States").setContent(R.id.tab_one_container).setIndicator("By States"));
        tabHost.addTab(tabHost.newTabSpec("House").setContent(R.id.tab_two_container).setIndicator("House"));
        tabHost.addTab(tabHost.newTabSpec("Senate").setContent(R.id.tab_three_container).setIndicator("Senate"));

        MyHttpRequest myHttpRequest = new MyHttpRequest(this);
        myHttpRequest.execute("http://hw8-env.5mmquaicpi.us-west-2.elasticbeanstalk.com/api.php?submit=true&db=Legislators&keyword=&chamber=&page=undefined");
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
        LegislatorsJson lj = gson.fromJson(result, LegislatorsJson.class);
        //State
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.state_list);
        ArrayList<LegislatorBean> lst = lj.byState();
        recyclerView.setAdapter(new MyLegislatorRecyclerViewAdapter(lst, mListener));
        displayIndex(getIndexList(mapStateIndex, lst, true), R.id.state_side_index);
        //House
        recyclerView = (RecyclerView) getView().findViewById(R.id.house_list);
        lst = lj.byHouse();
        recyclerView.setAdapter(new MyLegislatorRecyclerViewAdapter(lst, mListener));
        displayIndex(getIndexList(mapHouseIndex, lst, false), R.id.house_side_index);
        //Senate
        recyclerView = (RecyclerView) getView().findViewById(R.id.senate_list);
        lst = lj.bySenate();
        recyclerView.setAdapter(new MyLegislatorRecyclerViewAdapter(lst, mListener));
        displayIndex(getIndexList(mapSenateIndex, lst, false), R.id.senate_side_index);
    }

    private LinkedHashMap<String, Integer> getIndexList(LinkedHashMap<String, Integer> mapIndex, ArrayList<LegislatorBean> lst, boolean isState) {
        for (int i = 0; i < lst.size(); i++) {
            LegislatorBean bean = lst.get(i);
            String index = bean.getState_name().substring(0, 1);
            if (!isState) {
                index = bean.getLast_name().substring(0, 1);
            }

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
        return mapIndex;
    }

    private void displayIndex(LinkedHashMap<String, Integer> map, int side_index_id) {
        LinearLayout indexLayout = (LinearLayout) getView().findViewById(side_index_id);

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
            case R.id.state_side_index:
                ((RecyclerView) getView().findViewById(R.id.state_list)).scrollToPosition(mapStateIndex.get(selectedIndex.getText()));
                break;
            case R.id.house_side_index:
                ((RecyclerView) getView().findViewById(R.id.house_list)).scrollToPosition(mapHouseIndex.get(selectedIndex.getText()));
                break;
            case R.id.senate_side_index:
                ((RecyclerView) getView().findViewById(R.id.senate_list)).scrollToPosition(mapSenateIndex.get(selectedIndex.getText()));
                break;
        }
    }

    public void toggleFavorite(View v) {

    }
}
