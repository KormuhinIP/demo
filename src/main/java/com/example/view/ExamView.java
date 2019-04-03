package com.example.view;

import com.example.model.Student;
import com.vaadin.addon.pagination.Pagination;
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
    private List<Student> studentsList;
    private Pagination pagination;
    private Grid<Student> grid;
    private HorizontalLayout horizontalLayout;
    private TextField filter;


    public ExamView() {


    }




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
