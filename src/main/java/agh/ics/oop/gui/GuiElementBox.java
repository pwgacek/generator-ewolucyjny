package agh.ics.oop.gui;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class GuiElementBox {

    ImageViewSelector imageViewSelector;
    private final VBox verticalBox;

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


    }


    public void setImageView(IMapElement element){
        if(verticalBox.getChildren().size() > 0){
            verticalBox.getChildren().clear();
        }

        ImageView imageView = imageViewSelector.getImageView(element.getImgPath());
        verticalBox.getChildren().add(imageView);




    }

    public VBox getVerticalBox() {
        return verticalBox;
    }


}
