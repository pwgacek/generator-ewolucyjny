package agh.ics.oop.maps;
import agh.ics.oop.conditions.MapConditions;
import agh.ics.oop.map_elements.Animal;
import agh.ics.oop.map_elements.Grass;
import agh.ics.oop.map_elements.IPositionChangeObserver;
import agh.ics.oop.map_elements.Vector2d;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    private final int myID;
    private static int idCounter = 1;
    protected  int height;
    protected  int width;
    private final ArrayList<Animal> animals;
    private final Map<Vector2d, ArrayList<Animal>> animalsHashMap;
    private Vector2d jungleBottomLeftCords;
    private Vector2d jungleUpperRightCords;


    private final Map<Vector2d, Grass> grassInSavanna;
    private final Map<Vector2d,Grass> grassInJungle;
    private final Map<Vector2d,Grass> emptyPositionsInSavanna;
    private final Map<Vector2d,Grass> emptyPositionsInJungle;

    public AbstractWorldMap(MapConditions mapConditions) {
        myID = idCounter++;
        animals = new ArrayList<>();
        this.animalsHashMap = new HashMap<>();

        this.height = mapConditions.getHeight();
        this.width = mapConditions.getWidth();

        this.grassInJungle = new HashMap<>();
        this.grassInSavanna = new HashMap<>();
        this.emptyPositionsInJungle = new HashMap<>();
        this.emptyPositionsInSavanna = new HashMap<>();

        generateJungleCords(mapConditions.getJungleRatio());

        for(int y=0;y<=this.height;y++){
            for(int x=0;x<=this.width;x++){
                Vector2d vector = new Vector2d(x,y);
                Grass grass = new Grass(vector);
                if(jungleBottomLeftCords.precedes(vector) && jungleUpperRightCords.follows(vector)){
                    emptyPositionsInJungle.put(vector,grass);
                }
                else{
                    emptyPositionsInSavanna.put(vector,grass);
                }
            }
        }

        //addGrass(grassQuantity);

    }

    public int getMyID() {
        return myID;
    }



    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }




    public void addGrassToSavanna(){
        ArrayList <Vector2d>  availablePositions = new ArrayList<>();
        for(Vector2d position : emptyPositionsInSavanna.keySet()){
            if(!animalsHashMap.containsKey(position)){
                availablePositions.add(position);
            }
        }
        if(availablePositions.size() > 0){
            Vector2d chosenPosition =  availablePositions.get(new Random().nextInt(availablePositions.size()));
            Grass chosenGrass  = emptyPositionsInSavanna.get(chosenPosition);

            emptyPositionsInSavanna.remove(chosenPosition);
            grassInSavanna.put(chosenPosition,chosenGrass);
        }

    }

    public void addGrassToJungle(){
        ArrayList <Vector2d>  availablePositions = new ArrayList<>();
        for(Vector2d position : emptyPositionsInJungle.keySet()){
            if(!animalsHashMap.containsKey(position)){
                availablePositions.add(position);
            }
        }
        if(availablePositions.size() > 0){
            Vector2d chosenPosition = availablePositions.get(new Random().nextInt(availablePositions.size()));
            Grass chosenGrass  = emptyPositionsInJungle.get(chosenPosition);

            emptyPositionsInJungle.remove(chosenPosition);
            grassInJungle.put(chosenPosition,chosenGrass);
        }
    }

    public void removeGrassFromSavanna(Vector2d position){
        Grass grass = grassInSavanna.get(position);
        emptyPositionsInSavanna.put(position,grass);
        grassInSavanna.remove(position);
    }

    public void removeGrassFromJungle(Vector2d position){
        Grass grass = grassInJungle.get(position);
        emptyPositionsInJungle.put(position,grass);
        grassInJungle.remove(position);
    }




    @Override
    public boolean place(Animal animal) {

        animals.add(animal);
        if(animalsHashMap.containsKey(animal.getPosition())){
            animalsHashMap.get(animal.getPosition()).add(animal);
            Collections.sort(animalsHashMap.get(animal.getPosition()));
        }
        else{
            animalsHashMap.put(animal.getPosition(),new ArrayList<>());
            animalsHashMap.get(animal.getPosition()).add(animal);
        }

        animal.addObserver(this);
        return true;


    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animalsHashMap.containsKey(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(animalsHashMap.containsKey(position)) return animalsHashMap.get(position).get(0);
        if(grassInSavanna.containsKey(position)) return grassInSavanna.get(position);
        if(grassInJungle.containsKey(position)) return grassInJungle.get(position);
        return null;
    }


    @Override
    public void positionChanged(Vector2d oldPosition,Animal animal){

        if(!oldPosition.equals(animal.getPosition())) {
            if(animalsHashMap.containsKey(animal.getPosition())){
                animalsHashMap.get(animal.getPosition()).add(animal);
                Collections.sort(animalsHashMap.get(animal.getPosition()));
            }
            else{
                animalsHashMap.put(animal.getPosition(),new ArrayList<>());
                animalsHashMap.get(animal.getPosition()).add(animal);
            }
            animalsHashMap.get(oldPosition).remove(animal);
            if(animalsHashMap.get(oldPosition).isEmpty()){
                animalsHashMap.remove(oldPosition);
            }


        }
    }

    public void removeAnimal(Animal animal){
        animals.remove(animal);
        animalsHashMap.get(animal.getPosition()).remove(animal);
        if(animalsHashMap.get(animal.getPosition()).isEmpty()){
            animalsHashMap.remove(animal.getPosition());
        }

    }

    public void generateJungleCords(double ratio){
        int bottomLeftX,bottomLeftY,upperRightX,upperRightY;
        int centerW = width/2;
        int centerH = height/2;
        int a = (int)((width+1)*Math.sqrt(ratio/2));
        int b = (int)((height+1)*Math.sqrt(ratio/2));
        if(width%2==0){
            if(a%2==0){a++;}
            bottomLeftX = centerW - a/2;
            upperRightX = centerW + a/2;
        }
        else{
            if(a%2==1){a++;}
            bottomLeftX = centerW - a/2 + 1;
            upperRightX = centerW + a/2;
        }

        if(height%2==0){
            if(b%2==0){b++;}
            bottomLeftY = centerH - b/2;
            upperRightY = centerH + b/2;

        }
        else{
            if(b%2==1){b++;}
            bottomLeftY = centerH - b/2 + 1;
            upperRightY = centerH + b/2;
        }
        this.jungleBottomLeftCords = new Vector2d(bottomLeftX,bottomLeftY);
        this.jungleUpperRightCords = new Vector2d(upperRightX,upperRightY);



    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public Map<Vector2d, ArrayList<Animal>> getAnimalsHashMap() {
        return animalsHashMap;
    }

    public Map<Vector2d, Grass> getGrassInSavanna() {
        return grassInSavanna;
    }

    public Map<Vector2d, Grass> getGrassInJungle() {
        return grassInJungle;
    }

    public Vector2d getJungleBottomLeftCords() {
        return jungleBottomLeftCords;
    }

    public Vector2d getJungleUpperRightCords() {
        return jungleUpperRightCords;
    }
}
