package br.com.luizmonteiro.mypianotiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class MainClass extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;

	private Array<Row> rows;

	private float totalTime;
	private int indexInf;
	private int score;
	private Random random;
	private int status;

	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		random = new Random();

		rows = new Array<Row>();
		initMusic();

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
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX();
			int y = Cons.screenY - Gdx.input.getY();

			if(status == 0) status = 1;

			if(status == 1){
				for(int i = 0; i<rows.size; i++){
					int isRightTouched = rows.get(i).touch(x, y);

					if(isRightTouched != 0){
						if(isRightTouched == 1 && i == indexInf){
							score++;
							indexInf++;
						} else if(isRightTouched == 1 ){
							gameOver(0);
						} else {
							gameOver(0);
						}
						break;
					}
				}
			}
			if(status == 2) initMusic();
		}
	}

	private void update(float deltaTime) {
		if(status ==1){
			totalTime += deltaTime;

			Cons.currentSpeed = Cons.initSpeed + Cons.tileHeight*totalTime/8f;

			for(int i=0 ; i<rows.size; i++){
				int updateReturn = rows.get(i).updateSpeed(deltaTime);

				if(updateReturn != 0){
					if(updateReturn == 1){
						rows.removeIndex(i);
						i--;
						indexInf--;
						addRow();
					} else if(updateReturn == 2){
						gameOver(1);
					}
				}
			}
		}
	}

	private void addRow() {
		float y = rows.get(rows.size-1).y + Cons.tileHeight;
		rows.add(new Row(y, random.nextInt(4)));
	}

	private void initMusic(){
		totalTime = 0;
		indexInf = 0;
		score = 0;

		rows.clear();
		rows.add(new Row(Cons.tileHeight, random.nextInt(4)));
		addRow();
		addRow();
		addRow();
		addRow();

		status = 0;
	}

	private void gameOver(int opt) {
		Gdx.input.vibrate(200);
		status = 2;
		if(opt == 1){
			for(Row r: rows){
				r.y += Cons.tileHeight;
			}
		}
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
