package IonEngine.Backend;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public final class Render extends JComponent{

    private RenderWrapper renderQueue = null;
    private boolean renderingFrame = false;
    private RenderWrapper currentWrapper;
    private Dimension renderSize;
    private long last_time = System.nanoTime();
    private Font myFont = new Font ("Sans Serif", 1, 12);
    private double smoothedDelta = 0;
    private int frameCount = 1001;
    private double outputTime = 0;
    private boolean hasOutputed = false;

    public Render(Dimension renderSize){
        setPreferredSize(renderSize);
        this.renderSize = renderSize;
    }

    public void renderFrame(){
        repaint();
    }

    public void paintComponent(Graphics g){
        long time = System.nanoTime();
        float deltaTime = ((time - last_time) / 1000000000f);
        if (outputTime > 30 && !hasOutputed){
            System.out.println("30 Seconds Render Time: " + smoothedDelta);
            hasOutputed = true;
        } else {
            outputTime += deltaTime;
        }
        last_time = time;
        if (this.frameCount > 100){
            smoothedDelta += ((deltaTime * 1000) - smoothedDelta) * 0.08;
            this.frameCount = 0;
        } else {
            this.frameCount++;
        }
        Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont (this.myFont);
            g.setColor(Color.white);
            g.drawString("Render Time: " + String.format("%.5f", smoothedDelta), 5, this.getHeight() - 5);
        if(this.renderQueue != null){
            this.currentWrapper = renderQueue.getRenderStart();
            this.renderingFrame = true;
        } else {
            this.renderingFrame = false;
        }
        while(this.renderingFrame){
            currentWrapper.getObject().internalUpdate(deltaTime);
            currentWrapper.getObject().onUpdate(deltaTime);
            currentWrapper.getObject().render(g);
            this.currentWrapper = currentWrapper.nextWrapper();
            if(this.currentWrapper == null){
                this.renderingFrame = false;
            }
        }
    }

    public void instantiateRenderObject(RenderObject object){
        if (this.renderQueue == null){
            this.renderQueue = new RenderWrapper(object);
        } else {
            new RenderWrapper(object);
        }
    }
}
