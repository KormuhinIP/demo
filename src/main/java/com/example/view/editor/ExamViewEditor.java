package com.example.view.editor;

import com.example.component.EvaluationEnum;
import com.example.component.KindExamEnum;
import com.example.component.MyNotNullValidator;
import com.example.impl.ApplicationContextHolder;
import com.example.impl.StudentService;
import com.example.model.Exam;
import com.example.model.Student;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.Validator;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class ExamViewEditor {


    private static final Logger logger = LoggerFactory.getLogger(ExamViewEditor.class);


    private FormLayout layout;
    private HorizontalLayout hlayout;
    private Window sub;
    private BeanValidationBinder<Exam> binder;
    private Exam exam;
    private Grid grid;
    private List<Student> listStudents;
    private ApplicationContext ctx;


    public ExamViewEditor(Exam exam, Grid grid) {

        logger.debug("ExamViewEditor constructor invoked; " + exam.getId());

        this.exam = exam;
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

        binder = new BeanValidationBinder<>(Exam.class);

        DateField dateExam = new DateField("Date Exam");
        dateExam.setValue(exam.getDateExam() == null ? null : LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(exam.getDateExam())));
        dateExam.setResolution(DateResolution.DAY);
        binder.forField(dateExam).withValidator(new DateRangeValidator("input Birth Day", LocalDate.of(2018, 1, 1), LocalDate.now()))
            .withConverter(new LocalDateToDateConverter())
            .bind(Exam::getDateExam, Exam::setDateExam);
        layout.addComponent(dateExam);

        ComboBox kindExam = new ComboBox("Kind Exam", KindExamEnum.getTitles());   //I do not know how make adding Enum to ComboBox better
        kindExam.setValue(exam.getKindExam());
        kindExam.setEmptySelectionAllowed(false);
        binder.bind(kindExam, Exam::getKindExam, Exam::setKindExam);
        layout.addComponent(kindExam);

        TextField commentText = new TextField("Comments");
        commentText.setValue(exam.getComment() == null ? "" : exam.getComment());
        binder.forField(commentText).withValidator(new StringLengthValidator("input first name", 3, 100))
            .bind(Exam::getComment, Exam::setComment);
        layout.addComponent(commentText);

        ComboBox evaluation = new ComboBox("Evaluation", EvaluationEnum.getTitles()); //I do not know how make adding Enum to ComboBox better
        evaluation.setValue(exam.getEvaluation());
        evaluation.setEmptySelectionAllowed(false);
        binder.bind(evaluation, Exam::getEvaluation, Exam::setEvaluation);
        layout.addComponent(evaluation);

        ComboBox<Student> students = new ComboBox<>("Student", listStudents);
        students.setItemCaptionGenerator(Student::getLastName);
        students.setValue(exam.getStudent());
        students.setEmptySelectionAllowed(false);
        binder.forField(students).withValidator((Validator<Student>) new MyNotNullValidator("choose student"))
            .bind(Exam::getStudent, Exam::setStudent);
        layout.addComponent(students);

        buttonBuild();

        layout.addComponent(hlayout);
        sub.setContent(layout);
        UI.getCurrent().addWindow(sub);
    }


    public void buttonBuild() {

        logger.debug("ButtonBuild method (class ExamViewEditor) invoked");

        Button buttonOk = new Button("OK");
        buttonOk.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    binder.writeBean(exam);
                    grid.setItems(exam);
                    grid.getDataProvider().refreshAll();
                    grid.scrollToStart();

                } catch (ValidationException e) {
                    Notification.show("Exam could not be saved, " +
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
