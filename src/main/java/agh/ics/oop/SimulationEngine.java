package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine{
    private final ArrayList<MoveDirection> moves;
    private final IWorldMap map;
    private final ArrayList<Vector2d> positions = new ArrayList<>();

    public SimulationEngine(ArrayList<MoveDirection> moves, IWorldMap map,ArrayList<Vector2d> initialPositions){
        this.moves = moves;
        this.map = map;


        for(Vector2d position : initialPositions){
            if(map.place(new Animal(this.map,position))) positions.add(position) ;
        }

    }

    @Override
    public void run() {
        System.out.println(map);
        int positionIterator = 0;
        for(MoveDirection move:moves){
            System.out.println();
            if(positionIterator == positions.size()) positionIterator = 0;
            Animal a = (Animal) map.objectAt(positions.get(positionIterator));
            a.move(move);
            positions.set(positionIterator++,a.getPosition());
            System.out.println(map);
        }

    }

}
