package agh.ics.oop.gui;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

import java.util.function.UnaryOperator;

public class OptionsElementVBox extends VBox {
    Label name;
    TextField textField;

    public OptionsElementVBox(Label name, TextField textField) {
        super(name,textField);
        this.name = name;
        this.textField = textField;


        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9.]?")) { // this is the important line
                return change;
            }

            return null;
        };
        textField.setTextFormatter(new TextFormatter<String>(filter));
    }



}
