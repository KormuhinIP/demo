package com.example.view.editor;

import com.example.component.MyNotNullValidator;
import com.example.component.TrackView;
import com.example.impl.ApplicationContextHolder;
import com.example.impl.StudentService;
import com.example.impl.TeacherService;
import com.example.model.Lesson;
import com.example.model.Student;
import com.example.model.Teacher;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.Validator;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.validator.DateTimeRangeValidator;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class LessonViewEditor {

    private static final Logger logger = LoggerFactory.getLogger(LessonViewEditor.class);


    final FormLayout layout;
    final HorizontalLayout hlayout;
    final Window sub;
    BeanValidationBinder<Lesson> binder;
    Lesson lesson;
    Grid grid;
    private List<Student> listStudents;
    private List<Teacher> listTeachers;
    private ApplicationContext ctx;


    public LessonViewEditor(Lesson lesson, Grid grid) {

        logger.debug("LessonViewEditor constructor invoked; " + lesson.getId());

        this.lesson = lesson;
        this.grid = grid;


        ctx = ApplicationContextHolder.getApplicationContext();
        listStudents = ctx.getBean(StudentService.class).findAll();
        listTeachers = ctx.getBean(TeacherService.class).findAll();


        layout = new FormLayout();
        layout.setMargin(true);
        hlayout = new HorizontalLayout();

        sub = new Window("edit/add");
        sub.setHeight("400px");
        sub.setWidth("400px");
        sub.setPositionX(600);
        sub.setPositionY(150);

        binder = new BeanValidationBinder<>(Lesson.class);

        DateTimeField dateLesson = new DateTimeField("Date Lesson");
        dateLesson.setValue(lesson.getDateLesson() == null ? null : LocalDateTime.ofInstant(lesson.getDateLesson().toInstant(), ZoneId.systemDefault()));
        dateLesson.setResolution(DateTimeResolution.MINUTE);
        binder.forField(dateLesson).withValidator(new DateTimeRangeValidator("input Date and Time Lesson"
            , LocalDateTime.now(), LocalDateTime.of(2019, 12, 30, 12, 12)))
            .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
            .bind(Lesson::getDateLesson, Lesson::setDateLesson);

        layout.addComponent(dateLesson);

        ComboBox<Student> students = new ComboBox<>("Student", listStudents);
        students.setItemCaptionGenerator(Student::getLastName);
        students.setValue(lesson.getStudent());
        students.setEmptySelectionAllowed(false);
        binder.forField(students).withValidator((Validator<Student>) new MyNotNullValidator("choose student"))
            .bind(Lesson::getStudent, Lesson::setStudent);
        layout.addComponent(students);

        ComboBox<Teacher> teachers = new ComboBox<>("Teacher", listTeachers);
        teachers.setItemCaptionGenerator(Teacher::getLastName);
        teachers.setValue(lesson.getTeacher());
        teachers.setEmptySelectionAllowed(false);
        binder.forField(teachers).withValidator((Validator<Teacher>) new MyNotNullValidator("choose teacher"))
            .bind(Lesson::getTeacher, Lesson::setTeacher);
        layout.addComponent(teachers);


        Button buttonGPS = new Button("Track", e -> new TrackView());
        layout.addComponent(buttonGPS);


        buttonBuild();

        layout.addComponent(hlayout);
        sub.setContent(layout);
        UI.getCurrent().addWindow(sub);
    }


    public void buttonBuild() {

        logger.debug("ButtonBuild method (class LessonViewEditor) invoked");

        Button buttonOk = new Button("OK");
        buttonOk.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    binder.writeBean(lesson);
                    grid.setItems(lesson);
                    grid.getDataProvider().refreshAll();
                    grid.scrollToStart();

                } catch (ValidationException e) {
                    Notification.show("Lesson could not be saved, " +
                        "please check error messages for each field.");
                    logger.error(e.toString() + getClass().getName());
                }

                sub.close();
            }
        });

        Button buttonApply = new Button("Apply");
        buttonApply.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

                sub.close();
            }
        });

        Button buttonCancel = new Button("Cancel");
        buttonCancel.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

                sub.close();
            }
        });

        hlayout.addComponents(buttonOk, buttonApply, buttonCancel);
    }

}
