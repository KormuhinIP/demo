package com.example.view.editor;

import com.example.component.StatusLicenseEnum;
import com.example.model.Student;
import com.vaadin.ui.*;

import java.time.LocalDate;

public class studentViewEditor {

    final FormLayout layout;
    final HorizontalLayout hlayout;
    final Window sub;

    public studentViewEditor(Student student) {

        layout = new FormLayout();
        layout.setMargin(true);
        hlayout = new HorizontalLayout();

        sub = new Window("edit/add");
        sub.setHeight("500px");
        sub.setWidth("500px");
        sub.setPositionX(600);
        sub.setPositionY(150);

        TextField downloadPhotoText = new TextField("Download photo");
        layout.addComponent(downloadPhotoText);

        TextField lastNameText = new TextField("Last Name");
        lastNameText.setValue(student == null ? "" : student.getLastName());
        layout.addComponent(lastNameText);

        TextField firstNameText = new TextField("First Name");
        firstNameText.setValue(student == null ? "" : student.getFirstName());
        layout.addComponent(firstNameText);

        TextField patronymicText = new TextField("Patronymic");
        patronymicText.setValue(student == null ? "" : student.getPatronymic());
        layout.addComponent(patronymicText);

        TextField phoneText = new TextField("Phone");
        phoneText.setValue(student == null ? "" : student.getPhone());
        layout.addComponent(phoneText);

        DateField birthDayText = new DateField("Birth Day");
        birthDayText.setValue(student == null ? null : LocalDate.parse(String.valueOf(student.getBirthDay())));
        layout.addComponent(birthDayText);

        ComboBox licenseSelect = new ComboBox("License");
        licenseSelect.setValue(student == null ? null : student.getLicense());
        licenseSelect.setItems(StatusLicenseEnum.YES.getTitle(), StatusLicenseEnum.NO.getTitle());
        licenseSelect.setEmptySelectionAllowed(false);
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
