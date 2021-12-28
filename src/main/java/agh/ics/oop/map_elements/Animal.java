package agh.ics.oop.map_elements;
import agh.ics.oop.maps.AbstractWorldMap;

import java.util.*;

public class Animal implements IMapElement, Comparable<Animal> {

    private MapDirection direction ;
    private Vector2d position;
    private final AbstractWorldMap map;
    private final ArrayList<IPositionChangeObserver> observerList;
    private int energy;
    private final ArrayList<Integer> genotype;
    private final int startEnergy;
    private final int dateOfBirth;
    private int childrenCounter;


    public Animal(AbstractWorldMap map, Vector2d initialPosition, int startEnergy){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH.generateMapDirection();
        observerList = new ArrayList<>();
        energy =startEnergy;
        genotype = generateGenotype();
        dateOfBirth =0;
        this.startEnergy = startEnergy;
        childrenCounter = 0;
    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition, int startEnergy,Animal clonedAnimal,int dateOfBirth){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH.generateMapDirection();
        observerList = new ArrayList<>();
        energy =startEnergy;
        genotype = clonedAnimal.getGenotype();
        this.dateOfBirth =dateOfBirth;
        this.startEnergy = startEnergy;
        childrenCounter = 0;

    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition,Animal strongerParent,Animal weakerParent,int dateOfBirth,int startEnergy){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH.generateMapDirection();
        observerList = new ArrayList<>();
        energy = getChildsEnergy(strongerParent,weakerParent);
        strongerParent.changeEnergy(-(int)(strongerParent.getEnergy()/4));
        weakerParent.changeEnergy(-(int)(weakerParent.getEnergy()/4));
        genotype = getChildsGenotype(strongerParent,weakerParent);
        this.dateOfBirth = dateOfBirth;
        this.startEnergy = startEnergy;
        childrenCounter = 0;


    }




    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public int getChildrenCounter() {
        return childrenCounter;
    }

    public void incrementChildrenCounter() {
        this.childrenCounter+=1;
    }

    public MapDirection getDirection() { return direction; }
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public void changeEnergy(int energy) {
        this.energy +=energy;
    }

    private ArrayList<Integer> generateGenotype(){
        Random rd = new Random();
        ArrayList<Integer> genotype = new ArrayList<>();
        for(int i=0;i<32;i++){
            genotype.add(rd.nextInt(8));
        }
        Collections.sort(genotype);
        return genotype;
    }

    public int getRandomGen(){
        int randomIndex = new Random().nextInt(32);
        return genotype.get(randomIndex);
    }


    @Override
    public String getImgPath() {
        if (energy > startEnergy*0.8) return "src/main/resources/yellow.png";
        if (energy > startEnergy*0.6) return "src/main/resources/light_orange.png";
        if (energy > startEnergy*0.4) return "src/main/resources/orange.png";
        if (energy > startEnergy*0.2) return "src/main/resources/red.png";
        return "src/main/resources/brown.png";


    }


    public void move(int rotation){
        int x = this.getPosition().x;
        int y = this.getPosition().y;

        switch (rotation) {
            case 0 -> {
                if (map.canMoveTo(position.add(direction.toUnitVector())) )
                {
                    position = position.add(direction.toUnitVector());
                    maintainOnMap();
                }


            }
            case 4 -> {
                if (map.canMoveTo(position.subtract(direction.toUnitVector()))) {
                    position = position.subtract(direction.toUnitVector());
                    maintainOnMap();
                }
            }

            default ->direction = direction.rotate(rotation);
        }

        positionChanged(new Vector2d(x,y));
    }

    public void addObserver(IPositionChangeObserver observer){
        observerList.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observerList.remove(observer);
    }
    public void positionChanged(Vector2d oldPosition){
        for(IPositionChangeObserver observer:observerList){
            observer.positionChanged(oldPosition,this);
        }

    }



    @Override
    public int compareTo(Animal other) {
        return other.energy - this.energy;
    }

    private ArrayList<Integer> getChildsGenotype(Animal strongerParent,Animal weakerParent){


        ArrayList<Integer> childsGenotype = new ArrayList<>();

        int energySum = weakerParent.getEnergy() + strongerParent.getEnergy();
        int gensTakenFromStronger = (32*strongerParent.getEnergy()/energySum);

        boolean takeLeftSideFromStronger = new Random().nextBoolean();

        if(takeLeftSideFromStronger){
            for(int i=0;i<32;i++){
                if(i<gensTakenFromStronger){
                    childsGenotype.add(strongerParent.genotype.get(i));
                }
                else{
                    childsGenotype.add(weakerParent.genotype.get(i));
                }

            }
        }
        else{
            for(int i=0;i<32;i++){
                if(i<32-gensTakenFromStronger){
                    childsGenotype.add(weakerParent.genotype.get(i));
                }
                else{
                    childsGenotype.add(strongerParent.genotype.get(i));
                }

            }
        }
        Collections.sort(childsGenotype);
        return childsGenotype;
    }

    private int getChildsEnergy(Animal mother,Animal father){
        return ((mother.getEnergy() + father.getEnergy())/4);
    }

    private void maintainOnMap(){

        if(this.position.x == -1) this.position = new Vector2d(map.getWidth(),this.position.y);
        if(this.position.x == map.getWidth()+1) this.position = new Vector2d(0,this.position.y);
        if(this.position.y == -1) this.position = new Vector2d(this.position.x,map.getHeight());
        if(this.position.y == map.getHeight()+1) this.position = new Vector2d(this.position.x,0);
    }


    public ArrayList<Integer> getGenotype() {
        return genotype;
    }
}
