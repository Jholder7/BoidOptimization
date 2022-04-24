import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import IonEngine.Types.Particle;

// Refactored and ported to java by Justin Holder
//  Original curator: Daniel Shiffman (http://codingtra.in)

public class QuadTree extends Particle{

    private BoundingRectangle boundary;
    private int capacity;
    private LinkedList<Point> points;
    private boolean divided;
    private QuadTree northeast;
    private QuadTree northwest;
    private QuadTree southeast;
    private QuadTree southwest;

    public QuadTree(BoundingRectangle boundary, int capacity){
        super();
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new LinkedList<Point>();
        this.divided = false;
    }

    public void subdivide(){
        float x = this.boundary.x;
        float y = this.boundary.y;
        float w = this.boundary.w / 2;
        float h = this.boundary.h / 2;

        BoundingRectangle ne = new BoundingRectangle(x + w, y - h, w, h);
        this.northeast = new QuadTree(ne, this.capacity);

        BoundingRectangle nw = new BoundingRectangle(x - w, y - h, w, h);
        this.northwest = new QuadTree(nw, this.capacity);

        BoundingRectangle se = new BoundingRectangle(x + w, y + h, w, h);
        this.southeast = new QuadTree(se, this.capacity);

        BoundingRectangle sw = new BoundingRectangle(x - w, y + h, w, h);
        this.southwest = new QuadTree(sw, this.capacity);

        this.divided = true;
    }

    public boolean insert(Point point){
        if (!this.boundary.contains(point)){
            return false;
        }

        if (this.points.size() < this.capacity){
            this.points.push(point);
            return true;
        }

        if(!this.divided) {
            this.subdivide();
        }

        if (
            this.northeast.insert(point) ||
            this.northwest.insert(point) ||
            this.southeast.insert(point) ||
            this.southwest.insert(point)
        ) {
            return true;
        }

        System.out.println("Error Unreachable Code: QuadTree::Insert()");
        return false;
    }

    public LinkedList<Point> query(BoundingRectangle range, LinkedList<Point> found){
        if (!range.intersects(this.boundary)){
            //System.out.println("None");
            return found;
        }

        for (Point point: found){
            if (range.contains(point)){
                found.push(point);
            }
        }

        if (this.divided){
            this.northwest.query(range, found);
            this.northeast.query(range, found);
            this.southwest.query(range, found);
            this.southeast.query(range, found);
        }

        return found;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.drawRect((int) (((this.boundary.x/2) - this.boundary.w/2) + 450), (int) (((this.boundary.y/2) - this.boundary.h/2) + 450), (int) this.boundary.w, (int) this.boundary.h);
    }
}

// private Boid[] baseBoidArray;

    // public QuadTree(Boid[] boids){
    //     this.baseBoidArray = boids;
    // }

    // public Boid[] getLocalBoids(float posX, float posY, float radius){
    //     Boid[] boids = {this.baseBoidArray[0]};
    //     return boids;
    // }
