package agh.ics.oop.gui;
import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;


public class GuiElementBox extends Label {

    ImageViewSelector imageViewSelector;
    private IMapElement element;
    private final double cellSize;


    public GuiElementBox(double cellSize, boolean isSawanna, AtomicBoolean isRunning){
        imageViewSelector = new ImageViewSelector();

        this.cellSize = cellSize;
        this.setPrefSize(cellSize*0.9,cellSize*0.9);
        this.setAlignment(Pos.CENTER);
        BackgroundFill fill;
        if(isSawanna){
            fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
        }
        else{
            fill = new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
        }
        this.setBackground(new Background(fill));

        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(new Duration(0));


        this.setOnMouseEntered((event)->{
            if(!isRunning.get()){
                if(element!=null){
                    if(this.element.getClass() == Animal.class){

                        tooltip.setText("genotype: "+((Animal) element).getGenotype().toString());
                        this.setTooltip(tooltip);

                    }
                }
            }
        });

        this.setOnMouseExited((event -> {
            if(this.getTooltip()!=null){
                this.setTooltip(null);

            }
        }));



    }


    public void setImageView(IMapElement element){
        removeImageView();
        this.element = element;
        ImageView imageView = imageViewSelector.getImageView(element.getImgPath());
        imageView.setFitWidth(0.9*cellSize);
        imageView.setFitHeight(0.9*cellSize);

        this.getChildren().add(imageView);

    }

    public void removeImageView(){
        this.element = null;
        this.getChildren().clear();
    }




}
