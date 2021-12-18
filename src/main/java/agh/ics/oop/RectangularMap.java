package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class RectangularMap extends AbstractWorldMap{

    public RectangularMap(int width,int height){
        this.width = width;
        this.height = height;
        this.animals = new ArrayList<>();
        this.animalsMap = new HashMap<>();

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.x <= width && position.x >= 0 && position.y <= height && position.y >= 0;
    }





}
