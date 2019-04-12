package com.example.view;

import com.example.component.DashboardMenu;
import com.example.component.DashboardNavigator;
import com.example.component.DashboardViewType;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainView extends HorizontalLayout implements View {

    private static final Logger logger = LoggerFactory.getLogger(MainView.class);

    public MainView() {

        logger.debug("MainView constructor invoked;");

        setSizeFull();
        setSpacing(false);
        addComponent(new DashboardMenu());

        ComponentContainer content = new CssLayout();

        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);
        new DashboardNavigator(content);
        UI.getCurrent().getNavigator()
            .navigateTo(DashboardViewType.DASHBOARD.getViewName());
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }


}
