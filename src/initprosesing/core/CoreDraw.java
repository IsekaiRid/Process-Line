package initprosesing.core;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;

public class CoreDraw {

    // 🔲 basic rect
    public static void rect(float x, float y, float width, float height, Color color){
        Draw.color(color);

        Fill.rect(
            x + width / 2f,
            y + height / 2f,
            width,
            height
        );

        Draw.color();
    }

    // 🔳 rect dengan border
    public static void rectWithBorder(float x, float y, float w, float h, Color fill, Color border){

        // isi
        rect(x, y, w, h, fill);

        // border sederhana (4 sisi)
        float t = 2f;

        rect(x, y, w, t, border);             // bawah
        rect(x, y + h - t, w, t, border);     // atas
        rect(x, y, t, h, border);             // kiri
        rect(x + w - t, y, t, h, border);     // kanan
    }

    // 🔥 pseudo rounded (simple illusion)
    public static void softRect(float x, float y, float w, float h, Color color){
        // tengah
        rect(x + 4, y + 4, w - 8, h - 8, color);

        // sisi
        rect(x + 4, y, w - 8, 4, color);
        rect(x + 4, y + h - 4, w - 8, 4, color);

        rect(x, y + 4, 4, h - 8, color);
        rect(x + w - 4, y + 4, 4, h - 8, color);
    }

    public static void roundedRect(float x, float y, float w, float h, float r, Color color){

        Draw.color(color);

        // center rect
        Fill.rect(x + w/2f, y + h/2f, w - r*2, h);

        // kiri kanan
        Fill.rect(x + r/2f, y + h/2f, r, h - r*2);
        Fill.rect(x + w - r/2f, y + h/2f, r, h - r*2);

        // 4 sudut (pakai circle kecil)
        Fill.circle(x + r, y + r, r);
        Fill.circle(x + w - r, y + r, r);
        Fill.circle(x + r, y + h - r, r);
        Fill.circle(x + w - r, y + h - r, r);

        Draw.color();
    }

   public static void shadow(float x, float y, float w, float h, float r){
        Draw.color(0f, 0f, 0f, 0.25f);

        roundedRect(x + 4, y - 4, w, h, r, new Color(0f,0f,0f,0.25f));

        Draw.color();
    }
}