package agh.ics.oop;

import java.util.ArrayList;

public class Animal {

    private MapDirection direction ;
    private Vector2d position;
    private IWorldMap map;
    private final ArrayList<IPositionChangeObserver> observerList;

    public Animal() {
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2,2);
        observerList = new ArrayList<>();
    }


    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
        observerList = new ArrayList<>();
    }

    public MapDirection getDirection() { return direction; }
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        switch(direction){
            case EAST -> { return "E";}
            case NORTH -> {return "N";}
            case SOUTH -> {return "S";}
            default ->  {return "W";}

        }

    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move(MoveDirection direction){
        int x = this.getPosition().x;
        int y = this.getPosition().y;

        switch (direction) {
            case LEFT -> this.direction = this.direction.previous();
            case RIGHT -> this.direction = this.direction.next();
            case FORWARD -> {
                if (map.canMoveTo(this.position.add(this.direction.toUnitVector())) )
                    this.position = this.position.add(this.direction.toUnitVector());
            }
            case BACKWARD -> {
                if (map.canMoveTo(this.position.subtract(this.direction.toUnitVector())))
                    this.position = this.position.subtract(this.direction.toUnitVector());

            }
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
            observer.positionChanged(oldPosition,this.position);
        }

    }





}
