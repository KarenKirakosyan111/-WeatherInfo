package com.karen.weatherinfo.countryRecyclerView;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karen.weatherinfo.R;
import com.karen.weatherinfo.model.Country;

import java.util.List;

public class CountryRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private IOnClickItem mIOnClickItem;
    private Context context;
    private List<Country> countries;

    public CountryRecyclerViewAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.setmIOnClick(new MyViewHolder.IOnClick() {
            @Override
            public void onClick(int position) {
                if (mIOnClickItem != null) {
                    mIOnClickItem.onClickItem(countries.get(position));
                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.setDataSource(country);

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setIOnClickItem(IOnClickItem IOnClickItem) {
        mIOnClickItem = IOnClickItem;
    }

    public interface IOnClickItem{
        void onClickItem(Country country);
    }
}
