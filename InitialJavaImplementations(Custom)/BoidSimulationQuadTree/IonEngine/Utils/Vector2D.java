package IonEngine.Utils;

/**
 * Simple utility class for handling 2D vectors and the vectors and vector math.
 *
 * @param x
 * @param y
 */
public class Vector2D{

    public float x;
    public float y;

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(){
        this.x = 0;
        this.y = 0;
    }

    public float[] getPosition(){
        float[] position = {x, y};
        return position;
    }

    public float getPositionX(){
        return x;
    }

    public float getPositionY(){
        return y;
    }

    public void setPositionX(float x){
        this.x = x;
    }

    public void setPositionY(float y){
        this.y = y;
    }

    public void add(Vector2D otherVector){
        this.x += otherVector.x;
        this.y += otherVector.y;
    }

    public void sub(Vector2D otherVector){
        this.x -= otherVector.x;
        this.y -= otherVector.y;
    }

    public void div(float value){
        this.x /= value;
        this.y /= value;
    }

    public void mult(float value){
        this.x *= value;
        this.y *= value;
    }

    public static float dist(Vector2D vectorOne, Vector2D vectorTwo){
        return (float) Math.sqrt((Math.pow(vectorOne.x - vectorTwo.x, 2)) + (Math.pow(vectorOne.y - vectorTwo.y, 2)));
    }

    public void limit(float max){
        float mSq = this.magSq();
        if (mSq > max * max){
            this.div((float) Math.sqrt(mSq));
            this.mult(max);
        }
    }

    public float magSq(){
        float x = this.x;
        float y = this.y;
        return x * x + y * y;
    };

    public void setMag(float value){
        this.normalize().mult(value);
    }

    public Vector2D normalize(){
        float len = this.mag();
        if (len != 0f){
            this.mult(1 / len);
        }
        return this;
    }

    public float mag(){
        return (float) Math.sqrt(this.magSq());
    }

}
