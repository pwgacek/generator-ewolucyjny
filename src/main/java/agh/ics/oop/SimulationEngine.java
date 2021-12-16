package agh.ics.oop;

import agh.ics.oop.gui.MapVisualizer;
import javafx.application.Platform;

import java.util.ArrayList;

public class SimulationEngine  implements  Runnable{
    private ArrayList<MoveDirection> moves;
    private final IWorldMap map;
    private final ArrayList<Vector2d> positions = new ArrayList<>();
    private final MapVisualizer observer;
    private final int moveDelay;



    public SimulationEngine(IWorldMap map, ArrayList<Vector2d> initialPositions, MapVisualizer mapVisualizer, int moveDelay) {

        this.map = map;
        this.moveDelay = moveDelay;
        this.observer = mapVisualizer;

        for(Vector2d position : initialPositions){
            if(map.place(new Animal(this.map,position))) positions.add(position) ;
        }

    }

    public void setDirections(String[] moves)throws IllegalArgumentException {
        this.moves = new OptionParser().parse(moves);
    }

    @Override
    public void run() {
        Platform.runLater(observer::positionChanged);
        System.out.println(map);
        int positionIterator = 0;



        for(MoveDirection move:moves){


            if(positionIterator == positions.size()) positionIterator = 0;
            Animal a = (Animal) map.objectAt(positions.get(positionIterator));
            Vector2d oldPosition = new Vector2d(a.getPosition().x, a.getPosition().y);

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            a.move(move);
            Platform.runLater(observer::positionChanged);




            positions.set(positionIterator++,a.getPosition());
            System.out.println(map);
        }

    }

}
