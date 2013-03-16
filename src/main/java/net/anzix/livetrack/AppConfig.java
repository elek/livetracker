package net.anzix.livetrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Custom sitebrick configuration definition.
 */
public class AppConfig extends GuiceServletContextListener {

    @Override
    public Injector getInjector() {
        return Guice.createInjector(new LivetrackGuice());
    }

}
