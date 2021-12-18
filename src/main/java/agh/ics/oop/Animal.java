package agh.ics.oop;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Animal implements IMapElement {

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
        this.energy = 50;
        this.genotype = generateGenotype();
        System.out.println(Arrays.toString(this.genotype));
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
            observer.positionChanged(oldPosition,this.position);
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
}
