package com.example.view;

import com.example.impl.ApplicationContextHolder;
import com.example.model.Authentication;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.springframework.context.ApplicationContext;


@SpringView(name = LoginView.NAME)
public class LoginView extends VerticalLayout implements View {
    public static final String NAME = "";


    private final ApplicationContext ctx;


    public LoginView() {


        ctx = ApplicationContextHolder.getApplicationContext();

        Panel panel = new Panel("Login");
        panel.setSizeUndefined();
        addComponent(panel);

        TextField username = new TextField("Username");
        FormLayout content = new FormLayout();
        content.addComponent(username);

        PasswordField password = new PasswordField("Password");
        content.addComponent(password);


        Button send = new Button("Enter");

        send.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {


                if (ctx.getBean(Authentication.class).authenticate(username.getValue(), password.getValue())) {
                    VaadinSession.getCurrent().setAttribute("user", username.getValue());

                    getUI().getNavigator().addView(MainView.NAME, MainView.class);

                    getUI().getNavigator().navigateTo(MainView.NAME);

                } else {
                    Notification.show("Invalid credentials", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        content.addComponent(send);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);

        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
