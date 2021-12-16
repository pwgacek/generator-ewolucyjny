package agh.ics.oop.gui;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {


    private VBox verticalBox;
    public GuiElementBox(IMapElement mapElement ){
        try {
            Image image = new Image(new FileInputStream(mapElement.getImgPath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);

            Label label = new Label(mapElement.getPosition().toString());
            label.setFont(new Font(9));
           this.verticalBox = new VBox(imageView,label);
            verticalBox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

    }

    public VBox getVerticalBox() {
        return verticalBox;
    }


}
