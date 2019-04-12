package com.example.view;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.config.LineChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.LineDataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.Tooltips;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.example.impl.ApplicationContextHolder;
import com.example.impl.PaymentService;
import com.example.impl.StudentService;
import com.example.impl.TeacherService;
import com.example.model.Payment;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class DashboardView extends VerticalLayout implements View {

    private static final Logger logger = LoggerFactory.getLogger(DashboardView.class);

    private ApplicationContext ctx = ApplicationContextHolder.getApplicationContext();
    private List<Payment> paymentsList;
    private Grid<Payment> grid;


    public DashboardView() {

        logger.debug("DashboardView constructor invoked;");

        setSizeFull();
        HorizontalLayout layoutOne = new HorizontalLayout();
        layoutOne.setSizeFull();
        layoutOne.addComponent(buildChartStudents());
        layoutOne.addComponent(buildChartTeachers());
        HorizontalLayout layoutTwo = new HorizontalLayout();
        layoutTwo.setSizeFull();
        layoutTwo.addComponent(buildChartMoney());
        layoutTwo.addComponent(buildTablePayment());
        addComponent(layoutOne);
        addComponent(layoutTwo);
    }


    private Component buildChartStudents() {

        logger.debug("buildChartStudents method (DashboardView) invoked;");

        BarChartConfig barConfig = new BarChartConfig();
        barConfig.
            data()
            .labels("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
            .addDataset(
                new BarDataset().backgroundColor("red").label("Students").yAxisID("y-axis-1"))
            .and();
        barConfig.
            options()
            .responsive(true)
            .hover()
            .mode(InteractionMode.INDEX)
            .intersect(true)
            .animationDuration(400)
            .and()
            .title()
            .display(true)
            .text("Students")
            .and()
            .scales()
            .add(Axis.Y, new LinearScale().display(true).position(Position.LEFT).id("y-axis-1"))
            .and()
            .done();

        List<String> labels = barConfig.data().getLabels();
        for (Dataset<?, ?> ds : barConfig.data().getDatasets()) {
            BarDataset lds = (BarDataset) ds;
            List<Double> listStudents = new ArrayList<>();
            for (int i = 1; i <= labels.size(); i++) {
                listStudents.add(Double.valueOf(ctx.getBean(StudentService.class).getStudentStatistic(i))); // I know that it is bad code, but I could not make needed query
            }
            lds.dataAsList(listStudents);
        }
        ChartJs chart = new ChartJs(barConfig);
        chart.setJsLoggingEnabled(true);
        chart.setHeight(50, Unit.PERCENTAGE);
        chart.setWidth(70, Unit.PERCENTAGE);
        return chart;
    }


    private Component buildChartTeachers() {

        logger.debug("buildChartTeachers method (DashboardView) invoked;");

        BarChartConfig barConfig = new BarChartConfig();
        barConfig.
            data()
            .labels("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
            .addDataset(
                new BarDataset().backgroundColor("blue").label("Teachers").yAxisID("y-axis-1"))
            .and();
        barConfig.
            options()
            .responsive(true)
            .hover()
            .mode(InteractionMode.INDEX)
            .intersect(true)
            .animationDuration(400)
            .and()
            .title()
            .display(true)
            .text("Teachers")
            .and()
            .scales()
            .add(Axis.Y, new LinearScale().display(true).position(Position.LEFT).id("y-axis-1"))
            .and()
            .done();

        List<String> labels = barConfig.data().getLabels();
        for (Dataset<?, ?> ds : barConfig.data().getDatasets()) {
            BarDataset lds = (BarDataset) ds;
            List<Double> listTeachers = new ArrayList<>();
            for (int i = 1; i <= labels.size(); i++) {
                listTeachers.add(Double.valueOf(ctx.getBean(TeacherService.class).getTeacherStatistic(i))); // I know that it is bad code, but I could not make needed query
            }
            lds.dataAsList(listTeachers);
        }
        ChartJs chart = new ChartJs(barConfig);
        chart.setJsLoggingEnabled(true);
        chart.setHeight(50, Unit.PERCENTAGE);
        chart.setWidth(70, Unit.PERCENTAGE);
        return chart;
    }

    public Component buildChartMoney() {

        logger.debug(" buildChartMoney method (DashboardView) invoked;");

        LineChartConfig config = new LineChartConfig();
        config.data()
            .labels("January", "February", "March", "April", "May", "June", "August", "September", "October", "November", "December")
            .addDataset(new LineDataset().label("Money")
                .data()
                .fill(false))
            .and()
            .options()
            .responsive(true)
            .title()
            .display(true)
            .text("Chart of Money")
            .and()
            .tooltips()
            .position(Tooltips.PositionMode.NEAREST)
            .mode(InteractionMode.INDEX)
            .intersect(false)
            .yPadding(10)
            .xPadding(10)
            .caretSize(8)
            .caretPadding(10)
            .backgroundColor("rgba(72, 241, 12, 1)")
            .borderColor("rgba(0,0,0,1)")
            .borderWidth(4)
            .and()
            .done();

        List<String> labels = config.data().getLabels();
        for (Dataset<?, ?> ds : config.data().getDatasets()) {
            LineDataset lds = (LineDataset) ds;
            List<Double> listPayment = new ArrayList<>();
            for (int i = 1; i <= labels.size(); i++) {
                Double sumOfMonth = 0.0;
                try {
                    sumOfMonth = ctx.getBean(PaymentService.class).paymentOfMonth(i);
                } catch (NullPointerException e) {
                    logger.error(e.toString() + getClass().getName());
                }
                listPayment.add(sumOfMonth);
            }
            lds.dataAsList(listPayment);
        }

        ChartJs chart = new ChartJs(config);
        chart.addStyleName("chart-container");
        chart.setJsLoggingEnabled(true);
        chart.setHeight(50, Unit.PERCENTAGE);
        chart.setWidth(70, Unit.PERCENTAGE);
        return chart;
    }

    public Component buildTablePayment() {

        logger.debug("buildTablePayment method (DashboardView) invoked;");

        paymentsList = ctx.getBean(PaymentService.class).findAll();
        grid = new Grid<>(Payment.class);
        grid.setItems(paymentsList);
        grid.setColumns("paymentDate", "sumPayment");
        grid.setColumnOrder(grid.addColumn(item -> (item.getStudent().getLastName())).setCaption("Student"));
        grid.getColumn("paymentDate").setRenderer((Renderer) (new DateRenderer("%1$td-%1$tm-%1$tY"))); //Did not render without transformation
        grid.setWidth(70, Unit.PERCENTAGE);
        return grid;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
