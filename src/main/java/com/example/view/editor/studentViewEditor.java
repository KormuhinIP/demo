package com.example.view.editor;

import com.example.component.StatusLicenseEnum;
import com.example.model.Student;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class studentViewEditor {


    BeanValidationBinder<Student> binder;


    final FormLayout layout;
    final HorizontalLayout hlayout;
    final Window sub;
    Student student;
    Grid grid;


    public studentViewEditor(Student student, Grid grid) {


        this.student = student;

        this.grid = grid;


        layout = new FormLayout();
        layout.setMargin(true);
        hlayout = new HorizontalLayout();

        sub = new Window("edit/add");
        sub.setHeight("500px");
        sub.setWidth("500px");
        sub.setPositionX(600);
        sub.setPositionY(150);

        binder = new BeanValidationBinder<>(Student.class);

        TextField downloadPhotoText = new TextField("Download photo");
        downloadPhotoText.setValue(student.getLastName() == null ? "" : student.getPhoto());
        binder.forField(downloadPhotoText).withValidator(new StringLengthValidator("input name photo", 3, 25))
                .bind(Student::getPhoto, Student::setPhoto);
        layout.addComponent(downloadPhotoText);

        TextField lastNameText = new TextField("Last Name");
        lastNameText.setValue(student.getLastName() == null ? "" : student.getLastName());

        binder.forField(lastNameText).withValidator(new StringLengthValidator("input last name", 3, 25))
                .bind(Student::getLastName, Student::setLastName);

        layout.addComponent(lastNameText);

        TextField firstNameText = new TextField("First Name");
        firstNameText.setValue(student.getFirstName() == null ? "" : student.getFirstName());
        binder.forField(firstNameText).withValidator(new StringLengthValidator("input first name", 3, 25))
                .bind(Student::getFirstName, Student::setFirstName);
        layout.addComponent(firstNameText);

        TextField patronymicText = new TextField("Patronymic");
        patronymicText.setValue(student.getPatronymic() == null ? "" : student.getPatronymic());
        binder.forField(patronymicText).withValidator(new StringLengthValidator("input patronymic", 3, 25))
                .bind(Student::getPatronymic, Student::setPatronymic);
        layout.addComponent(patronymicText);

        TextField phoneText = new TextField("Phone");
        phoneText.setValue(student.getPhone() == null ? "" : String.valueOf(student.getPhone()));
        binder.forField(phoneText).withValidator(new RegexpValidator("input phone in the format: +7-917-818-0299",
                "^^(\\+)([0-9]-[0-9]{3}-[0-9]{3}-[0-9]{4})"))
                .bind(Student::getPhone, Student::setPhone);
        layout.addComponent(phoneText);

        DateField birthDay = new DateField("Birth Day");
        birthDay.setValue(student.getBirthDay() == null ? null : LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(student.getBirthDay())));
        birthDay.setResolution(DateResolution.DAY);
        binder.forField(birthDay).withValidator(new DateRangeValidator("input Birth Day", LocalDate.of(1900, 1, 1), LocalDate.of(2002, 1, 1)))
                .withConverter(new LocalDateToDateConverter())
                .bind(Student::getBirthDay, Student::setBirthDay);

        layout.addComponent(birthDay);

        ComboBox licenseSelect = new ComboBox("License");
        licenseSelect.setValue(student.getLicense() == null ? null : student.getLicense());
        licenseSelect.setItems(StatusLicenseEnum.YES.getTitle(), StatusLicenseEnum.NO.getTitle());
        licenseSelect.setEmptySelectionAllowed(false);
        binder.bind(licenseSelect, Student::getLicense, Student::setLicense);
        layout.addComponent(licenseSelect);

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
                    binder.writeBean(student);
                    grid.setItems(student);
                    grid.getDataProvider().refreshAll();
                    grid.scrollToStart();

                } catch (ValidationException e) {
                    Notification.show("Student could not be saved, " +
                            "please check error messages for each field.");
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
