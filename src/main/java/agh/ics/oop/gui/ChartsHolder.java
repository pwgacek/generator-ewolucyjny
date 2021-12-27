package agh.ics.oop.gui;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ChartsHolder extends GridPane {
    public ChartsHolder(DoubleStatsChart firstChart, StatsChart secondChart,StatsChart thirdChart,StatsChart fourthChart){
        setWidth(600);
        setHeight(300);

        GridPane.setConstraints(firstChart.getChart(),0,0);
        GridPane.setConstraints(secondChart.getChart(),1,0);
        GridPane.setConstraints(thirdChart.getChart(),0,1);
        GridPane.setConstraints(fourthChart.getChart(),1,1);

        this.getChildren().addAll(firstChart.getChart(), secondChart.getChart(),thirdChart.getChart(), fourthChart.getChart());
        this.getColumnConstraints().addAll(new ColumnConstraints(300),new ColumnConstraints(300));
        this.getRowConstraints().addAll(new RowConstraints(150),new RowConstraints(150));

    }

}
