package com.example.view;

import com.example.impl.ApplicationContextHolder;
import com.example.impl.CustomerService;
import com.example.model.Customer;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.ApplicationContext;

import java.util.List;


public class Student extends VerticalLayout implements View {


    private final ApplicationContext ctx;

    private Customer customer;

    private Binder<Customer> binder = new Binder<>(Customer.class);

    private Grid<Customer> grid = new Grid(Customer.class);
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private Button save = new Button("Save", e -> saveCustomer());


    public Student() {

        ctx = ApplicationContextHolder.getApplicationContext();


        updateGrid();
        grid.setColumns("firstName", "lastName");
        grid.addSelectionListener(e -> updateForm());

        binder.bindInstanceFields(this);

        addComponents(grid, firstName, lastName, save);

    }

    private void updateGrid() {
        List<Customer> customers = ctx.getBean(CustomerService.class).findAll();
        grid.setItems(customers);
        setFormVisible(false);
    }

    private void updateForm() {
        if (grid.asSingleSelect().isEmpty()) {
            setFormVisible(false);
        } else {
            customer = grid.asSingleSelect().getValue();
            binder.setBean(customer);
            setFormVisible(true);
        }
    }

    private void setFormVisible(boolean visible) {
        firstName.setVisible(visible);
        lastName.setVisible(visible);
        save.setVisible(visible);
    }

    private void saveCustomer() {
        ctx.getBean(CustomerService.class).update(customer);
        updateGrid();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
