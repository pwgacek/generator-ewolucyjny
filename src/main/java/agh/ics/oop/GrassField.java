package agh.ics.oop;

import java.util.ArrayList;
import java.util.Random;

public class GrassField extends AbstractWorldMap {

    private final int grassQuantity;
    private final ArrayList<Grass> grassList;

    public GrassField(int grassQuantity) {
        this.grassQuantity = grassQuantity;
        animals = new ArrayList<>();
        grassList = new ArrayList<>();

        for(int i =0;i<grassQuantity;i++){
            Vector2d position = generateGrassCords();
            while(isGrassAt(position)){
                position = generateGrassCords();
            }
            grassList.add(new Grass(position));
        }

    }

    private Vector2d generateGrassCords(){
        Random random = new Random();
        int x = random.nextInt((int)Math.sqrt(grassQuantity*10));
        int y = random.nextInt((int)Math.sqrt(grassQuantity*10));
        return new Vector2d(x,y);
    }
    private boolean isGrassAt(Vector2d position){
        for(Grass grass:grassList){
            if(grass.getPosition().equals(position)) return true;
        }
        return false;
    }
    public boolean canMoveTo(Vector2d position) {
        if (position.x >=0 &&  position.y >=0)
        {
           return !isOccupied(position);
        }
        return false;
    }


    @Override
    public Object objectAt(Vector2d position) {
        for(Animal animal:animals){
            if(animal.isAt(position)) return animal;
        }
        for(Grass grass:grassList){
            if(grass.getPosition().equals(position)) return grass;
        }
        return null;
    }
    private void getUpperRightBound(){
        int x=0;
        int y=0;
        for(Animal animal :animals){
            if(x<animal.getPosition().x)x = animal.getPosition().x;
            if(y<animal.getPosition().y)y = animal.getPosition().y;

        }
        for(Grass grass :grassList){
            if(x<grass.getPosition().x)x = grass.getPosition().x;
            if(y<grass.getPosition().y)y = grass.getPosition().y;

        }

        height = y;
        width = x;
    }

    @Override
    public String toString() {
       getUpperRightBound();
        return super.toString();
    }
}
