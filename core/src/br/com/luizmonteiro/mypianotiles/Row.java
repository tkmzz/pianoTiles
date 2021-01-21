package br.com.luizmonteiro.mypianotiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Row {

    private float y;
    private int correctTile;

    public Row(float y, int correctTile){
        this.y = y;
        this.correctTile = correctTile;
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Cons.green);
        shapeRenderer.rect(correctTile*Cons.tileWidth, y, Cons.tileWidth, Cons.tileHeight);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);

        for(int i=0 ; i<=3 ; i++){
            shapeRenderer.rect(i*Cons.tileWidth, y, Cons.tileWidth, Cons.tileHeight);
        }
    }

    public void updateSpeed(float time){
        y -= time*Cons.currentSpeed;
    }

}
