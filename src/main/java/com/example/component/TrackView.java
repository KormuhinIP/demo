package com.example.component;


import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackView {

    private static final Logger logger = LoggerFactory.getLogger(TrackView.class);

    private final VerticalLayout vlayout;
    private final Window sub;
    private final String apiKey = "api-key";
    private GoogleMap googleMap;

    public TrackView() {

        logger.debug("TrackView constructor invoked;");

        vlayout = new VerticalLayout();
        vlayout.setSizeFull();
        sub = new Window("maps");
        sub.setWidth("800px");
        sub.setHeight("800px");
        googleMap = new GoogleMap(apiKey, null, "english");
        googleMap.setSizeFull();
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);
        vlayout.addComponent(googleMap);

        sub.setContent(vlayout);
        UI.getCurrent().addWindow(sub);


    }
}