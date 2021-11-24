package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap extends AbstractWorldMap{

    public RectangularMap(int width,int height){
        this.width = width;
        this.height = height;
        this.animals = new ArrayList<>();

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.x <= width && position.x >=0 && position.y <= height && position.y >=0)
        {
            return !isOccupied(position);
        }
        return false;
    }

    @Override
    public Animal objectAt(Vector2d position) {
        for( Animal animal:animals){
            if(animal.isAt(position)) return animal;
        }
        return null;
    }



}
