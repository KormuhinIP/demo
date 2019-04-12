package com.example.view;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import java.util.Locale;


@SuppressWarnings("serial")
@Theme("vaadinmaps")

@SpringUI
@Widgetset("AppWidgetset")
public class VaadinloginUI extends UI {

    private static final Logger logger = LoggerFactory.getLogger(VaadinloginUI.class);


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setLocale(Locale.US);
        updateContent();
        logger.info("start UI");
    }

    @WebServlet(value = "/*", asyncSupported = true)
    // @VaadinServletConfiguration(productionMode = false, ui = VaadinloginUI.class, widgetset = "com.example.view.widgetset.AppWidgetset")
    public static class Servlet extends VaadinServlet {
    }

    private void updateContent() {
        setContent(new LoginView());
    }
}