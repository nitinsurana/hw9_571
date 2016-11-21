package com.nitinsurana.csci571.hw9.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nitinsurana.csci571.hw9.FavoriteDAO;
import com.nitinsurana.csci571.hw9.MyBillRecyclerViewAdapter;
import com.nitinsurana.csci571.hw9.MyCallback;
import com.nitinsurana.csci571.hw9.MyHttpRequest;
import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.BillBean;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment implements MyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        //Active
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.active_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //New
        recyclerView = (RecyclerView) view.findViewById(R.id.new_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        TabHost tabHost = (TabHost) view.findViewById(R.id.tab_host);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("Active").setContent(R.id.tab_one_container).setIndicator("Active"));
        tabHost.addTab(tabHost.newTabSpec("New").setContent(R.id.tab_two_container).setIndicator("New"));

        MyHttpRequest myHttpRequest = new MyHttpRequest(this);
        myHttpRequest.execute("http://hw8-env.5mmquaicpi.us-west-2.elasticbeanstalk.com/api.php?submit=true&db=Bills");
        return view;
    }

    @Override
    public void done(String result) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        Type type = new TypeToken<List<BillBean>>() {
        }.getType();

        //Active
        JsonArray activeArray = parser.parse(result).getAsJsonObject().get("active").getAsJsonObject().get("results").getAsJsonArray();
        List<BillBean> lstActive = gson.fromJson(activeArray, type);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.active_list);
        recyclerView.setAdapter(new MyBillRecyclerViewAdapter(lstActive, mListener));
        //New
        JsonArray newArray = parser.parse(result).getAsJsonObject().get("new").getAsJsonObject().get("results").getAsJsonArray();
        List<BillBean> lstNew = gson.fromJson(newArray, type);
        recyclerView = (RecyclerView) getView().findViewById(R.id.new_list);
        recyclerView.setAdapter(new MyBillRecyclerViewAdapter(lstNew, mListener));
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
