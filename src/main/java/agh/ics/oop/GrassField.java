package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;


public class GrassField extends AbstractWorldMap {


    public GrassField(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.x <= width && position.x >= 0 && position.y <= height && position.y >= 0;
    }


}
