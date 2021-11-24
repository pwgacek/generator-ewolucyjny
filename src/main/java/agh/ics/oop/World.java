package agh.ics.oop;

import java.util.ArrayList;

public class World {
    public static void main(String[] args) {


        String[] moves = new String[]{"f", "b", "r" ,"l", "f","f" ,"r", "r" ,"f", "f" ,"f" ,"f" ,"f" ,"f" };
        ArrayList<MoveDirection> directions = new OptionParser().parse(moves);
        IWorldMap map = new GrassField(10);
        ArrayList<Vector2d> positions = new ArrayList<>();
        positions.add(new Vector2d(2,2));
        positions.add(new Vector2d(3,4));

        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }




}

