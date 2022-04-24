package IonEngine;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import IonEngine.Backend.Render;
import IonEngine.Types.Particle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import java.awt.Color;

public class Window extends JFrame{

    private static Window currentWindow = null;
    private static Render render = null;
    public static JSlider alignSlider = new JSlider(0, 500, 100);
    public static JSlider cohesionSlider = new JSlider(0, 500, 100);
    public static JSlider separationSlider = new JSlider(0, 500, 100);

    public Window(){
        if (currentWindow != null){
            throw new IllegalStateException("Window is a singleton class. Only one instance can exist at any given point.");
        }

        render = new Render(new Dimension(500, 500));
        getContentPane().setBackground(new Color(44, 44, 44));
        add(render);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Ion Engine");
        initializeEngine();

        setVisible(true);
    }

    public void internalUpdate(){
        render.renderFrame();
    }

    public static Render getRenderer(){
        return render;
    }

    public Window(int width, int height, String title){
        if (currentWindow != null){
            throw new IllegalStateException("Window is a singleton class. Only one instance can exist at any given point.");
        }

        render = new Render(new Dimension(width, height));
        getContentPane().setBackground(new Color(44, 44, 44));
        add(render);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle(title);
        initializeEngine();

        setVisible(true);
    }

    private void initializeEngine(){

        JPanel sliderHolder = new JPanel();

        sliderHolder.setLayout(new GridLayout(1, 3));
        sliderHolder.setPreferredSize(new Dimension(0, 30));
        sliderHolder.setBackground(Color.darkGray);

        alignSlider.setBackground(Color.darkGray);
        alignSlider.setSnapToTicks(true);
        cohesionSlider.setBackground(Color.darkGray);
        cohesionSlider.setSnapToTicks(true);
        separationSlider.setBackground(Color.darkGray);
        separationSlider.setSnapToTicks(true);

        sliderHolder.add(alignSlider);
        sliderHolder.add(cohesionSlider);
        sliderHolder.add(separationSlider);

        this.add(sliderHolder, BorderLayout.SOUTH);

        Particle.internalDataPush(render.getWidth(), render.getHeight());
        System.out.println(render.getWidth());
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Particle.internalDataPush(render.getWidth(), render.getHeight());
            }
        });
    }
}
