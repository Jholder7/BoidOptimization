package IonEngine.Types;

import IonEngine.Backend.RenderObject;

import java.awt.Graphics;
import IonEngine.Window;
import IonEngine.Utils.Vector2D;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.util.Random;

public abstract class Particle implements RenderObject {

    protected Vector2D position = new Vector2D(0, 0);
    protected Vector2D velocity = new Vector2D(0, 0);
    protected Vector2D size = new Vector2D(5, 5);
    static protected Vector2D windowSize;
    private static Color[] potentialColor = {Color.decode("#70d6ff"), Color.decode("#ff70a6"), Color.decode("#ff9770"), Color.decode("#ffd670"), Color.decode("#e9ff70")};
    private Color color;
    private Random random = new Random();

    private int[] xPoints = {4, 4 ,17};
    private int[] yPoints = {-4, 4 , 0};
    private int OFFSET = 0;
    private float angle = 0;

    public Particle (){
        Window.getRenderer().instantiateRenderObject(this);
        this.color = potentialColor[random.nextInt(5)];
    }

    public void internalUpdate(float deltaTime){
        //todo: handle mass and declaration
        setPosition(new Vector2D(position.x + (velocity.x * deltaTime), position.y + (velocity.y * deltaTime)));
    }

    public final void setPosition(Vector2D position){
        this.position = position;
    }

    public final Vector2D getPosition(){
        return position;
    }

    public void setAngle(float angle){
        this.angle = angle*-1;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(this.color);
        //g.drawOval(Math.round(position.x), Math.round(position.y), (int) size.x, (int) size.y);

        AffineTransform at = new AffineTransform();
        Dimension size = getTriangleSize();
        int x = Math.round(position.x) - (size.width / 2);
        int y = Math.round(position.y) - (size.height / 2);
        at.translate(x, y);
        at.rotate(Math.toRadians(angle + this.OFFSET), Math.round(position.x) - x, Math.round(position.y) - y);
        g2d.setTransform(at);
        g2d.drawPolygon(xPoints, yPoints, 3);
        // Guide
        g2d.setColor(this.color);
    }

    protected Dimension getTriangleSize() {
        int maxX = 0;
        int maxY = 0;
        for (int index = 0; index < xPoints.length; index++) {
            maxX = Math.max(maxX, xPoints[index]);
        }
        for (int index = 0; index < yPoints.length; index++) {
            maxY = Math.max(maxY, yPoints[index]);
        }
        return new Dimension(maxX, maxY);
    }

    static public void internalDataPush(int windowWidth, int windowHeight){
        windowSize = new Vector2D(windowWidth, windowHeight);
    }

    public void onStart(){};
    public void onUpdate(float deltaTime){};

}
