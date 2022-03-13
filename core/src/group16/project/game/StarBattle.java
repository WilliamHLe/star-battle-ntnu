package group16.project.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarBattle extends ApplicationAdapter {

	private EntityManager entityManager;
	
	@Override
	public void create () {
		entityManager = new EntityManager();


		for (int i = 0; i < 10; i++) {
			Entity entity = new Entity();
			entity.setName("Sausage"+i);
			entity.addComponent(Transform.class);
			entity.addComponent(SpriteRenderer.class);
			entity.addComponent(FlyingSausage.class);
		}

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		entityManager.tick();
	}

	/*
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
