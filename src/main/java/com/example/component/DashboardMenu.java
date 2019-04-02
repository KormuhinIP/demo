package com.example.component;


import com.example.view.DashboardViewType;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;



@SuppressWarnings({ "serial", "unchecked" })
public final class DashboardMenu extends CustomComponent {


    private static final String STYLE_VISIBLE = "valo-menu-visible";


    public DashboardMenu() {
        setPrimaryStyleName("valo-menu");
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("QuickTickets <strong>Dashboard</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);
        return logoWrapper;
    }





    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName()
                        .contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (final DashboardViewType view : DashboardViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);
            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;

    }

    private Component buildBadgeWrapper(final Component menuItemButton,
                                        final Component badgeLabel) {
        CssLayout dashboardWrapper = new CssLayout(menuItemButton);
        dashboardWrapper.addStyleName("badgewrapper");
        dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
        badgeLabel.setWidthUndefined();
        badgeLabel.setVisible(false);
        dashboardWrapper.addComponent(badgeLabel);
        return dashboardWrapper;
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
