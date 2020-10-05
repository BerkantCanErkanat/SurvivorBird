package com.berkantcanerkanat.survivorbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bee {
    float x;
    float y;
    final int boy;
    final int en;
    public Bee(float x, float y) {
        this.x = x;
        this.y = y;
        boy = Gdx.graphics.getHeight()/10;
        en = Gdx.graphics.getWidth()/15;
    }
}
