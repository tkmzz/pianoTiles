package br.com.luizmonteiro.mypianotiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class MainClass extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private Texture txtStart;

	private Array<Row> rows;

	private float totalTime;
	private int indexInf;
	private int score;
	private Random random;
	private int status;
	private Piano piano;
	private BitmapFont font;
	private GlyphLayout glyphLayout;

	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		txtStart = new Texture("iniciar.png");
		shapeRenderer.setAutoShapeType(true);
		piano = new Piano("natal");

		random = new Random();

		rows = new Array<Row>();

		glyphLayout = new GlyphLayout();

		initFont();
		initMusic();

	}

	public void initFont(){
		FreeTypeFontGenerator.setMaxTextureSize(2048);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int)(0.06f*Cons.screenY);
		parameter.color = Color.CYAN;
		font = generator.generateFont(parameter);
		generator.dispose();
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


		batch.begin();

		if(status == 0) batch.draw(txtStart, 0, Cons.tileHeight/4, Cons.screenX, Cons.tileHeight/2);

		font.draw(batch, String.valueOf(score), 0, Cons.screenY);

		font.draw(batch, String.format("%.2f", Cons.currentSpeed/Cons.tileHeight), Cons.screenX-getWidth(font, String.format("%.2f", Cons.currentSpeed/Cons.tileHeight)), Cons.screenY);

		batch.end();
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
							piano.play();
						} else if(isRightTouched == 1 ){
							rows.get(indexInf).error();
							gameOver(0);
						} else {
							gameOver(0);
						}
						break;
					}
				}
			}else if(status == 2) initMusic();
		}
	}

	private void update(float deltaTime) {
		if(status ==1){
			totalTime += deltaTime;

			Cons.currentSpeed = Cons.initSpeed + Cons.tileHeight*totalTime/8f;

			for(int i=0 ; i<rows.size; i++){
				int updateReturn = rows.get(i).updateSpeed(deltaTime);
				rows.get(i).anim(deltaTime);

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
		} else if(status == 2){
			for (Row r:rows){
				r.anim(deltaTime);
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

		Cons.currentSpeed = 0;

		piano.reset();
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

	private float getWidth(BitmapFont font, String text){
		glyphLayout.reset();
		glyphLayout.setText(font, text);
		return glyphLayout.width;
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
		batch.dispose();
		txtStart.dispose();
		piano.dispose();
	}
}
