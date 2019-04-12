package com.example.view.editor;

import com.example.component.StatusLicenseEnum;
import com.example.model.Student;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.filesystemdataprovider.FilesystemData;
import org.vaadin.filesystemdataprovider.FilesystemDataProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class StudentViewEditor {

    private static final Logger logger = LoggerFactory.getLogger(StudentViewEditor.class);

    private BeanValidationBinder<Student> binder;
    private FormLayout layout;
    private HorizontalLayout hlayout;
    private Window sub;
    private Student student;
    private Grid grid;
    private TextField downloadPhotoText;

    public StudentViewEditor(Student student, Grid grid) {

        logger.debug("StudentViewEditor constructor invoked; " + student.getId());

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

        downloadPhotoText = new TextField("Download photo");
        downloadPhotoText.setValue(student.getLastName() == null ? "" : student.getPhoto());
        binder.forField(downloadPhotoText).withValidator(new StringLengthValidator("input name photo", 3, 100))
                .bind(Student::getPhoto, Student::setPhoto);
        layout.addComponent(downloadPhotoText);
        Button button = new Button("Open file");

        button.addClickListener(clickEvent -> fileTree());
        layout.addComponent(button);

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
        phoneText.setValue(student.getPhone() == null ? "" : student.getPhone());
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
        licenseSelect.setValue(student.getLicense());
        licenseSelect.setItems(StatusLicenseEnum.getTitles());
        licenseSelect.setEmptySelectionAllowed(false);
        binder.bind(licenseSelect, Student::getLicense, Student::setLicense);
        layout.addComponent(licenseSelect);

        buttonBuild();


        layout.addComponent(hlayout);
        sub.setContent(layout);
        UI.getCurrent().addWindow(sub);
    }


    private void fileTree() {

        logger.debug("fileTree method (class StudentViewEditor) invoked");

        Panel panel = new Panel();
        Window winTree = new Window();
        VerticalLayout layout = new VerticalLayout();

        File rootFile = new File("/");
        FilesystemData root = new FilesystemData(rootFile, false);
        FilesystemDataProvider fileSystem = new FilesystemDataProvider(root);
        final Tree<File> tree = new Tree<>();
        tree.setDataProvider(fileSystem);
        tree.setItemIconGenerator(item -> {
            if (item.isDirectory())
                return VaadinIcons.FOLDER;
            else return VaadinIcons.FILE;
        });

        tree.addItemClickListener(event -> {
            if (event.getItem().isFile()) {
                downloadPhotoText.setValue(event.getItem().getPath());
                winTree.close();
            }
        });
        panel.setSizeFull();
        panel.setContent(tree);
        layout.setHeight("300px");
        layout.setWidth("600px");
        layout.addComponent(panel);
        winTree.setPositionX(600);
        winTree.setPositionY(250);
        winTree.setContent(layout);
        UI.getCurrent().addWindow(winTree);
    }

    public void buttonBuild() {

        logger.debug("buttonBuild method (class StudentViewEditor) invoked");

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
