package com.example.component;

import com.example.model.Exam;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TrackView {


    final VerticalLayout vlayout;
    final Window sub;
    private final String apiKey = "api-key";
    Exam exam;
    Grid grid;
    private GoogleMap googleMap;


    public TrackView() {

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