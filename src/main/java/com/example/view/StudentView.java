package com.example.view;

import com.example.impl.ApplicationContextHolder;
import com.example.impl.StudentService;
import com.example.model.Student;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.ApplicationContext;

import java.util.List;


public class StudentView extends VerticalLayout implements View {


    private final ApplicationContext ctx;

    private Student customer;

    private Binder<Student> binder = new Binder<>(Student.class);

    private Grid<Student> grid = new Grid(Student.class);
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private Button save = new Button("Save", e -> saveCustomer());


    public StudentView() {

        ctx = ApplicationContextHolder.getApplicationContext();


        updateGrid();
        grid.setColumns("firstName", "lastName");
        grid.addSelectionListener(e -> updateForm());

        binder.bindInstanceFields(this);

        addComponents(grid, firstName, lastName, save);

    }

    private void updateGrid() {
        List<Student> customers = ctx.getBean(StudentService.class).findAll();
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
        ctx.getBean(StudentService.class).update(customer);
        updateGrid();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
