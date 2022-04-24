import IonEngine.Types.Particle;
import IonEngine.Utils.Vector2D;
import java.util.Random;
import IonEngine.Window;

public class Boid extends Particle{

    private Main mainReference;
    private Vector2D acceleration;
    private float MAX_STEERING_FORCE = 0.2f;
    private float MAX_SPEED = 100;
    private float PERCEPTIONRADIUS = 50f;

    Boid(Main mainReference){
        super();
        this.mainReference = mainReference;
    }

    @Override
    public void onStart(){
        Random random = new Random();
        setPosition(new Vector2D(random.nextInt((int) windowSize.x), random.nextInt((int) windowSize.y)));
        velocity = new Vector2D(random.nextInt(400) - 200, random.nextInt(400) - 200);
        acceleration = new Vector2D(0, 0);
    }

    @Override
    public void onUpdate(float deltaTime){
        this.velocity.add(acceleration);
        float angle = 0;
        if (velocity.x > 0 && velocity.y > 0){
            angle = ((float) Math.atan(velocity.y / velocity.x) * (float) (180/Math.PI));
        } else if (velocity.x < 0 && velocity.y > 0){
            angle = ((float) Math.atan(velocity.y / velocity.x) * (float) (180/Math.PI)) - 180;
        } else if (velocity.x < 0 && velocity.y < 0){
            angle = ((float) Math.atan(velocity.y / velocity.x) * (float) (180/Math.PI)) - 180;
        } else if (velocity.x > 0 && velocity.y < 0){
            angle = ((float) Math.atan(velocity.y / velocity.x) * (float) (180/Math.PI));
        }
        this.setAngle(angle * -1);


        flock(this.mainReference.boids);
        edgeCheck();
    }

    //flock runs the three sub-behaviors to simulate flocking
    //TODO: Evaluate all of the Three behavior function when combined so essently just multiply them.
    public void flock(Boid[] boids){
        this.acceleration.mult(0); //set acceleration to zero so it doesn't accumulate
        Vector2D alignment = this.align(boids);
        Vector2D cohesion = this.cohesion(boids);
        Vector2D separation = this.separation(boids);

        alignment.mult(Window.alignSlider.getValue()/100.0f);
        cohesion.mult(Window.cohesionSlider.getValue()/100.0f);
        separation.mult(Window.separationSlider.getValue()/100.0f);

        acceleration.add(alignment);
        acceleration.add(cohesion);
        acceleration.add(separation);
    }

    //Move towards the average position of local flockmates
    //TODO: Evaluate this function
    private Vector2D separation(Boid[] boids){
        float perceptionRadius = this.PERCEPTIONRADIUS;
        int total = 0;

        Vector2D localAveragePosition = new Vector2D();
        for(Boid other: boids){
            float distance = Vector2D.dist(this.position, other.position);
            if (other != this && distance < perceptionRadius){
                Vector2D diff = new Vector2D(this.position.x, this.position.y);
                diff.sub(other.position);
                diff.mult((float) Math.sqrt(distance));//inversely proportional to the distance
                localAveragePosition.add(diff);
                total++;
            }
        }
        if (total > 0) {
            localAveragePosition.div(total);
            localAveragePosition.setMag(this.MAX_SPEED);
            localAveragePosition.sub(this.velocity);
            localAveragePosition.limit(MAX_STEERING_FORCE);
        }
        return localAveragePosition;
    }

    //Align Steer towards the average heading of local flockmates
    //TODO: Evaluate this function
    private Vector2D align(Boid[] boids){
        float perceptionRadius = this.PERCEPTIONRADIUS;
        int total = 0;

        Vector2D localAverageVelocity = new Vector2D();
        for(Boid other: boids){
            float distance = Vector2D.dist(this.position, other.position);
            if (other != this && distance < perceptionRadius){
                localAverageVelocity.add(other.velocity);
                total++;
            }
        }
        if (total > 0) {
            localAverageVelocity.div(total);
            localAverageVelocity.setMag(this.MAX_SPEED);
            localAverageVelocity.sub(this.velocity);
            localAverageVelocity.limit(MAX_STEERING_FORCE);
        }
        return localAverageVelocity;
    }

    //Move towards the average position of local flockmates
    //TODO: Evaluate this function
    private Vector2D cohesion(Boid[] boids){
        float perceptionRadius = this.PERCEPTIONRADIUS;
        int total = 0;

        Vector2D localAveragePosition = new Vector2D();
        for(Boid other: boids){
            float distance = Vector2D.dist(this.position, other.position);
            if (other != this && distance < perceptionRadius){
                localAveragePosition.add(other.position);
                total++;
            }
        }
        if (total > 0) {
            localAveragePosition.div(total);
            localAveragePosition.sub(this.position);
            localAveragePosition.setMag(this.MAX_SPEED);
            localAveragePosition.sub(this.velocity);
            localAveragePosition.limit(MAX_STEERING_FORCE);
        }
        return localAveragePosition;
    }

    //Side Note "Behavior" just allows the particles to reinter the screen on leaving the screen
    private void edgeCheck(){
        if (this.position.x > windowSize.x) {
            this.position.x = 0;
          } else if (this.position.x < 0) {
            this.position.x = windowSize.x;
          }
          if (this.position.y > windowSize.y) {
            this.position.y = 0;
          } else if (this.position.y < 0) {
            this.position.y = windowSize.y;
          }
    }
}
