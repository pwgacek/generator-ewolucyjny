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


public class GuiElementBox extends VBox {

    ImageViewSelector imageViewSelector;

    private IMapElement element;


    public GuiElementBox(boolean isSawanna, Label chosenGenotype){
        imageViewSelector = new ImageViewSelector();
        //setImageView(mapElement);

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
            if(this.element!=null){
                if(this.element.getClass() == Animal.class){
                    Animal animal = (Animal) element;
                    chosenGenotype.setText(Arrays.toString(animal.getGenotype()));

                }
            }
        });
    }


    public void setImageView(IMapElement element){
        removeImageView();
        this.element = element;
        ImageView imageView = imageViewSelector.getImageView(element.getImgPath());
        this.getChildren().add(imageView);

    }

    public void removeImageView(){
        this.element = null;
        this.getChildren().clear();
    }




}
