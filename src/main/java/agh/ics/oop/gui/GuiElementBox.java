package agh.ics.oop.gui;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {


    private VBox verticalBox;
    public GuiElementBox(IMapElement mapElement,boolean isSawanna ){
        try {
            Image image = new Image(new FileInputStream(mapElement.getImgPath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(28);
            imageView.setFitHeight(28);



           this.verticalBox = new VBox(imageView);
            verticalBox.setAlignment(Pos.CENTER);
            BackgroundFill fill;
            if(isSawanna){
                fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
            }
            else{
                fill = new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
            }
            verticalBox.setBackground(new Background(fill));


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

    }

    public GuiElementBox(boolean isJungle){
        this.verticalBox = new VBox();
        BackgroundFill fill;
        if(isJungle){
            fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
        }
        else{
            fill = new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
        }

        verticalBox.setBackground(new Background(fill));
    }

    public VBox getVerticalBox() {
        return verticalBox;
    }


}
