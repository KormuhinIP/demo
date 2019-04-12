package com.example.view.editor;

import com.example.component.MyNotNullValidator;
import com.example.impl.ApplicationContextHolder;
import com.example.impl.StudentService;
import com.example.model.Payment;
import com.example.model.Student;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.Validator;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class PaymentViewEditor {

    private static final Logger logger = LoggerFactory.getLogger(PaymentViewEditor.class);

    private FormLayout layout;
    private HorizontalLayout hlayout;
    private Window sub;
    private BeanValidationBinder<Payment> binder;
    private Payment payment;
    private Grid grid;
    private List<Student> listStudents;
    private ApplicationContext ctx;


    public PaymentViewEditor(Payment payment, Grid grid) {

        logger.debug("PaymentViewEditor constructor invoked; " + payment.getId());

        this.payment = payment;
        this.grid = grid;
        ctx = ApplicationContextHolder.getApplicationContext();
        listStudents = ctx.getBean(StudentService.class).findAll();
        layout = new FormLayout();
        layout.setMargin(true);
        hlayout = new HorizontalLayout();
        sub = new Window("edit/add");
        sub.setHeight("400px");
        sub.setWidth("400px");
        sub.setPositionX(600);
        sub.setPositionY(150);

        binder = new BeanValidationBinder<>(Payment.class);

        ComboBox<Student> students = new ComboBox<>("Student", listStudents);
        students.setItemCaptionGenerator(Student::getLastName);
        students.setValue(payment.getStudent());
        students.setEmptySelectionAllowed(false);
        binder.forField(students).withValidator((Validator<Student>) new MyNotNullValidator("choose student"))
            .bind(Payment::getStudent, Payment::setStudent);
        layout.addComponent(students);

        DateField paymentDate = new DateField("Payment Date");
        paymentDate.setValue(payment.getPaymentDate() == null ? null : LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(payment.getPaymentDate())));
        paymentDate.setResolution(DateResolution.DAY);
        binder.forField(paymentDate).withValidator(new DateRangeValidator("input Payment Date", LocalDate.of(1900, 1, 1), LocalDate.now()))
                .withConverter(new LocalDateToDateConverter())
                .bind(Payment::getPaymentDate, Payment::setPaymentDate);
        layout.addComponent(paymentDate);

        TextField sumPayment = new TextField("Sum Payment");

        sumPayment.setValue(payment.getSumPayment() == null ? "" : payment.getSumPayment().toString());
        binder.forField(sumPayment).withConverter(new StringToDoubleConverter("input sum payment"))
                .bind(Payment::getSumPayment, Payment::setSumPayment);
        layout.addComponent(sumPayment);

        buttonBuild();

        layout.addComponent(hlayout);
        sub.setContent(layout);
        UI.getCurrent().addWindow(sub);
    }


    public void buttonBuild() {

        logger.debug("ButtonBuild method (class PaymentViewEditor) invoked");

        Button buttonOk = new Button("OK");
        buttonOk.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    binder.writeBean(payment);
                    grid.setItems(payment);
                    grid.getDataProvider().refreshAll();
                    grid.scrollToStart();

                } catch (ValidationException e) {
                    Notification.show("Payment could not be saved, " +
                            "please check error messages for each field.");
                    logger.error(e.toString() + getClass().getName());
                }

                sub.close();
            }
        });

        Button buttonApply = new Button("Apply");
        buttonApply.addClickListener((Button.ClickListener) event -> sub.close());

        Button buttonCancel = new Button("Cancel");
        buttonCancel.addClickListener((Button.ClickListener) event -> sub.close());

        hlayout.addComponents(buttonOk, buttonApply, buttonCancel);

    }

}
