package agh.ics.oop.gui.charts;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class StatsChart  {


    private final LineChart<Number,Number> chart;
    private final XYChart.Series<Number,Number> series;
    private final NumberAxis xAxis,yAxis;
    private int minX,maxX,x;

    public StatsChart(String name){
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);


        xAxis.setTickMarkVisible(false);
        yAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        yAxis.setMinorTickVisible(false);
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
        xAxis.setLabel(name);

        chart = new LineChart<>(xAxis,yAxis);
        chart.setPrefSize(240,100);
        chart.setCreateSymbols(false);
        series = new XYChart.Series<>();

        this.chart.getData().add(series);
        minX=0;
        maxX=200;
        x=0;
    }

    public void update(Double value){


        series.getData().add(new XYChart.Data<>(x++,value));
        if(series.getData().size()>200){

            series.getData().remove(0,1);
            minX++;
            maxX++;
        }

        xAxis.setLowerBound(minX);
        xAxis.setUpperBound(maxX);
        xAxis.setTickUnit(25);
        yAxis.setUpperBound(yAxis.getUpperBound()*1.2);



    }

    public LineChart<Number,Number> getChart(){
        return chart;
    }
}
