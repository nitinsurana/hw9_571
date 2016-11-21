package com.nitinsurana.csci571.hw9.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nitinsurana.csci571.hw9.R;
import com.nitinsurana.csci571.hw9.beans.BillBean;
import com.nitinsurana.csci571.hw9.beans.CommitteeBean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BillBean} and makes a call to the
 * specified {@link OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCommitteeRecyclerViewAdapter extends RecyclerView.Adapter<MyCommitteeRecyclerViewAdapter.ViewHolder> {

    private final List<CommitteeBean> mValues;
    private final OnFragmentInteractionListener mListener;

    public MyCommitteeRecyclerViewAdapter(List<CommitteeBean> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_committee_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCommittee_id());
        holder.mName.setText(mValues.get(position).getName());
        holder.mChamber.setText(mValues.get(position).getChamber());

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
        public final TextView mIdView;
        public final TextView mName;
        public final TextView mChamber;
        public CommitteeBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mName = (TextView) view.findViewById(R.id.name);
            mChamber = (TextView) view.findViewById(R.id.chamber);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
