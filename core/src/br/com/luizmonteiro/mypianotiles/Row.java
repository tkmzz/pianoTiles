package br.com.luizmonteiro.mypianotiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static br.com.luizmonteiro.mypianotiles.Cons.*;

public class Row {

    public float y;
    private final int correctTile;
    private int pos;
    private boolean ok;
    private boolean dest;
    private float anim;

    public Row(float y, int correctTile){
        this.y = y;
        this.correctTile = correctTile;
        ok = false;
        dest = false;
        anim = 0;
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(green);
        shapeRenderer.rect(correctTile*tileWidth, y, tileWidth, tileHeight);

        if(dest){
            if(ok){
                shapeRenderer.setColor(right);
            } else{
                shapeRenderer.setColor(wrong);
            }

            shapeRenderer.rect(pos*tileWidth + (tileWidth - anim*tileWidth)/2f,
                    y + (tileHeight - anim*tileHeight)/2,
                    anim*tileWidth, anim*tileHeight);
        }

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);

        for(int i=0 ; i<=3 ; i++){
            shapeRenderer.rect(i*tileWidth, y, tileWidth, tileHeight);
        }
    }

    public void anim(float time){
        if(dest && anim < 1){
            anim += 5*time;
            if(anim >= 1){
                anim = 1;
            }
        }
    }

    public int updateSpeed(float time){
        y -= time*currentSpeed;
        if(y < -tileHeight){
            if(ok){
                return 1;
            } else{
                error();
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
                dest = true;
                return 1;
            } else {
                ok = false;
                dest = true;
                return 2;
            }
        }
        return 0;
    }

    public void error(){
        dest = true;
        pos = correctTile;
    }
}
