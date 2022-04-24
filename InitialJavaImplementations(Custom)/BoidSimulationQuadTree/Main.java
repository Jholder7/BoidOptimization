import java.util.Random;

import IonEngine.*;

public class Main{

    private int BOID_COUNT = 500;
    public QuadTree boidQuadTree;

    public static void main(String[] args){
        System.setProperty( "sun.java2d.uiScale", "1.0" ); //Fix windows UI Scaling issues
        new Main();
    }

    Main(){
        Window window = new Window(900, 900, "PostOptimized Boid Simulation");

        boidQuadTree = new QuadTree(new BoundingRectangle(0, 0, 900, 900), 4);

        Random random = new Random();

        for(int x = 0; x < BOID_COUNT; x++){
            Point newpoint = new Point(random.nextInt(900), random.nextInt(900));
            boidQuadTree.insert(newpoint);
        }

        while(true){
            window.internalUpdate();
        }
    }
}