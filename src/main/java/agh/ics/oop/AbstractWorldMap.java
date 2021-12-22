package agh.ics.oop;
import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{
    protected  int height;
    protected  int width;
    public ArrayList<Animal> animals;
    public Map<Vector2d, ArrayList<Animal>> animalsMap;
    public Vector2d jungleCord1 = new Vector2d(7,7);
    public Vector2d jungleCord2 = new Vector2d(13,13);


    protected Map<Vector2d,Grass> grassAtSawanna;
    protected Map<Vector2d,Grass> grassAtJungle;
    public Map<Vector2d,Grass> emptyAtSawanna;
    public Map<Vector2d,Grass> emptyAtJungle;

    public AbstractWorldMap(int width,int height) {

        animals = new ArrayList<>();
        this.animalsMap = new HashMap<>();
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




    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }




    public void addGrassToSawanna(){
        Vector2d chosenPosition = (Vector2d) emptyAtSawanna.keySet().toArray()[new Random().nextInt(emptyAtSawanna.size())];
        Grass chosenGrass  = emptyAtSawanna.get(chosenPosition);

        emptyAtSawanna.remove(chosenPosition);
        grassAtSawanna.put(chosenPosition,chosenGrass);
    }

    public void addGrassToJungle(){
        Vector2d chosenVector = (Vector2d) emptyAtJungle.keySet().toArray()[new Random().nextInt(emptyAtJungle.size())];
        Grass chosenGrass  = emptyAtJungle.get(chosenVector);

        emptyAtJungle.remove(chosenVector);
        grassAtJungle.put(chosenVector,chosenGrass);
    }

    public void removeGrassFromSawanna(Vector2d position){
        Grass grass = grassAtSawanna.get(position);
        emptyAtSawanna.put(position,grass);
        grassAtSawanna.remove(position);
    }

    public void removeGrassFromJungle(Vector2d position){
        Grass grass = grassAtJungle.get(position);
        emptyAtJungle.put(position,grass);
        grassAtJungle.remove(position);
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
        if(grassAtSawanna.containsKey(position)) return grassAtSawanna.get(position);
        if(grassAtJungle.containsKey(position)) return grassAtJungle.get(position);
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



}
