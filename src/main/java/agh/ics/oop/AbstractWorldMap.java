package agh.ics.oop;

import java.util.ArrayList;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{
    protected  int height;
    protected  int width;
    public ArrayList<Animal> animals;
    public Map<Vector2d,Animal> animalsMap;
    public MapBoundary mapBoundary = new MapBoundary();

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public boolean place(Animal animal) {
        if(!isOccupied(animal.getPosition())){
            animals.add(animal);
            animalsMap.put(animal.getPosition(),animal);
            animal.addObserver(this);
            mapBoundary.addtoboundAnimalX(animal.getPosition());
            mapBoundary.addtoboundAnimalY(animal.getPosition());
            animal.addObserver(mapBoundary);
            return true;
        }
        else{
            throw new IllegalArgumentException("position: "+animal.getPosition() + " is occupied, can not place animal");
        }

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animalsMap.containsKey(position);
    }
    @Override
    public Object objectAt(Vector2d position) {
        if(animalsMap.containsKey(position)) return animalsMap.get(position);
        return null;
    }

    @Override
    public String toString() {
        return  (new MapVisualizer(this).draw(new Vector2d(0,0),new Vector2d(width,height)));
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){

        if(!oldPosition.equals(newPosition)) {
            animalsMap.put(newPosition, animalsMap.get(oldPosition));
            animalsMap.remove(oldPosition, animalsMap.get(oldPosition));
        }
    }

}
