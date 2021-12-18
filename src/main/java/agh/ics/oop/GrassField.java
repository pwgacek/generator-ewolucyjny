package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap {

    private final int grassQuantity;
    private final ArrayList<Grass> grassList;
    private final Map<Vector2d,Grass> grassMap;

    public GrassField(int grassQuantity,int width,int height) {
        this.grassQuantity = grassQuantity;
        animals = new ArrayList<>();
        grassList = new ArrayList<>();
        this.animalsMap = new HashMap<>();
        this.grassMap = new HashMap<>();
        this.height = height;
        this.width = width;

        for(int i =0;i<grassQuantity;i++){
            Vector2d position = generateGrassCords();
            while(isGrassAt(position)){
                position = generateGrassCords();
            }


            grassList.add(new Grass(position));
            grassMap.put(position,new Grass(position));
        }

    }

    private Vector2d generateGrassCords(){
        Random random = new Random();
        int x = random.nextInt(width+1);
        int y = random.nextInt(height+1);
        return new Vector2d(x,y);
    }
    private boolean isGrassAt(Vector2d position){
        for(Grass grass:grassList){
            if(grass.getPosition().equals(position)) return true;
        }
        return false;
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
    public Object objectAt(Vector2d position) {
        Object animal = super.objectAt(position);
        if(animal != null) return animal;
        if(grassMap.containsKey(position)) return grassMap.get(position);
        return null;
    }
}
