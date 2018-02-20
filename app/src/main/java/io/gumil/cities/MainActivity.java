package io.gumil.cities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.gumil.cities.view.list.CityListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CityListFragment fragment = CityListFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, fragment, CityListFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
