package IonEngine.Backend;
import java.awt.Graphics;

public interface RenderObject {
    public abstract void onStart();
    public abstract void onUpdate(float deltaTime);
    public abstract void render(Graphics g);
    public abstract void internalUpdate(float deltaTime);
}
