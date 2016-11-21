package com.nitinsurana.csci571.hw9;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nitinsurana.csci571.hw9.beans.BillBean;
import com.nitinsurana.csci571.hw9.fragments.LegislatorFragment;
import com.nitinsurana.csci571.hw9.fragments.OnFragmentInteractionListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BillBean} and makes a call to the
 * specified {@link OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBillRecyclerViewAdapter extends RecyclerView.Adapter<MyBillRecyclerViewAdapter.ViewHolder> {

    private final List<BillBean> mValues;
    private final OnFragmentInteractionListener mListener;

    public MyBillRecyclerViewAdapter(List<BillBean> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bill_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getBill_id());
        String title = mValues.get(position).getShort_title();
        if (StringUtils.isBlank(title)) {
            title = mValues.get(position).getOfficial_title();
        }
        holder.mTitle.setText(title);
        holder.mIntroducedOn.setText(DateFormatUtils.format(mValues.get(position).getIntroduced_on(), "MMM dd, yyyy"));

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
        public final TextView mTitle;
        public final TextView mIntroducedOn;
        public BillBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mTitle = (TextView) view.findViewById(R.id.title);
            mIntroducedOn = (TextView) view.findViewById(R.id.introduced_on);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
