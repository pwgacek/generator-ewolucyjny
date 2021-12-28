package agh.ics.oop.gui.visualization;
import agh.ics.oop.map_elements.Animal;
import agh.ics.oop.map_elements.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;


public class GuiElementBox extends VBox {

    ImageViewSelector imageViewSelector;
    private IMapElement element;
    private final double cellSize;
    private boolean hasTooltip;


    public GuiElementBox(double cellSize, boolean isSavanna, AtomicBoolean isRunning){
        imageViewSelector = new ImageViewSelector();


        this.cellSize = cellSize;
        this.hasTooltip = false;

        this.setPrefSize(cellSize,cellSize);
        this.setAlignment(Pos.TOP_CENTER);
        BackgroundFill fill;
        if(isSavanna){
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
                        Tooltip.install(this,tooltip);
                        hasTooltip = true;

                    }
                }
            }
        });

        this.setOnMouseExited(event -> {
            if(hasTooltip){
                Tooltip.uninstall(this,tooltip);
                hasTooltip = false;
            }


        });



    }


    public void setImageView(IMapElement element){
        removeImageView();
        this.element = element;
        ImageView imageView = imageViewSelector.getImageView(element.getImgPath());
        imageView.setFitWidth(cellSize);
        imageView.setFitHeight(cellSize);


        this.getChildren().add(imageView);

    }

    public void removeImageView(){
        this.element = null;
        this.getChildren().clear();
    }




}
