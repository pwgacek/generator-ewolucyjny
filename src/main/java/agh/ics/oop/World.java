package agh.ics.oop;

import java.util.ArrayList;

public class World {
    public static void main(String[] args) {
//        System.out.println("start");
//        String[] moves =  {"l","r","dfs","b","fd"};
//
//        run(convert(moves));
//        System.out.println("stop");
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }
    static ArrayList<Direction> convert(String[] args){
        ArrayList<Direction> directions = new ArrayList<>();
        for(String item : args){
            switch (item){
                case "f" -> directions.add(Direction.FORWARD);
                case "b" -> directions.add(Direction.BACKWARD);
                case "r" -> directions.add(Direction.RIGHT);
                case "l" -> directions.add(Direction.LEFT);
            }
        }
        return  directions;
    }

    static void run(ArrayList<Direction> args){

        System.out.println("zwierzak idzie!!!");
        StringBuilder finalString = new StringBuilder();
        for(Direction move : args){
            switch (move){
                case FORWARD -> {
                    finalString.append("zwierzak idzie do przodu,");
                }

                case BACKWARD -> {
                    finalString.append("zwierzak idzie do tyłu,");
                }
                case RIGHT -> {
                    finalString.append("zwierzak idzie w prawo,");
                }
                case LEFT -> {
                    finalString.append("zwierzak idzie w lewo,");

                }
            }
        }
        finalString.deleteCharAt(finalString.length()-1);
        System.out.println(finalString);

    }
}

