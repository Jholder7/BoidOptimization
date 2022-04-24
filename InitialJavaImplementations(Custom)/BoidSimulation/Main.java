import IonEngine.*;

public class Main{

    private int BOID_COUNT = 100;
    public Boid[] boids = new Boid[BOID_COUNT];

    public static void main(String[] args){
        System.setProperty( "sun.java2d.uiScale", "1.0" ); //Fix windows UI Scaling issues
        new Main();
    }

    Main(){
        Window window = new Window(900, 900, "PreOptimized Boid Simulation");
        for(int x = 0; x < BOID_COUNT; x++){
            boids[x] = new Boid(this);
        }

        while(true){
            window.internalUpdate();
        }
    }
}