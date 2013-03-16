package net.anzix.livetrack;

import com.google.sitebricks.SitebricksModule;
import net.anzix.livetrack.api.PointResource;

/**
 * Custom Guice module for this application.
 */
public class LivetrackGuice extends SitebricksModule {


    @Override
    protected void configureSitebricks() {
        scan(AppConfig.class.getPackage());
        scan(PointResource.class.getPackage());
        bind(Store.class).asEagerSingleton();
    }
}
