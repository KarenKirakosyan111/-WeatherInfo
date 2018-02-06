package com.karen.weatherinfo.countryRecyclerView;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.karen.weatherinfo.R;
import com.karen.weatherinfo.model.Country;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private IOnClick mIOnClick;
    private TextView countryName;

    public MyViewHolder(View itemView) {
        super(itemView);
        countryName = itemView.findViewById(R.id.country_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIOnClick != null) {
                    mIOnClick.onClick(getAdapterPosition());
                }
            }
        });
    }

    public void setDataSource(Country country) {
        countryName.setText(country.getCountryName());
    }

    public void setmIOnClick(IOnClick mIOnClick) {
        this.mIOnClick = mIOnClick;
    }

    interface IOnClick {
        void onClick(int position);
    }
}
