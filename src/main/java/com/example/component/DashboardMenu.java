package com.example.component;


import com.example.view.DashboardViewType;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;



@SuppressWarnings({ "serial", "unchecked" })
public final class DashboardMenu extends CustomComponent {


    public DashboardMenu() {
        setPrimaryStyleName("valo-menu");
        setSizeUndefined();
        setCompositionRoot(buildContent());


    }

    private Component buildContent() {
        final VerticalLayout menuContent = new VerticalLayout();

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildMenuItems());
        Button button = buildExit();
        menuContent.addComponent(button);
        menuContent.setComponentAlignment(button, Alignment.BOTTOM_CENTER);


        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("<strong>School Of Driving</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);
        return logoWrapper;
    }

    private Button buildExit() {
        Button exit = new Button("exit");
        exit.addStyleName(ValoTheme.BUTTON_SMALL);
        exit.addStyleName("red");
        exit.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

            }
        });
        return exit;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();


        for (final DashboardViewType view : DashboardViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);
            menuItemsLayout.addComponent(menuItemComponent);
        }
        menuItemsLayout.addStyleName("secondHL");
        return menuItemsLayout;
    }

    public final class ValoMenuItemButton extends Button {

        private final DashboardViewType view;

        public ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;

            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase()
                    + view.getViewName().substring(1));
            addClickListener(new ClickListener() {

                @Override
                public void buttonClick(final ClickEvent event) {
                    UI.getCurrent().getNavigator()
                            .navigateTo(view.getViewName());
                }
            });

        }

    }
}
