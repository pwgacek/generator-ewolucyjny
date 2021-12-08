package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final SortedSet<Vector2d> boundAnimalX;
    private final SortedSet<Vector2d> boundAnimalY;
    private final SortedSet<Vector2d> boundGrassX;
    private final SortedSet<Vector2d> boundGrassY;

    private Vector2DComparatorX cx = new Vector2DComparatorX();
    private Vector2DComparatorY cy = new Vector2DComparatorY();


    public MapBoundary(){
        this.boundAnimalX = new TreeSet<>(cx);
        this.boundAnimalY = new TreeSet<>(cy);
        this.boundGrassX = new TreeSet<>(cx);
        this.boundGrassY = new TreeSet<>(cy);
    }

    public void addtoboundAnimalX(Vector2d v){
        boundAnimalX.add(v);
    }

    public void addtoboundAnimalY(Vector2d v){
        boundAnimalY.add(v);
    }

    public void addtoboundGrassX(Vector2d v){
        boundGrassX.add(v);
    }

    public void addtoboundGrassY(Vector2d v){
        boundGrassY.add(v);
    }

    public int getboundAnimalX(){
        return boundAnimalX.last().x;
    }
    public int getboundAnimalY(){
        return boundAnimalY.last().y;
    }
    public int getboundGrassX(){
        return boundGrassX.last().x;
    }
    public int getboundGrassY(){
        return boundGrassY.last().y;
    }





    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if(!oldPosition.equals(newPosition)) {
            boundAnimalX.remove(oldPosition);
            boundAnimalX.add(newPosition);
            boundAnimalY.remove(oldPosition);
            boundAnimalY.add(newPosition);
        }
    }
}
