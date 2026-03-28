package initprosesing.core;

import arc.graphics.Color;
import arc.scene.style.Drawable;

public class CoreDrawable implements Drawable {

    private float radius;
    private Color color;

    public CoreDrawable(float radius, Color color){
        this.radius = radius;
        this.color = color;
    }

    // ===== DRAW BASIC =====
    @Override
    public void draw(float x, float y, float width, float height){
       Color drawColor = color.cpy();
    CoreDraw.shadow(x, y, width, height, radius);
    CoreDraw.roundedRect(x, y, width, height, radius, drawColor);
    }

    // ===== DRAW ADVANCED (WAJIB!) =====
    @Override
    public void draw(float x, float y, float originX, float originY,
                     float width, float height,
                     float scaleX, float scaleY,
                     float rotation){

        // kita abaikan transform, pakai basic draw saja
        draw(x, y, width, height);
    }

    // ===== SIZE =====
    @Override public float getMinWidth(){ return 0; }
    @Override public float getMinHeight(){ return 0; }

    @Override public void setMinWidth(float width){}
    @Override public void setMinHeight(float height){}

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