package com.example.component;



import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
public class DashboardNavigator extends Navigator {

    private static final Logger logger = LoggerFactory.getLogger(DashboardNavigator.class);


    public DashboardNavigator(final ComponentContainer container) {

        super(UI.getCurrent(), container);

        logger.debug("DashboardNavigator constructor invoked;");

        initViewChangeListener();
        initViewProviders();
    }


    private void initViewChangeListener() {

        logger.debug("initViewChangeListener method (DashboardNavigator) invoked;");

        addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(final ViewChangeEvent event) {
                return true;
            }

            @Override
            public void afterViewChange(final ViewChangeEvent event) {
                DashboardViewType view = DashboardViewType.getByViewName(event
                    .getViewName());
            }
        });
    }

    private void initViewProviders() {

        logger.debug("initViewProviders method (DashboardNavigator) invoked;");

        for (final DashboardViewType viewType : DashboardViewType.values()) {
            ViewProvider viewProvider = new ClassBasedViewProvider(
                viewType.getViewName(), viewType.getViewClass()) {
                private View cachedInstance;

                @Override
                public View getView(final String viewName) {
                    View result = null;
                    if (viewType.getViewName().equals(viewName)) {
                        if (viewType.isStateful()) {
                            if (cachedInstance == null) {
                                cachedInstance = super.getView(viewType
                                    .getViewName());
                            }
                            result = cachedInstance;
                        } else {
                            result = super.getView(viewType.getViewName());
                        }
                    }
                    return result;
                }
            };
            addProvider(viewProvider);
        }
    }
}
