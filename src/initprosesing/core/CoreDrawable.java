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

        // shadow dulu
        CoreDraw.shadow(x, y, width, height);

        // background
        CoreDraw.softRect(x, y, width, height, color);
    }

    @Override public float getMinWidth(){ return 0; }
    @Override public float getMinHeight(){ return 0; }
}