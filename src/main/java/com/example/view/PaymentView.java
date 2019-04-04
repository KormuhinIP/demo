package com.example.view;

import com.example.impl.ApplicationContextHolder;
import com.example.impl.PaymentService;
import com.example.model.Payment;
import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.Renderer;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class PaymentView extends VerticalLayout implements View {


    private ApplicationContext ctx;
    private List<Payment> paymentsList;
    private Pagination pagination;
    private Grid<Payment> grid;
    private HorizontalLayout horizontalLayout;
    private TextField filter;


    public PaymentView() {

        ctx = ApplicationContextHolder.getApplicationContext();
        paymentsList = ctx.getBean(PaymentService.class).findAll();
        grid = new Grid<>(Payment.class);


        showPayment("");

        createFilter();
        filter.addValueChangeListener(e -> showPayment(e.getValue()));


        grid.setColumns("paymentDate", "sumPayment");
        grid.setColumnOrder(grid.addColumn(item -> (item.getStudent().getLastName())).setCaption("Student"));
        grid.getColumn("paymentDate").setRenderer((Renderer) (new DateRenderer("%1$td-%1$tm-%1$tY")));


        setHeight("100%");
        grid.setHeight("100%");
        grid.setRowHeight(64);
        grid.setWidth("100%");


        buttonBild();

        addComponents(filter, grid, pagination, horizontalLayout);
        setExpandRatio(grid, 1);


    }


    private void buttonBild() {

        horizontalLayout = new HorizontalLayout();


        Button add = new Button("add in", e -> {

        });


        Button delete = new Button("delete");


        Button edit = new Button("edit", e -> {
            if (grid.asSingleSelect().getValue() != null) {

            }
        });


        horizontalLayout.addComponents(add, edit, delete);

    }


    private void createFilter() {
        filter = new TextField();
        filter.setPlaceholder("input value");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
    }

    private void showPayment(String name) {
        if (name.equals("")) {
            createPagination(paymentsList);
        } else {
            List<Payment> list = ctx.getBean(PaymentService.class).findByName(name);
            createPagination(list);
        }
    }


    private void createPagination(List<Payment> list) {

        if (list.size() < 10) {
            grid.setItems(list);
        } else
            grid.setItems(list.subList(0, 10));

        PaginationResource paginationResource = PaginationResource.newBuilder().setPage(1).setLimit(10).build();
        pagination = new Pagination(paginationResource);

        pagination.setItemsPerPage(10, 20);
        pagination.setTotalCount(list.size());

        pagination.addPageChangeListener(new PaginationChangeListener() {
            @Override
            public void changed(PaginationResource event) {
                grid.setItems(list.subList(event.fromIndex(), event.toIndex()));
                grid.getDataProvider().refreshAll();
                grid.scrollToStart();
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
