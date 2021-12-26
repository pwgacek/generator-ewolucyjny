package agh.ics.oop.gui;
import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;


public class GuiElementBox extends VBox {

    ImageViewSelector imageViewSelector;
    private IMapElement element;
    private double cellSize;


    public GuiElementBox(double cellSize, boolean isSawanna, Label chosenGenotype, AtomicBoolean isRunning){
        imageViewSelector = new ImageViewSelector();
        //setImageView(mapElement);
        this.cellSize = cellSize;
        this.setAlignment(Pos.CENTER);
        BackgroundFill fill;
        if(isSawanna){
            fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
        }
        else{
            fill = new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
        }
        this.setBackground(new Background(fill));



        this.setOnMouseClicked((click)->{
            if(!isRunning.get()){
                if(element!=null){
                    if(this.element.getClass() == Animal.class){
                        Animal animal = (Animal) element;
                        chosenGenotype.setText(animal.getGenotype().toString());

                    }
                }
            }

        });
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
