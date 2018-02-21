package io.gumil.cities.view.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private TextView emptyTextView;

    private CityAdapter adapter = new CityAdapter();
    private CityListPresenter presenter;
    private MenuItem searchMenu;
    private SearchView searchView;

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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        searchMenu = menu.findItem(R.id.actionSearch);
        searchView = (SearchView) searchMenu.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.filter(newText);
                return true;
            }
        });

        searchMenu.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (getActivity() != null) {
                    FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
                    int index = supportFragmentManager.getBackStackEntryCount() - 1;

                    if (index == -1) {
                        return true;
                    }

                    String name = supportFragmentManager.getBackStackEntryAt(index).getName();

                    if (!name.equals(CityListFragment.class.getSimpleName())) {
                        getActivity().onBackPressed();
                        return false;
                    } else {
                        return true;
                    }
                }
                return true;
            }
        });

        if (adapter.getItemCount() > 0 && searchMenu != null) {
            searchMenu.setVisible(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showToolbar(true);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        emptyTextView = view.findViewById(R.id.emptyText);

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
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
    }

    @Override
    public void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        emptyTextView.setVisibility(View.GONE);
    }

    @Override
    public void showCities(List<City> cities) {
        if (searchMenu != null) {
            searchMenu.setVisible(true);
        }

        emptyTextView.setVisibility(cities.isEmpty() ? View.VISIBLE : View.GONE);
        adapter.setCities(cities);
    }

    @Override
    public void showError(int stringRes) {
        if (getView() != null) {
            Snackbar.make(getView(), stringRes, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(City city) {
        if (getActivity() instanceof MainActivity) {
            LatLng location = new LatLng(city.getCoord().getLatitude(), city.getCoord().getLongitude());
            ((MainActivity) getActivity()).goTo(MapFragment.newInstance(city.getName(), location), true);
        }
    }
}
