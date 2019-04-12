package com.example.view.editor;

import com.example.model.Teacher;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class TeacherViewEditor {

    private static final Logger logger = LoggerFactory.getLogger(TeacherViewEditor.class);


    final FormLayout layout;
    final HorizontalLayout hlayout;
    final Window sub;
    BeanValidationBinder<Teacher> binder;
    Teacher teacher;
    Grid grid;


    public TeacherViewEditor(Teacher teacher, Grid grid) {



        this.teacher = teacher;
        this.grid = grid;


        layout = new FormLayout();
        layout.setMargin(true);
        hlayout = new HorizontalLayout();

        sub = new Window("edit/add");
        sub.setHeight("500px");
        sub.setWidth("500px");
        sub.setPositionX(600);
        sub.setPositionY(150);

        binder = new BeanValidationBinder<>(Teacher.class);


        TextField lastNameText = new TextField("Last Name");
        lastNameText.setValue(teacher.getLastName() == null ? "" : teacher.getLastName());
        binder.forField(lastNameText).withValidator(new StringLengthValidator("input last name", 3, 25))
                .bind(Teacher::getLastName, Teacher::setLastName);
        layout.addComponent(lastNameText);

        TextField firstNameText = new TextField("First Name");
        firstNameText.setValue(teacher.getFirstName() == null ? "" : teacher.getFirstName());
        binder.forField(firstNameText).withValidator(new StringLengthValidator("input first name", 3, 25))
                .bind(Teacher::getFirstName, Teacher::setFirstName);
        layout.addComponent(firstNameText);

        TextField patronymicText = new TextField("Patronymic");
        patronymicText.setValue(teacher.getPatronymic() == null ? "" : teacher.getPatronymic());
        binder.forField(patronymicText).withValidator(new StringLengthValidator("input patronymic", 3, 25))
                .bind(Teacher::getPatronymic, Teacher::setPatronymic);
        layout.addComponent(patronymicText);


        DateField birthDay = new DateField("Birth Day");
        birthDay.setValue(teacher.getBirthDay() == null ? null : LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(teacher.getBirthDay())));
        birthDay.setResolution(DateResolution.DAY);
        binder.forField(birthDay).withValidator(new DateRangeValidator("input Birth Day", LocalDate.of(1900, 1, 1), LocalDate.of(2002, 1, 1)))
                .withConverter(new LocalDateToDateConverter())
                .bind(Teacher::getBirthDay, Teacher::setBirthDay);
        layout.addComponent(birthDay);

        TextField experiance = new TextField("Experience");
        experiance.setValue(teacher.getExperience() == null ? "" : String.valueOf(teacher.getExperience()));
        binder.forField(experiance).withValidator(new StringLengthValidator("input experience (years)", 1, 2))
                .withConverter(new StringToIntegerConverter("input experience (years)"))
                .bind(Teacher::getExperience, Teacher::setExperience);
        layout.addComponent(experiance);

        TextField numberLicense = new TextField("Number License");
        numberLicense.setValue(teacher.getNumberLicense() == null ? "" : teacher.getNumberLicense());
        binder.forField(numberLicense).withValidator(new StringLengthValidator("input number License", 3, 25))
                .bind(Teacher::getNumberLicense, Teacher::setNumberLicense);
        layout.addComponent(numberLicense);


        ButtonBild();


        layout.addComponent(hlayout);
        sub.setContent(layout);
        UI.getCurrent().addWindow(sub);
    }


    public void ButtonBild() {


        Button buttonOk = new Button("OK");
        buttonOk.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    binder.writeBean(teacher);
                    grid.setItems(teacher);
                    grid.getDataProvider().refreshAll();
                    grid.scrollToStart();

                } catch (ValidationException e) {
                    Notification.show("Teacher could not be saved, " +
                            "please check error messages for each field.");
                    logger.info(e.toString() + getClass().getName());
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
