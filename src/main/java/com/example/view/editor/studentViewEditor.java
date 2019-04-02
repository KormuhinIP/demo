package com.example.view.editor;

import com.example.model.Student;
import com.vaadin.ui.*;

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
        layout.addComponent(lastNameText);

        TextField firstNameText = new TextField("First Name");
        layout.addComponent(firstNameText);

        TextField patronymicText = new TextField("Patronymic");
        layout.addComponent(patronymicText);

        TextField PhoneText = new TextField("Phone");
        layout.addComponent(PhoneText);

        DateField birthDayText = new DateField("Birth Day");
        layout.addComponent(birthDayText);

        ComboBox licenseSelect = new ComboBox("License");

        layout.addComponent(licenseSelect);


        createButton();

        layout.addComponent(hlayout);
        sub.setContent(layout);
        UI.getCurrent().addWindow(sub);
    }


    public void createButton() {

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
