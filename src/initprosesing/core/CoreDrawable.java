package initprosesing.core;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
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
        Fill.color(color);
        Fill.roundRect(x, y, width, height, radius);
        Fill.color(); // reset
    }

    // optional (biar layout ngerti ukuran)
    @Override public float getMinWidth(){ return 0; }
    @Override public float getMinHeight(){ return 0; }
}