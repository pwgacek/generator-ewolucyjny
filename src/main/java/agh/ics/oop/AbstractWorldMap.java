package agh.ics.oop;
import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{
    protected  int height;
    protected  int width;
    public ArrayList<Animal> animals;
    public Map<Vector2d, ArrayList<Animal>> animalsMap;
    public Vector2d jungleBottomLeftCords;
    public Vector2d jungleUpperRightCords;


    protected Map<Vector2d,Grass> grassAtSawanna;
    protected Map<Vector2d,Grass> grassAtJungle;
    public Map<Vector2d,Grass> emptyAtSawanna;
    public Map<Vector2d,Grass> emptyAtJungle;

    public AbstractWorldMap(MapConditions mapConditions) {

        animals = new ArrayList<>();
        this.animalsMap = new HashMap<>();

        this.height = mapConditions.getHeight();
        this.width = mapConditions.getWidth();

        this.grassAtJungle = new HashMap<>();
        this.grassAtSawanna = new HashMap<>();
        this.emptyAtJungle = new HashMap<>();
        this.emptyAtSawanna = new HashMap<>();

        generateJungleCords(mapConditions.getJungleRatio());

        for(int y=0;y<=this.height;y++){
            for(int x=0;x<=this.width;x++){
                Vector2d vector = new Vector2d(x,y);
                Grass grass = new Grass(vector);
                if(this.jungleBottomLeftCords.precedes(vector) && this.jungleUpperRightCords.follows(vector)){
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
        Vector2d chosenPosition = (Vector2d) emptyAtJungle.keySet().toArray()[new Random().nextInt(emptyAtJungle.size())];
        Grass chosenGrass  = emptyAtJungle.get(chosenPosition);

        emptyAtJungle.remove(chosenPosition);
        grassAtJungle.put(chosenPosition,chosenGrass);
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




}
