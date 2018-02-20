package io.gumil.cities.view.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.gumil.cities.CityApplication;
import io.gumil.cities.MainActivity;
import io.gumil.cities.R;
import io.gumil.cities.model.City;
import io.gumil.cities.view.map.MapFragment;

public class CityListFragment extends Fragment implements CityView, CityAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CityAdapter adapter = new CityAdapter();
    private CityListPresenter presenter;

    public static CityListFragment newInstance() {
        Bundle args = new Bundle();
        CityListFragment fragment = new CityListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
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
        adapter.setListener(this);

        if (getContext() != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }

        if (getActivity() != null && getActivity().getApplication() instanceof CityApplication) {
            presenter = new CityListPresenter(((CityApplication) getActivity().getApplication()).getRepository(), this);
            presenter.loadCities();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showToolbar(true);
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

    @Override
    public void onItemClick(City city) {
        if (getActivity() instanceof MainActivity) {
            LatLng location = new LatLng(city.getCoord().getLatitude(), city.getCoord().getLongitude());
            ((MainActivity) getActivity()).goTo(MapFragment.newInstance(city.getName(), location), true);
        }
    }
}
