package group16.project.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import sun.security.action.GetBooleanAction;

public class FlyingSausage extends Component {
    private Transform cachedTransform;
    private Random random = new Random();


    public FlyingSausage(Entity entity) {
        super(entity);
    }

    @Override
    protected void start() {
        String textureName;
        float scale;
        if (random.nextBoolean()) {
            textureName = "yogurt.jpg";
            scale = 0.3f;
        } else {
            textureName = "badlogic.jpg";
            scale = 0.7f;
        }
        entity.getComponent(Transform.class).setScale(scale);
        Texture texture = new Texture(textureName);

        entity.getComponent(SpriteRenderer.class).setTexture(texture);
        entity.getComponent(Transform.class).setPosition(new Vector2(random.nextFloat()*400, random.nextFloat()*300));
    }

    @Override
    protected void tick() {
        if (cachedTransform == null) cachedTransform = entity.getComponent(Transform.class);
        float x = (random.nextFloat()-0.5f)*5f;
        float y = (random.nextFloat()-0.5f)*5f;
        Vector2 randomMovement = new Vector2(x, y);
        cachedTransform.setPosition(cachedTransform.getPosition().add(randomMovement));
    }
}
