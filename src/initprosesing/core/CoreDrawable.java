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

    @Override
    public void draw(float x, float y, float width, float height){
        CoreDraw.shadow(x, y, width, height);
        CoreDraw.softRect(x, y, width, height, color);
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