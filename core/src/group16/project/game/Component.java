package group16.project.game;

public abstract class Component {
    public final Entity entity;  // public is probably fine because it's final
    private boolean started = false;  // unstarted components call start() instead of tick()

    protected Component(Entity entity) {
        this.entity = entity;
    }

    public final void externalTick() {
        if (!started) {
            start();
            started = true;
        } else {
            tick();
        }
    }

    protected void start() {
        // Called before first tick.
        // You may override this method in subclasses.
    }
    protected void tick() {
        // Called every tick.
        // You may override this method in subclasses.
    }
    public boolean isStarted() {
        return started;
    }
}
