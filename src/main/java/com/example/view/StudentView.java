package com.example.view;

import com.example.impl.ApplicationContextHolder;
import com.example.impl.StudentService;
import com.example.model.Student;
import com.example.view.editor.studentViewEditor;
import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.renderers.ImageRenderer;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class StudentView extends VerticalLayout implements View {

    private final ApplicationContext ctx;
    private List<Student> studentsList;
    private Pagination pagination;
    private Grid<Student> grid;
    private HorizontalLayout horizontalLayout;
    private TextField filter;


    public StudentView() {

        ctx = ApplicationContextHolder.getApplicationContext();
        studentsList = ctx.getBean(StudentService.class).findAll();
        grid = new Grid<>(Student.class);

        showStudent("");

        createFilter();
        filter.addValueChangeListener(e -> showStudent(e.getValue()));


        grid.setColumns("lastName", "firstName", "patronymic", "phone", "birthDay", "license");

        Column<Student, ThemeResource> imageColum = grid.addColumn(p -> new ThemeResource("img/" + p.getPhoto() + ".jpg"),
                new ImageRenderer()).setCaption("Photo").setWidth(100);

        grid.setColumnOrder(imageColum);

        setHeight("100%");
        grid.setHeight("100%");
        grid.setRowHeight(64);
        grid.setWidth("100%");


        buttonBild();

        addComponents(filter, grid, pagination, horizontalLayout);
        setExpandRatio(grid, 1);


    }


    private void buttonBild() {
        Button add = new Button("add in");

        Button delete = new Button("delete");
        horizontalLayout = new HorizontalLayout();


        Button edit = new Button("edit", e -> {
            if (grid.asSingleSelect().getValue() != null) {
                new studentViewEditor(grid.asSingleSelect().getValue());
            }
        });


        horizontalLayout.addComponents(add, edit, delete);

    }


    private void createFilter() {
        filter = new TextField();
        filter.setPlaceholder("input value");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
    }

    private void showStudent(String name) {
        if (name.equals("")) {
            createPagination(studentsList);
        } else {
            List<Student> list = ctx.getBean(StudentService.class).findByName(name);
            createPagination(list);
        }
    }


    private void createPagination(List<Student> list) {

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
