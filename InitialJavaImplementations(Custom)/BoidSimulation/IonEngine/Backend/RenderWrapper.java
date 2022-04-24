package IonEngine.Backend;

public class RenderWrapper {

    private static RenderWrapper firstObject;
    private static RenderWrapper lastObject;
    private RenderObject object;
    private RenderWrapper nextWrapper;

    public RenderWrapper(RenderObject object){
        object.onStart();
        if (this.firstObject == null){
            this.firstObject = this;
            this.lastObject = this;
        } else {
            this.lastObject.setNextWrapper(this);
            this.lastObject = this;
        }
        this.object = object;
    }

    public RenderObject getObject(){
        return this.object;
    }

    public RenderWrapper nextWrapper(){
        return this.nextWrapper;
    }

    public void setNextWrapper(RenderWrapper nextWrapper){
        this.nextWrapper = nextWrapper;
    }

    public RenderWrapper getRenderStart(){
        return firstObject;
    }

}
