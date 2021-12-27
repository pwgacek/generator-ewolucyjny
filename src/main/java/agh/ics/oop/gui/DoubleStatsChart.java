package agh.ics.oop.gui;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
public class DoubleStatsChart {


    private final ScatterChart<Number,Number> chart;
    private final XYChart.Series<Number,Number> firstSeries;
    private final XYChart.Series<Number,Number> secondSeries;
    private final NumberAxis xAxis,yAxis;
    private int minX,maxX,x;

    public DoubleStatsChart(String name){
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);

        xAxis.setTickMarkVisible(false);
        yAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        yAxis.setMinorTickVisible(false);

        this.chart = new ScatterChart<>(xAxis,yAxis);
        this.chart.setPrefSize(240,100);

        firstSeries = new XYChart.Series<>();
        secondSeries = new XYChart.Series<>();

        xAxis.setLabel(name);

        this.chart.getData().add(firstSeries);
        this.chart.getData().add(secondSeries);
        minX=0;
        maxX=200;
        x=0;
    }

    public void update(int firstValue,int secondValue){


        //Double maxY = (Double) Collections.max(series.getData().toArray(new Double[] a));

        firstSeries.getData().add(new XYChart.Data<>(x,firstValue));
        secondSeries.getData().add(new XYChart.Data<>(x,secondValue));
        x++;

        if(firstSeries.getData().size()>200){

            firstSeries.getData().remove(0,1);
            secondSeries.getData().remove(0,1);
            minX++;
            maxX++;
        }

        xAxis.setLowerBound(minX);
        xAxis.setUpperBound(maxX);
        xAxis.setTickUnit(25);
        yAxis.setUpperBound(yAxis.getUpperBound()*1.2);



    }

    public ScatterChart<Number,Number> getChart(){
        return chart;
    }
}
