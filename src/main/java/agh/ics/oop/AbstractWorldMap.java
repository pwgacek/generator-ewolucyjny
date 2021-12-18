package agh.ics.oop;

import javafx.collections.transformation.SortedList;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{
    protected  int height;
    protected  int width;
    public ArrayList<Animal> animals;
    public Map<Vector2d, ArrayList<Animal>> animalsMap;
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

        animals.add(animal);
        if(animalsMap.containsKey(animal.getPosition())){
            animalsMap.get(animal.getPosition()).add(animal);
            Collections.sort(animalsMap.get(animal.getPosition()));
        }
        else{
            animalsMap.put(animal.getPosition(),new ArrayList<>());
            animalsMap.get(animal.getPosition()).add(animal);
        }

        animal.addObserver(this);
        return true;


    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animalsMap.containsKey(position);
    }
    @Override
    public Object objectAt(Vector2d position) {
        if(animalsMap.containsKey(position)) return animalsMap.get(position).get(0);
        return null;
    }

    @Override
    public String toString() {
        return  (new MapVisualizer(this).draw(new Vector2d(0,0),new Vector2d(width,height)));
    }
    @Override
    public void positionChanged(Vector2d oldPosition,Animal animal){

        if(!oldPosition.equals(animal.getPosition())) {
            if(animalsMap.containsKey(animal.getPosition())){
                animalsMap.get(animal.getPosition()).add(animal);
                Collections.sort(animalsMap.get(animal.getPosition()));
            }
            else{
                animalsMap.put(animal.getPosition(),new ArrayList<>());
                animalsMap.get(animal.getPosition()).add(animal);
            }
            animalsMap.get(oldPosition).remove(animal);
            if(animalsMap.get(oldPosition).isEmpty()){
                animalsMap.remove(oldPosition);
            }


        }
    }

    public void removeAnimal(Animal animal){
        animals.remove(animal);
        animalsMap.get(animal.getPosition()).remove(animal);
        if(animalsMap.get(animal.getPosition()).isEmpty()){
            animalsMap.remove(animal.getPosition());
        }

    }

    public void removeGrass(Grass grass){
        grassList.remove(grass);
        grassMap.remove(grass.getPosition());
    }


}
