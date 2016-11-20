package com.nitinsurana.csci571.hw9;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitinsurana.csci571.hw9.beans.LegislatorBean;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link LegislatorBean} and makes a call to the
 * specified {@link LegislatorFragment.OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLegislatorRecyclerViewAdapter extends RecyclerView.Adapter<MyLegislatorRecyclerViewAdapter.ViewHolder> {

    private final List<LegislatorBean> mValues;
    private final LegislatorFragment.OnFragmentInteractionListener mListener;

    public MyLegislatorRecyclerViewAdapter(ArrayList<LegislatorBean> items, LegislatorFragment.OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_legislator, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getFullname());
        holder.mPartyView.setText("(" + mValues.get(position).getParty() + ")");
        holder.mStateView.setText(mValues.get(position).getState_name());
        holder.mDistrictView.setText(mValues.get(position).getDistrict());
        Picasso.with(holder.mImageView.getContext())
                .load(mValues.get(position).getImageUrl())
//                .resize(100, 150)
//                .centerCrop()
                .into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnFragmentInteractionListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mPartyView;
        public final TextView mStateView;
        public final TextView mDistrictView;
        public final ImageView mImageView;
        public LegislatorBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mPartyView = (TextView) view.findViewById(R.id.party);
            mStateView = (TextView) view.findViewById(R.id.state);
            mDistrictView = (TextView) view.findViewById(R.id.district);
            mImageView = (ImageView) view.findViewById(R.id.img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
