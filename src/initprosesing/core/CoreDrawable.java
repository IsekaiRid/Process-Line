package initprosesing.core;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.scene.style.Drawable;

public class CoreDrawable implements Drawable {

    float radius;
    Color color;

    public CoreDrawable(float radius, Color color){
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw(float x, float y, float width, float height){

        Draw.color(color);

        Fill.rect(
            x + width/2f,
            y + height/2f,
            width,
            height
        );

        Draw.color();
    }

    @Override public float getMinWidth(){ return 0; }
    @Override public float getMinHeight(){ return 0; }
}