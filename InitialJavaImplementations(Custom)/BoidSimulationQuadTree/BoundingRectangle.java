

//Supper simple bounding rectangle type
// Ported from Javascript to Java by Justin Holder
//      Original work and derivation by Daniel Shiffman (GitHub: https://github.com/CodingTrain/website/blob/main/CodingChallenges/CC_098.1_QuadTree/P5/quadtree.js)

public class BoundingRectangle {

    public float x;
    public float y;
    public float w;
    public float h;

    public BoundingRectangle(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean contains(Point point){
        return (
            point.x >= this.x - this.w &&
            point.x <= this.x + this.w &&
            point.y >= this.y - this.h &&
            point.y <= this.y + this.h
        );
    }

    public boolean intersects(BoundingRectangle area){
        return !(
            area.x - area.w > this.x + this.w ||
            area.x + area.w < this.x - this.w ||
            area.y - area.h > this.y + this.h ||
            area.y + area.h < this.y - this.h
        );
    }

}
