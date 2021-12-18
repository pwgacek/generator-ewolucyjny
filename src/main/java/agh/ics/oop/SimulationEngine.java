package agh.ics.oop;

import agh.ics.oop.gui.MapVisualizer;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SimulationEngine  implements  Runnable{
    private ArrayList<MoveDirection> moves;
    private final AbstractWorldMap map;
    private final ArrayList<Vector2d> positions = new ArrayList<>();
    private final MapVisualizer observer;
    private final int moveDelay;
    private boolean transfer = true;



    public SimulationEngine(AbstractWorldMap map, ArrayList<Vector2d> initialPositions, MapVisualizer mapVisualizer, int moveDelay) {

        this.map = map;
        this.moveDelay = moveDelay;
        this.observer = mapVisualizer;

        for(Vector2d position : initialPositions){
            if(map.place(new Animal(this.map,position))) positions.add(position) ;
        }

    }



    @Override
    public void run() {
        waitForRunLater();
        Platform.runLater(observer::positionChanged);
        System.out.println(map);

        while(!map.animals.isEmpty()){
            send();
            receive();
        }

    }



    public synchronized void send() {
        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
            }
        }
        transfer = false;
        System.out.println("---"+map.animals.toString());
        ArrayList<Animal> animalsToRemove = new ArrayList<>();

        for(Animal animal : map.animals){

            if(animal.getEnergy() > 0) {
                animal.move(animal.getRandomGen());
                animal.loseEnergy(5);

            }
            else{
                animalsToRemove.add(animal);
            }

        }


        for(Animal animal : animalsToRemove){
            System.out.println(animal.reports.toString());
            map.removeAnimal(animal);
        }


        notifyAll();
    }

    public synchronized void receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
            }
        }
        transfer = true;
        try {
            Thread.sleep(moveDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        Platform.runLater(observer::positionChanged);
        waitForRunLater();


        System.out.println(map);
        notifyAll();

    }
    public static void waitForRunLater()  {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(() -> semaphore.release());
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
