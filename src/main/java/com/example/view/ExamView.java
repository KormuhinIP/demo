package com.example.view;

import com.example.impl.ApplicationContextHolder;
import com.example.impl.ExamService;
import com.example.model.Exam;
import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ExamView extends VerticalLayout implements View {


    private ApplicationContext ctx;
    private List<Exam> examsList;
    private Pagination pagination;
    private Grid<Exam> grid;
    private HorizontalLayout horizontalLayout;
    private TextField filter;


    public ExamView() {


        ctx = ApplicationContextHolder.getApplicationContext();
        examsList = ctx.getBean(ExamService.class).findAll();
        grid = new Grid<>(Exam.class);

        addComponent(grid);

/*

        showStudent("");

       // createFilter();
        filter.addValueChangeListener(e -> showStudent(e.getValue()));


        grid.setColumns("dataExam", "student", "kindExam", "comment", "evaluation");



        setHeight("100%");
        grid.setHeight("100%");
        grid.setRowHeight(64);
        grid.setWidth("100%");


      //  buttonBild();

        addComponents(filter, grid, pagination, horizontalLayout);
        setExpandRatio(grid, 1);

*/

    }


    private void showStudent(String name) {
        if (name.equals("")) {
            createPagination(examsList);
        } else {
            List<Exam> list = ctx.getBean(ExamService.class).findByName(name);
            createPagination(list);
        }
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



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
