package agh.ics.oop;

import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

public abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{
    protected  int height;
    protected  int width;
    public ArrayList<Animal> animals;
    public Map<Vector2d, Animal> animalsMap;
    protected  int grassQuantity;
    protected  ArrayList<Grass> grassList;
    protected  Map<Vector2d,Grass> grassMap;


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    protected Vector2d generateGrassCords(){
        Random random = new Random();
        int x = random.nextInt(width+1);
        int y = random.nextInt(height+1);
        return new Vector2d(x,y);
    }
    protected boolean isGrassAt(Vector2d position){
        for(Grass grass:grassList){
            if(grass.getPosition().equals(position)) return true;
        }
        return false;
    }
    public void addGrass(int grassQuantity) {
        for(int i = 0; i< grassQuantity; i++){
            Vector2d position = generateGrassCords();
            while(isGrassAt(position)){
                position = generateGrassCords();
            }


            grassList.add(new Grass(position));
            grassMap.put(position,new Grass(position));
        }
    }

    @Override
    public boolean place(Animal animal) {
        if(!isOccupied(animal.getPosition())){
            animals.add(animal);
            animalsMap.put(animal.getPosition(),animal);
            animal.addObserver(this);
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

    public void removeAnimal(Animal animal){
        animals.remove(animal);
        animalsMap.remove(animal.getPosition());
    }

    public void removeGrass(Grass grass){
        grassList.remove(grass);
        grassMap.remove(grass.getPosition());
    }


}
