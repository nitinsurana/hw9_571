package com.nitinsurana.csci571.hw9.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nitinsurana.csci571.hw9.FavoriteDAO;
import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.LegislatorBean;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LegislatorDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegislatorDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private LegislatorBean bean;
    ;

    private OnFragmentInteractionListener mListener;

    public LegislatorDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bean LegislatorBean.
     * @return A new instance of fragment LegislatorDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LegislatorDetailFragment newInstance(LegislatorBean bean) {
        LegislatorDetailFragment fragment = new LegislatorDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("bean", bean);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bean = (LegislatorBean) getArguments().getSerializable("bean");
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_legislator_detail, container, false);

        ImageView img = (ImageView) view.findViewById(R.id.party_img);
        TextView txt = (TextView) view.findViewById(R.id.party_title);
        if (bean.getParty().equalsIgnoreCase("r")) {
            Picasso.with(img.getContext()).load(R.drawable.r).into(img);
            txt.setText(" Republican");
        } else if (bean.getParty().equalsIgnoreCase("d")) {
            Picasso.with(img.getContext()).load(R.drawable.d).into(img);
            txt.setText(" Democrat");
        } else {
            Picasso.with(img.getContext()).load(R.drawable.i).into(img);
            txt.setText(" Independent");
        }

        img = (ImageView) view.findViewById(R.id.img);
        Picasso.with(img.getContext())
                .load(bean.getImageUrl())
                .resize(180, 200)
                .into(img);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(bean.getTitle() + ". " + bean.getFullname());

        txt = (TextView) view.findViewById(R.id.email);
        txt.setText(bean.getOc_email());

        txt = (TextView) view.findViewById(R.id.chamber);
        txt.setText(bean.getChamber());

        txt = (TextView) view.findViewById(R.id.contact);
        txt.setText(bean.getPhone());

        txt = (TextView) view.findViewById(R.id.start_term);
        String term = DateFormatUtils.format(bean.getTerm_start(), "MMM dd, yyyy");
        txt.setText(term);

        txt = (TextView) view.findViewById(R.id.end_term);
        term = DateFormatUtils.format(bean.getTerm_end(), "MMM dd, yyyy");
        txt.setText(term);

        float t = (float) ((1.0 * new Date().getTime() - bean.getTerm_start().getTime()) / (1.0 * bean.getTerm_end().getTime() - bean.getTerm_start().getTime()));
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.term);
        progressBar.setProgress((int) (t * 100));

        txt = (TextView) view.findViewById(R.id.term_txt);
        txt.setText((int)( t * 100) + "%               ");

        txt = (TextView) view.findViewById(R.id.office);
        txt.setText(bean.getOffice());

        txt = (TextView) view.findViewById(R.id.state);
        txt.setText(bean.getState());

        txt = (TextView) view.findViewById(R.id.fax);
        txt.setText(bean.getFax());

        txt = (TextView) view.findViewById(R.id.birthday);
        term = DateFormatUtils.format(bean.getBirthday(), "MMM dd, yyyy");
        txt.setText(term);

        img = (ImageView) view.findViewById(R.id.fav);
        if (FavoriteDAO.legislatorBeanMap.get(bean.getBioguide_id()) != null) {
            Picasso.with(img.getContext()).load(R.drawable.ss).into(img);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.OnFragmentInteractionListener(nul);
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

    public void toggleFavorite(View view) {
        ImageView img = (ImageView) view.findViewById(R.id.fav);
        if (FavoriteDAO.legislatorBeanMap.get(bean.getBioguide_id()) == null) {
            FavoriteDAO.legislatorBeanMap.put(bean.getBioguide_id(), bean);
            Picasso.with(img.getContext()).load(R.drawable.ss).into(img);
        } else {
            FavoriteDAO.legislatorBeanMap.remove(bean.getBioguide_id());
            Picasso.with(img.getContext()).load(R.drawable.s).into(img);
        }
    }
}
