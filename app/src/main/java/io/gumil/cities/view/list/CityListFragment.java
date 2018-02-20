package io.gumil.cities.view.list;

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

import java.util.List;

import io.gumil.cities.CityApplication;
import io.gumil.cities.R;
import io.gumil.cities.model.City;

public class CityListFragment extends Fragment implements CityView {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CityAdapter adapter = new CityAdapter();

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
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (getActivity() != null && getActivity().getApplication() instanceof CityApplication) {
            CityListPresenter presenter = new CityListPresenter(((CityApplication) getActivity().getApplication()).getRepository(), this);
            presenter.loadCities();
        }
    }

    @Override
    public void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showCities(List<City> cities) {
        adapter.setCities(cities);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
