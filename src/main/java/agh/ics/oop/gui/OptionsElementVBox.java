package agh.ics.oop.gui;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

public class OptionsElementVBox extends VBox {
    Label name;
    TextField textField;

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



}
