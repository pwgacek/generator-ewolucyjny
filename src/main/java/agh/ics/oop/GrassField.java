package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap {



    public GrassField(int width,int height) {

        animals = new ArrayList<>();

        this.animalsMap = new HashMap<>();
        this.grassMap = new HashMap<>();
        this.height = height;
        this.width = width;
        this.grassAtJungle = new HashMap<>();
        this.grassAtSawanna = new HashMap<>();
        this.emptyAtJungle = new HashMap<>();
        this.emptyAtSawanna = new HashMap<>();

        for(int y=0;y<=this.height;y++){
            for(int x=0;x<=this.width;x++){
                Vector2d vector = new Vector2d(x,y);
                Grass grass = new Grass(vector);
                if(this.jungleCord1.precedes(vector) && this.jungleCord2.follows(vector)){
                    this.emptyAtJungle.put(vector,grass);
                }
                else{
                    this.emptyAtSawanna.put(vector,grass);
                }
            }
        }

        //addGrass(grassQuantity);

    }





    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.x <= width && position.x >= 0 && position.y <= height && position.y >= 0;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object animal = super.objectAt(position);
        if(animal != null) return animal;
        if(grassAtSawanna.containsKey(position)) return grassAtSawanna.get(position);
        if(grassAtJungle.containsKey(position)) return grassAtJungle.get(position);
        return null;
    }
}
