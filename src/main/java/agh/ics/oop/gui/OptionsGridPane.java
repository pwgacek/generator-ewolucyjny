package agh.ics.oop.gui;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class OptionsGridPane extends GridPane {

    private final OptionsElementVBox widthVBox;
    private final OptionsElementVBox heightVBox;
    private final OptionsElementVBox jungleRatioVBox;
    private final OptionsElementVBox startEnergyVBox;
    private final OptionsElementVBox moveEnergyVBox;
    private final OptionsElementVBox plantEnergyVBox;
    private final OptionsElementVBox animalQuantityVBox;

    public OptionsGridPane(String name){

        this.getColumnConstraints().add(new ColumnConstraints(200));
        Label nameLabel = new Label(name);
        nameLabel.setFont(new Font(24));
        this.getRowConstraints().add(new RowConstraints(70));
        nameLabel.setMaxWidth(140);
        GridPane.setHalignment(nameLabel, HPos.CENTER);
        GridPane.setConstraints(nameLabel,0,0);
        this.getChildren().add(nameLabel);


        widthVBox = new OptionsElementVBox(new Label("width:"),new TextField("10"),true);
        this.addElement(widthVBox,1);
        heightVBox = new OptionsElementVBox(new Label("height:"),new TextField("10"),true);
        this.addElement(heightVBox,2);
        jungleRatioVBox = new OptionsElementVBox(new Label("jungle ratio:"),new TextField("0.5"),false);
        this.addElement(jungleRatioVBox,3);
        startEnergyVBox = new OptionsElementVBox(new Label("start energy:"),new TextField("100"),true);
        this.addElement(startEnergyVBox,4);
        moveEnergyVBox = new OptionsElementVBox(new Label("move energy:"),new TextField("1"),true);
        this.addElement(moveEnergyVBox,5);
        plantEnergyVBox = new OptionsElementVBox(new Label("plant energy:"),new TextField("10"),true);
        this.addElement(plantEnergyVBox,6);
        animalQuantityVBox = new OptionsElementVBox(new Label("animal quantity:"),new TextField("20"),true);
        this.addElement(animalQuantityVBox,7);

    }

    private void addElement(OptionsElementVBox vBox,int rowIndex){
        this.getRowConstraints().add(new RowConstraints(70));
        vBox.setMaxWidth(140);
        GridPane.setHalignment(vBox, HPos.CENTER);
        GridPane.setConstraints(vBox,0,rowIndex);
        this.getChildren().add(vBox);
    }
    public int getWidthCondition() throws NumberFormatException{

        return Integer.parseInt(this.widthVBox.textField.getText()) - 1;
    }

    public int getHeightCondition() throws NumberFormatException{

        return Integer.parseInt(this.heightVBox.textField.getText()) - 1;
    }

    public double getJungleRatio()throws NumberFormatException{
        return Double.parseDouble(this.jungleRatioVBox.textField.getText());
    }

    public int getStartEnergyCondition()throws NumberFormatException{

        return Integer.parseInt(this.startEnergyVBox.textField.getText());
    }

    public int getMoveEnergyCondition()throws NumberFormatException{

        return Integer.parseInt(this.moveEnergyVBox.textField.getText());
    }

    public int getPlantEnergyCondition()throws NumberFormatException{

        return Integer.parseInt(this.plantEnergyVBox.textField.getText());
    }

    public int getAnimalQuantityCondition()throws NumberFormatException{

        return Integer.parseInt(this.animalQuantityVBox.textField.getText());
    }




}


