package com.example.view;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class DashboardView extends VerticalLayout implements View {





    public DashboardView() {
        setSizeFull();
        HorizontalLayout layoutOne = new HorizontalLayout();


        layoutOne.setWidth(50, Unit.PERCENTAGE);


        layoutOne.addComponent(buildChartStudents());
        layoutOne.addComponent(buildChartStudents());

        HorizontalLayout layoutTwo = new HorizontalLayout();

        layoutTwo.setSizeFull();
        layoutTwo.setWidth(50, Unit.PERCENTAGE);

        layoutTwo.addComponent(buildChartStudents());
        layoutTwo.addComponent(buildChartStudents());

        addComponent(layoutOne);
        addComponent(layoutTwo);



    }


    private Component buildChartStudents() {
        BarChartConfig barConfig = new BarChartConfig();
        barConfig.
                data()
                .labels("January", "February", "March", "April", "May", "June", "July")
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
                data.add((Math.random() > 0.5 ? 1.0 : -1.0) * Math.round(Math.random() * 100));
            }
            lds.dataAsList(data);
        }

        ChartJs chart = new ChartJs(barConfig);
        chart.setJsLoggingEnabled(true);
        return chart;

    }







    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
