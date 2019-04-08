package com.example.view;

import com.example.impl.ApplicationContextHolder;
import com.example.impl.LessonService;
import com.example.model.Lesson;
import com.vaadin.addon.pagination.Pagination;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.Renderer;
import org.springframework.context.ApplicationContext;

import java.util.List;


public class LessonView extends VerticalLayout implements View {

    private ApplicationContext ctx;
    private List<Lesson> lessonsList;
    private Pagination pagination;
    private Grid<Lesson> grid;
    private HorizontalLayout horizontalLayout;
    private DateField filter;


    public LessonView() {

        ctx = ApplicationContextHolder.getApplicationContext();
        lessonsList = ctx.getBean(LessonService.class).findAll();
        grid = new Grid<>(Lesson.class);


        //  showLesson("");

        //   createFilter();
        //  filter.addValueChangeListener(e -> showLesson(e.getValue()));

        grid.setItems(lessonsList);


        grid.setColumns("dateLesson");

        grid.addColumn(Lesson::getDateLesson).setRenderer((Renderer) (new DateRenderer("%1$td-%1$tm-%1$tY"))).setCaption("Date lesson");
        grid.addColumn(Lesson::getDateLesson).setRenderer((Renderer) (new DateRenderer("%1$tH.%1$tM"))).setCaption("Time lesson");
        grid.addColumn(item -> (item.getStudent().getLastName())).setCaption("Student");
        grid.addColumn(item -> (item.getTeacher().getLastName())).setCaption("Teacher");

        setHeight("100%");
        grid.setHeight("100%");
        grid.setRowHeight(64);
        grid.setWidth("100%");


        //  buttonBild();

        addComponents(grid);
        setExpandRatio(grid, 1);


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


    }
}
