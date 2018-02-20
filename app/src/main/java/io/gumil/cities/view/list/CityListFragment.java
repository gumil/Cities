package io.gumil.cities.view.list;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;

import io.gumil.cities.CityApplication;
import io.gumil.cities.R;
import io.gumil.cities.model.City;

public class CityListFragment extends Fragment {

    public static CityListFragment newInstance() {
        Bundle args = new Bundle();
        CityListFragment fragment = new CityListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final CityAdapter adapter = new CityAdapter();
        recyclerView.setAdapter(adapter);

        new AsyncTask<Void, Void, Void>() {

            private List<City> cities;

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    cities = ((CityApplication) getActivity().getApplication()).getRepository().getCities();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.setCities(cities);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }.execute();
    }
}
