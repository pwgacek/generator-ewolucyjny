package agh.ics.oop.gui;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

public class OptionsElementVBox extends VBox {
    Label name;
    TextField textField;
    ChoiceBox<String> choiceBox;

    public OptionsElementVBox(Label name, TextField textField,boolean isInt) {
        super(name,textField);
        this.name = name;
        this.textField = textField;


        UnaryOperator<TextFormatter.Change> intFilter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]?")) {
                return change;
            }

            return null;
        };

        UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String text = change.getText();

            if (text.matches("[0-9.]?")) {

                return change;
            }


            return null;
        };




        if(isInt) textField.setTextFormatter(new TextFormatter<String>(intFilter));
        else textField.setTextFormatter(new TextFormatter<String>(doubleFilter));
    }

    public OptionsElementVBox(Label name, ChoiceBox<String> choiceBox){
        super(name,choiceBox);
        this.name = name;
        this.choiceBox = choiceBox;

        this.choiceBox.setItems(FXCollections.observableArrayList("normal evolution","magical evolution"));
        this.choiceBox.setPrefWidth(140);
        this.choiceBox.setValue("normal evolution");
    }




}
