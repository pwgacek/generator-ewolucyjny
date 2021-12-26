package agh.ics.oop;
import java.util.*;

public class Animal implements IMapElement, Comparable<Animal> {

    private MapDirection direction ;
    private Vector2d position;
    private final AbstractWorldMap map;
    private final ArrayList<IPositionChangeObserver> observerList;
    private int energy;
    private final ArrayList<Integer> genotype;
    public ArrayList<String> reports;
    private static int id = 0;
    public final int myID;
    private final int startEnergy;
    private final int dateOfBirth;
    private int childrenCounter;


    public Animal(AbstractWorldMap map, Vector2d initialPosition, int startEnergy){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH.generateMapDirection();
        observerList = new ArrayList<>();
        this.energy =startEnergy; //40+ new Random().nextInt(10);
        this.genotype = generateGenotype();
        System.out.println(this.genotype.toString());
        this.reports = new ArrayList<>();
        this.myID = setID();
        this.dateOfBirth =0;
        this.startEnergy = startEnergy;
        this.childrenCounter = 0;
        reports.add("ID: "+ this.myID +"--genotype: "+ this.genotype.toString() + "position: " + position.toString() + "energy: " + this.energy );

    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition,Animal strongerParent,Animal weakerParent,int dateOfBirth,int startEnergy){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH.generateMapDirection();
        observerList = new ArrayList<>();
        this.energy = getChildsEnergy(strongerParent,weakerParent);
        strongerParent.changeEnergy(-(int)(strongerParent.getEnergy()/4));
        weakerParent.changeEnergy(-(int)(weakerParent.getEnergy()/4));
        this.genotype = getChildsGenotype(strongerParent,weakerParent);
        this.dateOfBirth = dateOfBirth;
        this.startEnergy = startEnergy;

        this.reports = new ArrayList<>();
        this.myID = setID();
        this.childrenCounter = 0;
        reports.add("ID: " + this.myID + "--genotype: "+ this.genotype.toString() + "position: " + position.toString() + "energy: " + this.energy );


    }
    static int setID(){
        return id++;

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
        if (this.energy > this.startEnergy*0.8) return "src/main/resources/yellow.png";
        if (this.energy > this.startEnergy*0.6) return "src/main/resources/light_orange.png";
        if (this.energy > this.startEnergy*0.4) return "src/main/resources/orange.png";
        if (this.energy > this.startEnergy*0.2) return "src/main/resources/red.png";
        return "src/main/resources/brown.png";


    }


    public void move(int rotation){
        int x = this.getPosition().x;
        int y = this.getPosition().y;
        MapDirection oldDirection = this.direction;

        switch (rotation) {
            case 0 -> {
                if (map.canMoveTo(this.position.add(this.direction.toUnitVector())) )
                {
                    this.position = this.position.add(this.direction.toUnitVector());
                    maintainOnMap();
                }


            }
            case 4 -> {
                if (map.canMoveTo(this.position.subtract(this.direction.toUnitVector()))) {
                    this.position = this.position.subtract(this.direction.toUnitVector());
                    maintainOnMap();
                }
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
            case NORTH ->  "N ";
            case NORTHEAST -> "NE";
            case EAST -> "E ";
            case SOUTHEAST -> "SE";
            case SOUTH ->  "S ";
            case SOUTHWEST -> "SW";
            case WEST ->  "W ";
            case NORTHWEST ->  "NW";

        };

    }

    @Override
    public int compareTo(Animal other) {
        return other.energy - this.energy;
    }

    private ArrayList<Integer> getChildsGenotype(Animal strongerParent,Animal weakerParent){

        //int[] childsGenotype = new int[32];
        ArrayList<Integer> childsGenotype = new ArrayList<>();

        int energySum = weakerParent.getEnergy() + strongerParent.getEnergy();
        int gensTakenFromStronger = (32*strongerParent.getEnergy()/energySum);
        System.out.println("biore od silniejszego: "+ gensTakenFromStronger + " genow");
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
