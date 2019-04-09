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
import com.example.model.Payment;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.Renderer;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class DashboardView extends VerticalLayout implements View {

    private ApplicationContext ctx;
    private List<Payment> paymentsList;
    private Grid<Payment> grid;


    public DashboardView() {
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
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((Math.random() > 0.5 ? 1.0 : 0.0) * Math.round(Math.random() * 100));
            }
            lds.dataAsList(data);
        }
        ChartJs chart = new ChartJs(barConfig);
        chart.setJsLoggingEnabled(true);
        chart.setHeight(50, Unit.PERCENTAGE);
        chart.setWidth(70, Unit.PERCENTAGE);
        return chart;

    }


    private Component buildChartTeachers() {
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
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((Math.random() > 0.5 ? 1.0 : 0.0) * Math.round(Math.random() * 100));
            }
            lds.dataAsList(data);
        }
        ChartJs chart = new ChartJs(barConfig);
        chart.setJsLoggingEnabled(true);
        chart.setHeight(50, Unit.PERCENTAGE);
        chart.setWidth(70, Unit.PERCENTAGE);
        return chart;

    }


    public Component buildChartMoney() {
        LineChartConfig config = new LineChartConfig();
        config.data()
                .labels("January", "February", "March", "April", "May", "June", "August", "September", "October", "November", "December")
                .addDataset(new LineDataset().label("Money")
                        .data(10d, 30d, 46d, 2d, 8d, 50d, 0d)
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

        ChartJs chart = new ChartJs(config);
        chart.addStyleName("chart-container");
        chart.setJsLoggingEnabled(true);
        chart.setHeight(50, Unit.PERCENTAGE);
        chart.setWidth(70, Unit.PERCENTAGE);

        return chart;
    }


    public Component buildTablePayment() {

        ctx = ApplicationContextHolder.getApplicationContext();
        paymentsList = ctx.getBean(PaymentService.class).findAll();


        grid = new Grid<>(Payment.class);
        grid.setItems(paymentsList);
        grid.setColumns("paymentDate", "sumPayment");
        grid.setColumnOrder(grid.addColumn(item -> (item.getStudent().getLastName())).setCaption("Student"));
        grid.getColumn("paymentDate").setRenderer((Renderer) (new DateRenderer("%1$td-%1$tm-%1$tY")));


        grid.setWidth(70, Unit.PERCENTAGE);

        return grid;
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
