package net.anzix.livetrack;

import com.google.sitebricks.SitebricksModule;
import com.google.sitebricks.channel.ChannelListener;
import com.google.sitebricks.channel.ChannelModule;
import net.anzix.livetrack.api.PointResource;

/**
 * Custom Guice module for this application.
 */
public class LivetrackGuice extends SitebricksModule {


    @Override
    protected void configureSitebricks() {
        install(new ChannelModule("/channel") {
            @Override
            protected void configureChannels() {
                processAll().with(PointResource.class);
                bind(ChannelListener.class).to(PointResource.Listener.class);
            }
        }
        );


        scan(AppConfig.class.getPackage());
        scan(PointResource.class.getPackage());
        bind(Store.class).asEagerSingleton();
    }
}
