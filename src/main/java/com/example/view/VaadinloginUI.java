package com.example.view;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.EnableVaadinNavigation;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;
import java.util.Locale;

@SuppressWarnings("serial")
@Theme("valo")
@SpringUI
@EnableVaadinNavigation
@SpringViewDisplay
public class VaadinloginUI extends UI {


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setLocale(Locale.US);
        updateContent();

    }

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = VaadinloginUI.class)
    public static class Servlet extends VaadinServlet {
    }

    private void updateContent() {
        setContent(new LoginView());
    }
}