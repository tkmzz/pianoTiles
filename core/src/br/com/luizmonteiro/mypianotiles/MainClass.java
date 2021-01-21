package br.com.luizmonteiro.mypianotiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class MainClass extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;

	private Array<Row> rows;

	private float totalTime;

	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		rows = new Array<Row>();
		rows.add(new Row(0, 0));
		rows.add(new Row(Cons.tileHeight, 1));
		rows.add(new Row(2*Cons.tileHeight, 2));

		totalTime = 0;

	}

	@Override
	public void render () {
		input();

		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin();

		for(Row r: rows){
			r.draw(shapeRenderer);
		}

		shapeRenderer.end();
	}

	private void input() {
		
	}

	private void update(float deltaTime) {
		totalTime += deltaTime;

		Cons.currentSpeed = Cons.initSpeed + Cons.tileHeight*totalTime/8f;

		for(Row r: rows){
			r.updateSpeed(deltaTime);
		}
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
