package group16.project.game;

import java.util.HashSet;

public final class EntityManager {
    private static EntityManager instance;
    private HashSet<Entity> entities = new HashSet<>();

    public EntityManager() {
        instance = this;
    }

    public static EntityManager getInstance() {
        return instance;
    }
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void tick() {
        for (Entity entity : entities) {
            entity.tick();
        }
    }
}
