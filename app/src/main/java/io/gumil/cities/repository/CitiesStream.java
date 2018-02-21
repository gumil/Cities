package io.gumil.cities.repository;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class CitiesStream implements Stream {

    private Context context;

    public CitiesStream(Context context) {
        this.context = context;
    }

    @Override
    public InputStream getStream() throws IOException {
        return context.getAssets().open("cities.json");
    }
}
