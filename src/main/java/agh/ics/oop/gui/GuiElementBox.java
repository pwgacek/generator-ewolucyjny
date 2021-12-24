package agh.ics.oop.gui;
import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Arrays;


public class GuiElementBox {

    ImageViewSelector imageViewSelector;
    private final VBox verticalBox;
    private IMapElement element;

    public GuiElementBox(boolean isSawanna ){
        imageViewSelector = new ImageViewSelector();
        //setImageView(mapElement);
        this.verticalBox = new VBox();
        verticalBox.setAlignment(Pos.CENTER);
        BackgroundFill fill;
        if(isSawanna){
            fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
        }
        else{
            fill = new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
        }
        verticalBox.setBackground(new Background(fill));

        verticalBox.setOnMouseClicked((click)->{
            if(this.element!=null){
                if(this.element.getClass() == Animal.class){
                    Animal animal = (Animal) element;
                    System.out.println("my id: " + animal.myID+" my genome: "+ Arrays.toString(animal.getGenotype()));
                }
            }
        });
    }


    public void setImageView(IMapElement element){
        removeImageView();
        this.element = element;
        ImageView imageView = imageViewSelector.getImageView(element.getImgPath());
        verticalBox.getChildren().add(imageView);

    }

    public void removeImageView(){
        this.element = null;
        verticalBox.getChildren().clear();
    }


    public VBox getVerticalBox() {
        return verticalBox;
    }


}
