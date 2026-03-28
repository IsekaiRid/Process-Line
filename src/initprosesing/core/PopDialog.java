package initprosesing.core;

import arc.graphics.Color;
import arc.scene.style.Drawable;

public class PopDialog implements Drawable {

    private float radius;
    private Color color;

    // 🔥 ukuran optional (kalau mau override)
    private float prefWidth = -1;
    private float prefHeight = -1;

    public PopDialog(float radius, Color color){
        this.radius = radius;
        this.color = color;
    }

    // 🔥 constructor dengan ukuran
    public PopDialog(float radius, Color color, float width, float height){
        this.radius = radius;
        this.color = color;
        this.prefWidth = width;
        this.prefHeight = height;
    }

    // ===== DRAW BASIC =====
    @Override
    public void draw(float x, float y, float width, float height){

        Color drawColor = color.cpy();

        // shadow
        CoreDraw.shadow(x, y, width, height, radius);

        // background
        CoreDraw.roundedRect(x, y, width, height, radius, drawColor);
    }

    // ===== DRAW ADVANCED =====
    @Override
    public void draw(float x, float y, float originX, float originY,
                     float width, float height,
                     float scaleX, float scaleY,
                     float rotation){

        draw(x, y, width, height);
    }

    // ===== SIZE CONTROL =====
    @Override
    public float getMinWidth(){
        return prefWidth >= 0 ? prefWidth : 0;
    }

    @Override
    public float getMinHeight(){
        return prefHeight >= 0 ? prefHeight : 0;
    }

    @Override public void setMinWidth(float width){ this.prefWidth = width; }
    @Override public void setMinHeight(float height){ this.prefHeight = height; }

    // ===== PADDING =====
    @Override public float getTopHeight(){ return 0; }
    @Override public float getBottomHeight(){ return 0; }
    @Override public float getLeftWidth(){ return 0; }
    @Override public float getRightWidth(){ return 0; }

    @Override public void setTopHeight(float height){}
    @Override public void setBottomHeight(float height){}
    @Override public void setLeftWidth(float width){}
    @Override public void setRightWidth(float width){}
}