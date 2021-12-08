package agh.ics.oop;

import java.util.ArrayList;

public class OptionParser {
    public ArrayList<MoveDirection> parse(String[] args){
        ArrayList<MoveDirection> directions = new ArrayList<>();
        for(String item : args){

                switch (item){
                    case "f", "forward" -> directions.add(MoveDirection.FORWARD);
                    case "b","backward" -> directions.add(MoveDirection.BACKWARD);
                    case "r","right" -> directions.add(MoveDirection.RIGHT);
                    case "l","left" -> directions.add(MoveDirection.LEFT);
                    default -> throw new IllegalArgumentException(item+" is not legal move specification");
                }


        }
        return directions;
    }

}
