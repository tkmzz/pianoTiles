package br.com.luizmonteiro.mypianotiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Cons {

    public static Color green = new Color(0, 0.4f, 0, 1);

    public static int screenX = Gdx.graphics.getWidth();
    public static int screenY = Gdx.graphics.getHeight();

    public static int tileWidth = screenX/4;
    public static int tileHeight = screenY/4;

    public static float initSpeed = 2*tileHeight/1f;
    public static float currentSpeed = 0;

}
