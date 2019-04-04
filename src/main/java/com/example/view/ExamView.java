package com.example.view;

import com.example.impl.ApplicationContextHolder;
import com.example.impl.ExamService;
import com.example.model.Exam;
import com.example.view.editor.ExamViewEditor;
import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.Renderer;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.List;


public class ExamView extends VerticalLayout implements View {


    private ApplicationContext ctx;
    private List<Exam> examsList;
    private Pagination pagination;
    private Grid<Exam> grid;
    private HorizontalLayout horizontalLayout;
    private DateField filter;


    public ExamView() {


        ctx = ApplicationContextHolder.getApplicationContext();
        examsList = ctx.getBean(ExamService.class).findAll();
        grid = new Grid<>(Exam.class);

        showStudent(null);

        createFilter();

        filter.addValueChangeListener(e ->
                showStudent(e.getValue() != null ? java.sql.Date.valueOf(e.getValue()) : null));


        grid.setColumns("dateExam", "kindExam", "comment", "evaluation");
        grid.addColumn(item -> (item.getStudent().getLastName())).setCaption("Student");
        grid.getColumn("dateExam").setRenderer((Renderer) (new DateRenderer("%1$td-%1$tm-%1$tY")));

        setHeight("100%");
        grid.setHeight("100%");
        grid.setRowHeight(64);
        grid.setWidth("100%");

        buttonBild();

        addComponents(filter, grid, pagination, horizontalLayout);
        setExpandRatio(grid, 1);



    }


    private void showStudent(Date date) {
        if (date == null) {
            createPagination(examsList);
        } else {
            List<Exam> list = ctx.getBean(ExamService.class).findByDate(date);
            createPagination(list);
        }
    }


    private void createFilter() {
        filter = new DateField();
        filter.setPlaceholder("input date");
    }


    private void createPagination(List<Exam> list) {

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


    private void buttonBild() {

        horizontalLayout = new HorizontalLayout();


        Button add = new Button("add in", e -> {
            new ExamViewEditor(new Exam(), grid);
        });


        Button delete = new Button("delete");


        Button edit = new Button("edit", e -> {
            if (grid.asSingleSelect().getValue() != null) {
                new ExamViewEditor(grid.asSingleSelect().getValue(), grid);
            }
        });


        horizontalLayout.addComponents(add, edit, delete);
    }




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
