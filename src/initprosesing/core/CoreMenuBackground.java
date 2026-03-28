package initprosesing.core;

import arc.*;
import arc.util.*;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Image;
import arc.scene.style.TextureRegionDrawable;
import mindustry.Vars;
import mindustry.game.EventType;

public class CoreMenuBackground {

    static TextureRegion[] frames;
    static int index = 0;
    static float timer = 0f;

    static float speed = 0.08f; // 🔥 kecepatan animasi (kecil = lebih cepat)

    public static void init(){

        int total = 99;
        frames = new TextureRegion[total];

        // ===== LOAD FRAME =====
        for(int i = 0; i < total; i++){
            String name = String.format("bg/ezgif-frame-%03d", i + 1);
            frames[i] = Core.atlas.find(name);
        }

        // ===== IMAGE BACKGROUND =====
        Image bg = new Image(frames[0]);
        bg.setFillParent(true);

        // masuk ke menu
        Vars.ui.menuGroup.addChild(bg);
        bg.toBack(); // 🔥 biar di paling belakang

        // ===== LOOP ANIMASI =====
        Events.run(EventType.Trigger.update, () -> {

            timer += Time.delta;

            if(timer >= speed){
                timer = 0f;

                index++;
                if(index >= frames.length){
                    index = 0;
                }

                bg.setDrawable(new TextureRegionDrawable(frames[index]));
            }
        });
    }
}