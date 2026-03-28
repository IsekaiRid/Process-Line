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

    // ✨ shadow sederhana
    public static void shadow(float x, float y, float w, float h){
        Draw.color(0f, 0f, 0f, 0.4f);

        Fill.rect(
            x + w/2f + 3,
            y + h/2f - 3,
            w,
            h
        );

        Draw.color();
    }
}