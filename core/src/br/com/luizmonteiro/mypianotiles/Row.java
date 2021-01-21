package br.com.luizmonteiro.mypianotiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static br.com.luizmonteiro.mypianotiles.Cons.*;

public class Row {

    public float y;
    private final int correctTile;
    private int pos;
    private boolean ok;

    public Row(float y, int correctTile){
        this.y = y;
        this.correctTile = correctTile;
        ok = false;
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(green);
        shapeRenderer.rect(correctTile*tileWidth, y, tileWidth, tileHeight);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);

        for(int i=0 ; i<=3 ; i++){
            shapeRenderer.rect(i*tileWidth, y, tileWidth, tileHeight);
        }
    }

    public int updateSpeed(float time){
        y -= time*currentSpeed;
        if(y < -tileHeight){
            if(ok){
                return 1;
            } else{
                return 2;
            }
        }
        return 0;
    }

    public int touch(int tX, int tY) {

        if(tY >= y && tY <= y + tileHeight){
            pos = tX/tileWidth;

            if(pos == correctTile){
                ok = true;
                return 1;
            } else {
                ok = false;
                return 2;
            }
        }
        return 0;
    }
}
