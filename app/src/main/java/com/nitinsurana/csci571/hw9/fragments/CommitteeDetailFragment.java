package com.nitinsurana.csci571.hw9.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitinsurana.csci571.hw9.FavoriteDAO;
import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.CommitteeBean;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommitteeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommitteeDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private CommitteeBean bean;

    private OnFragmentInteractionListener mListener;

    public CommitteeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BillDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommitteeDetailFragment newInstance(CommitteeBean bean) {
        CommitteeDetailFragment fragment = new CommitteeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("bean", bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bean = (CommitteeBean) getArguments().getSerializable("bean");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_committee_detail, container, false);

        TextView txt = (TextView) view.findViewById(R.id.id);
        txt.setText(bean.getCommittee_id());

        txt = (TextView) view.findViewById(R.id.name);
        txt.setText(bean.getName());

        txt = (TextView) view.findViewById(R.id.chamber);
        txt.setText(bean.getChamber());

        txt = (TextView) view.findViewById(R.id.parent_committee);
        txt.setText(bean.getParent_committee_id());

        txt = (TextView) view.findViewById(R.id.contact);
        txt.setText(bean.getPhone());

        ImageView img = (ImageView) view.findViewById(R.id.fav);
        if (FavoriteDAO.committeeBeanMap.get(bean.getCommittee_id()) != null) {
            Picasso.with(img.getContext()).load(R.drawable.ss).into(img);
        }

        return view;
    }

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

    public void toggleFavorite(View view) {
        ImageView img = (ImageView) view.findViewById(R.id.fav);
        if (FavoriteDAO.committeeBeanMap.get(bean.getCommittee_id()) == null) {
            FavoriteDAO.committeeBeanMap.put(bean.getCommittee_id(), bean);
            Picasso.with(img.getContext()).load(R.drawable.ss).into(img);
        } else {
            FavoriteDAO.committeeBeanMap.remove(bean.getCommittee_id());
            Picasso.with(img.getContext()).load(R.drawable.s).into(img);
        }
    }
}