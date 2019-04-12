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
public class VaadinUI extends UI {

    private static final Logger logger = LoggerFactory.getLogger(VaadinUI.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setLocale(Locale.US);
        updateContent();
        logger.debug("start UI");
    }

    private void updateContent() {

        logger.debug("updateContent method (VaadinUI) invoked; ");

        setContent(new LoginView());
    }

    @WebServlet(value = "/*", asyncSupported = true)
    public static class Servlet extends VaadinServlet {
    }
}