package group16.project.game;

import com.badlogic.gdx.math.Vector2;

public class Transform extends Component {
    private Vector2 position = Vector2.Zero;
    private float rotation = 0f;  // In degrees, anti-clockwise
    private float scale = 1f;

    public Transform(Entity entity) {
        super(entity);
    }

    public Vector2 getPosition() {
        return position;
    }
    public void setPosition(Vector2 position) {
        if (position == null) this.position = Vector2.Zero;
        else this.position = position;
    }
    public float getRotation() {
        return rotation;
    }
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
}
