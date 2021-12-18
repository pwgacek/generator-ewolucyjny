package agh.ics.oop;
import java.util.*;

public class Animal implements IMapElement, Comparable<Animal> {

    private MapDirection direction ;
    private Vector2d position;
    private final IWorldMap map;
    private final ArrayList<IPositionChangeObserver> observerList;
    private int energy;
    private final int[] genotype;
    public ArrayList<String> reports;


    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
        observerList = new ArrayList<>();
        this.energy =40+ new Random().nextInt(10);
        this.genotype = generateGenotype();
        System.out.println(Arrays.toString(this.genotype));
        this.reports = new ArrayList<>();
        reports.add("--genotype: "+ Arrays.toString(this.genotype) + "position: " + position.toString() + "energy: " + this.energy );
    }
    public Animal(IWorldMap map, Vector2d initialPosition,Animal strongerParent,Animal weakerParent){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
        observerList = new ArrayList<>();
        System.out.println("Rozmnazanie: matka: " + strongerParent.getPosition() + " energia: "+ strongerParent.getEnergy() +" genotyp: " + Arrays.toString(strongerParent.genotype) + " ojciec: " + weakerParent.getPosition() + " energia: "+weakerParent.getEnergy()+" genotyp: " + Arrays.toString(weakerParent.genotype));

        this.energy = getChildsEnergy(strongerParent,weakerParent);
        strongerParent.changeEnergy(-(int)(strongerParent.getEnergy()/4));
        weakerParent.changeEnergy(-(int)(weakerParent.getEnergy()/4));
        this.genotype = getChildsGenotype(strongerParent,weakerParent);
        System.out.println("Po rozmnożeniu: genotyp: "+ Arrays.toString(this.genotype)+ " energia: "+ this.energy);
        System.out.println("energia rodzicow: "+ strongerParent.getEnergy() +", "+weakerParent.getEnergy());
        this.reports = new ArrayList<>();
        reports.add("--genotype: "+ Arrays.toString(this.genotype) + "position: " + position.toString() + "energy: " + this.energy );

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

    private int[] generateGenotype(){
        Random rd = new Random();
        int[] genotype = new int[32];
        for(int i=0;i<32;i++){
            genotype[i] = rd.nextInt(8);
        }
        Arrays.sort(genotype);
        return genotype;
    }

    public int getRandomGen(){
        int randomIndex = new Random().nextInt(32);
        return genotype[randomIndex];
    }


    @Override
    public String getImgPath() {
        if (this.energy > 40) return "src/main/resources/yellow.png";
        if (this.energy > 30) return "src/main/resources/light_orange.png";
        if (this.energy > 20) return "src/main/resources/orange.png";
        if (this.energy > 10) return "src/main/resources/red.png";
        return "src/main/resources/brown.png";


    }


    public void move(int rotation){
        int x = this.getPosition().x;
        int y = this.getPosition().y;
        MapDirection oldDirection = this.direction;

        switch (rotation) {
            case 0 -> {
                if (map.canMoveTo(this.position.add(this.direction.toUnitVector())) )
                    this.position = this.position.add(this.direction.toUnitVector());
            }
            case 4 -> {
                if (map.canMoveTo(this.position.subtract(this.direction.toUnitVector())))
                    this.position = this.position.subtract(this.direction.toUnitVector());
            }

            default ->this.direction = this.direction.rotate(rotation);
        }

        positionChanged(new Vector2d(x,y));
        reports.add("---rotation: " + rotation+" oldDirection: " + oldDirection + " newDirection:" +this.direction +" oldPosition: "+ new Vector2d(x,y) +" newPosition: " + position + " energy: " + this.energy );
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
    public String toString() {
        return switch(direction){
            case NORTH ->  "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH ->  "S";
            case SOUTHWEST -> "SW";
            case WEST ->  "W";
            case NORTHWEST ->  "NW";

        };

    }

    @Override
    public int compareTo(Animal other) {
        return other.energy - this.energy;
    }

    private int[] getChildsGenotype(Animal strongerParent,Animal weakerParent){

        int[] childsGenotype = new int[32];


        int energySum = weakerParent.getEnergy() + strongerParent.getEnergy();
        int gensTakenFromStronger = (int)((strongerParent.getEnergy()/energySum)*32);
        boolean takeLeftSideFromStronger = new Random().nextBoolean();

        if(takeLeftSideFromStronger){
            for(int i=0;i<32;i++){
                if(i<gensTakenFromStronger){
                    childsGenotype[i] = strongerParent.genotype[i];
                }
                else{
                    childsGenotype[i] = weakerParent.genotype[i];
                }

            }
        }
        else{
            for(int i=0;i<32;i++){
                if(i<32-gensTakenFromStronger){
                    childsGenotype[i] = weakerParent.genotype[i];
                }
                else{
                    childsGenotype[i] = strongerParent.genotype[i];
                }

            }
        }
        Arrays.sort(childsGenotype);
        return childsGenotype;
    }

    private int getChildsEnergy(Animal mother,Animal father){
        return (int)((mother.getEnergy() + father.getEnergy())/4);
    }



}
