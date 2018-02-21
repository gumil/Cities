package io.gumil.cities.repository;

import java.io.IOException;
import java.io.InputStream;

public interface Stream {

    InputStream getStream() throws IOException;
}
