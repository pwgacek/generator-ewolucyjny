package agh.ics.oop;

import java.util.ArrayList;

public abstract class AbstractWorldMap implements IWorldMap {
    protected  int height;
    protected  int width;
    protected ArrayList<Animal> animals;


    @Override
    public boolean place(Animal animal) {
        if(!isOccupied(animal.getPosition())){
            animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {

        for( Animal animal:animals){
            if(animal.getPosition().equals(position)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return  (new MapVisualizer(this).draw(new Vector2d(0,0),new Vector2d(width,height)));
    }


}
