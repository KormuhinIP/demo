package com.example.view;

import com.example.component.DashboardMenu;
import com.example.component.DashboardNavigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;


public class MainView extends HorizontalLayout implements View {


    public MainView() {

        setSizeFull();
        addStyleName("mainview");
        setSpacing(false);
        addComponent(new DashboardMenu());

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
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
