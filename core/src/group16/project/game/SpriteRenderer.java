package group16.project.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SpriteRenderer extends Component {
    private boolean flipX;
    private boolean flipY;
    private Texture texture;
    private SpriteBatch batch = new SpriteBatch();

    private Transform cachedTransform;

    protected SpriteRenderer(Entity entity) {
        super(entity);
    }
    @Override
    protected void tick() {
        if (texture == null) {
            System.out.println("SpriteRenderer texture missing");
            return;
        }

        if (cachedTransform == null) cachedTransform = entity.getComponent(Transform.class);
        Vector2 pos = cachedTransform.getPosition();
        int srcWidth = texture.getWidth();
        int srcHeight = texture.getHeight();
        int srcX = 0;
        int srcY = 0;
        float scale = cachedTransform.getScale();
        int width = (int)Math.floor(srcWidth*scale);
        int height = (int)Math.floor(srcHeight*scale);

        batch.begin();
        batch.draw(texture, pos.x, pos.y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
        batch.end();
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }
    public boolean getFlipX() {
        return flipX;
    }
    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }
    public boolean getFlipY() {
        return flipY;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public Texture getTexture() {
        return texture;
    }
}
