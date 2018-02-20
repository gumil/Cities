package io.gumil.cities.view.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.gumil.cities.R;
import io.gumil.cities.model.City;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> cities;

    public CityAdapter() {
        this.cities = new ArrayList<>();
    }

    public CityAdapter(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
        public CityViewHolder(View itemView) {
            super(itemView);
        }

        void bind(City city) {
            TextView cityTextView = itemView.findViewById(R.id.item_city_name);

            cityTextView.setText(String.format("%s, %s", city.getName(), city.getCountry()));
        }
    }
}