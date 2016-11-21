package com.nitinsurana.csci571.hw9.fragments;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitinsurana.csci571.hw9.FavoriteDAO;
import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.BillBean;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BillDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private BillBean bean;

    private OnFragmentInteractionListener mListener;

    public BillDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BillDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillDetailFragment newInstance(BillBean bean) {
        BillDetailFragment fragment = new BillDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("bean", bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bean = (BillBean) getArguments().getSerializable("bean");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_detail, container, false);

        TextView txt = (TextView) view.findViewById(R.id.id);
        txt.setText(bean.getBill_id());

        txt = (TextView) view.findViewById(R.id.type);
        txt.setText(bean.getBill_type());

        txt = (TextView) view.findViewById(R.id.title);
        String title = bean.getShort_title();
        if (StringUtils.isBlank(title)) {
            title = bean.getOfficial_title();
        }
        txt.setText(title);

        txt = (TextView) view.findViewById(R.id.sponsor);
        txt.setText(bean.getSponsor().getFullname());

        txt = (TextView) view.findViewById(R.id.chamber);
        txt.setText(bean.getChamber());

        txt = (TextView) view.findViewById(R.id.introduced_on);
        String term = DateFormatUtils.format(bean.getIntroduced_on(), "MMM dd, yyyy");
        txt.setText(term);

        txt = (TextView) view.findViewById(R.id.status);
        txt.setText(bean.getHistory().isActive() ? "Active" : "New");

        ImageView img = (ImageView) view.findViewById(R.id.fav);
        if (FavoriteDAO.billBeanMap.get(bean.getBill_id()) != null) {
            Picasso.with(img.getContext()).load(R.drawable.ss).into(img);
        }

        txt = (TextView) view.findViewById(R.id.bill_url);
        if (bean.getLast_version() != null && bean.getLast_version().get("urls") != null) {
            String pdf = ((java.util.Map) bean.getLast_version().get("urls")).get("pdf").toString();
            txt.setText(pdf);
        } else {
            txt.setText("N.A.");
        }

        txt = (TextView) view.findViewById(R.id.congress_url);
        if (bean.getUrls() != null && bean.getUrls().get("congress") != null) {
            String pdf = bean.getUrls().get("congress").toString();
            txt.setText(pdf);
        } else {
            txt.setText("N.A.");
        }

        txt = (TextView) view.findViewById(R.id.version_status);
        if (bean.getLast_version() != null && bean.getLast_version().get("version_name") != null) {
            String pdf = bean.getLast_version().get("version_name").toString();
            txt.setText(pdf);
        } else {
            txt.setText("N.A.");
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
        if (FavoriteDAO.billBeanMap.get(bean.getBill_id()) == null) {
            FavoriteDAO.billBeanMap.put(bean.getBill_id(), bean);
            Picasso.with(img.getContext()).load(R.drawable.ss).into(img);
        } else {
            FavoriteDAO.billBeanMap.remove(bean.getBill_id());
            Picasso.with(img.getContext()).load(R.drawable.s).into(img);
        }
    }
}