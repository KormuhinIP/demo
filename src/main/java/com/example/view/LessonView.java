package com.example.view;

import com.example.component.TrackView;
import com.example.impl.ApplicationContextHolder;
import com.example.impl.LessonService;
import com.example.model.Lesson;
import com.example.model.Teacher;
import com.example.view.editor.LessonViewEditor;
import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;


public class LessonView extends VerticalLayout implements View {

    private static final Logger logger = LoggerFactory.getLogger(LessonView.class);

    private ApplicationContext ctx;
    private List<Lesson> lessonsList;
    private Pagination pagination;
    private Grid<Lesson> grid;
    private HorizontalLayout horizontalLayout;
    private ComboBox<Teacher> filter;
    private List<Teacher> listTeachers;


    public LessonView() {

        logger.debug("LessonView constructor invoked;");

        ctx = ApplicationContextHolder.getApplicationContext();
        lessonsList = ctx.getBean(LessonService.class).findAll();
        listTeachers = ctx.getBean(LessonService.class).listTeachers();

        grid = new Grid<>(Lesson.class);

        showLesson(null);

        createFilter(listTeachers);
        filter.addValueChangeListener(e -> showLesson(e.getValue()));
        grid.setColumns();
        grid.addColumn(Lesson::getDateLesson).setRenderer((Renderer) (new DateRenderer("%1$td-%1$tm-%1$tY"))).setCaption("Date lesson");
        grid.addColumn(Lesson::getDateLesson).setRenderer((Renderer) (new DateRenderer("%1$tH.%1$tM"))).setCaption("Time lesson");
        grid.addColumn(item -> (item.getStudent().getLastName())).setCaption("Student");
        grid.addColumn(item -> (item.getTeacher().getLastName())).setCaption("Teacher");
        grid.addComponentColumn(person -> new Button("Track", e -> new TrackView())).setCaption("GPS Track");
        setHeight("100%");
        grid.setHeight("100%");
        grid.setRowHeight(63);
        grid.setWidth("100%");

        buttonBuild();

        addComponents(filter, grid, pagination, horizontalLayout);
        setExpandRatio(grid, 1);
    }

    private void showLesson(Teacher teacher) {

        logger.debug("showLesson method (LessonView) invoked; " + teacher);

        if (teacher == null) {
            logger.warn("teacher=null, exception may occur");
            createPagination(lessonsList);
        } else {
            List<Lesson> list = ctx.getBean(LessonService.class).findLessons(teacher.getId());
            createPagination(list);
        }
    }

    private void createFilter(List<Teacher> listTeachers) {

        logger.debug("createFilter method  invoked " + listTeachers);

        filter = new ComboBox<>("Teacher", listTeachers);
        filter.setPlaceholder("choose teacher");
        filter.setItemCaptionGenerator(Teacher::getLastName);
    }

    private void createPagination(List<Lesson> list) {

        logger.debug("createPagination method (LessonView) invoked; " + list);

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


    private void buttonBuild() {

        logger.debug("buttonBuild method (LessonView) invoked");

        horizontalLayout = new HorizontalLayout();

        Button add = new Button("add in", e -> {
            new LessonViewEditor(new Lesson(), grid);
        });

        Button delete = new Button("delete");

        Button edit = new Button("edit", e -> {
            if (grid.asSingleSelect().getValue() != null) {
                new LessonViewEditor(grid.asSingleSelect().getValue(), grid);
            }
        });

        horizontalLayout.addComponents(add, edit, delete);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
