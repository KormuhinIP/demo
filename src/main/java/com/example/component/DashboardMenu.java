package com.example.component;


import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings({"serial"})
public final class DashboardMenu extends CustomComponent {

    private static final Logger logger = LoggerFactory.getLogger(DashboardMenu.class);
    private VerticalLayout menuContent;
    private Label logo;
    private HorizontalLayout logoWrapper;
    private CssLayout menuItemsLayout;
    private Component menuItemComponent;

    public DashboardMenu() {

        logger.debug("DashboardMenu constructor invoked;");

        setPrimaryStyleName("valo-menu");
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    private Component buildContent() {

        logger.debug("buildContent method (DashboardMenu) invoked;");

        menuContent = new VerticalLayout();
        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildMenuItems());
        Button button = buildExit();
        menuContent.addComponent(button);
        menuContent.setComponentAlignment(button, Alignment.BOTTOM_CENTER);
        return menuContent;
    }

    private Component buildTitle() {

        logger.debug("buildTitle method (DashboardMenu) invoked;");

        logo = new Label("<strong>School Of Driving</strong>",
            ContentMode.HTML);
        logo.setSizeUndefined();
        logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);
        return logoWrapper;
    }

    private Button buildExit() {

        logger.debug("buildExit method (DashboardMenu) invoked;");

        Button exit = new Button("exit");
        exit.addStyleName(ValoTheme.BUTTON_SMALL);
        exit.addStyleName("red");
        exit.addClickListener((Button.ClickListener) event -> {
        });
        return exit;
    }

    private Component buildMenuItems() {

        logger.debug("buildMenuItems method (DashboardMenu) invoked;");

        menuItemsLayout = new CssLayout();

        for (final DashboardViewType view : DashboardViewType.values()) {
            menuItemComponent = new ValoMenuItemButton(view);
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
            addClickListener((ClickListener) event -> UI.getCurrent().getNavigator()
                .navigateTo(view.getViewName()));

        }

    }
}
