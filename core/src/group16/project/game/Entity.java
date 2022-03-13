package group16.project.game;

import java.util.HashSet;

public final class Entity {
    private HashSet<Component> components = new HashSet<>();
    private String name = "Unnamed entity";

    public Entity() {
        EntityManager.getInstance().addEntity(this);
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        for (Component c : components) {
            if (clazz.isInstance(c)) {
                return clazz.cast(c);
            }
        }
        return  null;
    }
    public <T extends Component> T addComponent(Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor(Entity.class).newInstance(this);
            components.add(obj);
            return obj;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    public void tick() {
        for (Component component : components) {
            component.externalTick();
        }
    }
    public void setName(String name) {
        if (name == null) {
            System.out.println("An entity name was set to null. This is not allowed");
        }
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
