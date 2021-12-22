package agh.ics.oop.gui;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageViewSelector {
    private final Map<String, ImageView> imageViewMap;
    private final String[] paths;

    public ImageViewSelector(){
        imageViewMap = new HashMap<>();
        paths = new String[]{ "src/main/resources/yellow.png", "src/main/resources/light_orange.png","src/main/resources/orange.png", "src/main/resources/red.png", "src/main/resources/brown.png","src/main/resources/green.png"};

        for (String path : paths) {
            try {
                Image image = new Image(new FileInputStream(path));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(28);
                imageView.setFitHeight(28);
                imageViewMap.put(path, imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public ImageView getImageView(String path){
        return imageViewMap.get(path);
    }

}
