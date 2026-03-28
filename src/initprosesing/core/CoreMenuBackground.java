package initprosesing.core;

import arc.*;
import mindustry.mod.*;
import arc.util.*;
import arc.util.Time;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Image;
import arc.scene.style.TextureRegionDrawable;
import mindustry.Vars;
import mindustry.game.EventType;

public class CoreMenuBackground {

    static TextureRegion[] frames;
    static int index = 0;
    static float timer = 0f;

    static float speed = 0.08f; // 🔥 atur kecepatan animasi

    public static void init(){

        // ✅ tunggu UI siap
        Events.on(EventType.ClientLoadEvent.class, e -> {

            Log.info("INIT MENU BACKGROUND");

            int total = 99;
            frames = new TextureRegion[total];

            // ===== LOAD FRAME =====
            for(int i = 0; i < total; i++){
                String name = String.format("bg/ezgif-frame-%03d", i + 1);

                TextureRegion region = Core.atlas.find(name);

                if(region == null){
                    Log.err("Frame tidak ditemukan: " + name);
                }

                frames[i] = region;
            }

            // ===== BUAT IMAGE BACKGROUND =====
            Image bg = new Image(frames[0]);
            bg.setFillParent(true);

            // 🔥 masuk ke main menu
            Vars.ui.menuGroup.addChild(bg);
            bg.toBack();

            // ===== LOOP ANIMASI =====
            Events.run(EventType.Trigger.update, () -> {

                timer += Time.delta;

                if(timer >= speed){
                    timer = 0f;

                    index++;
                    if(index >= frames.length){
                        index = 0;
                    }

                    if(frames[index] != null){
                        bg.setDrawable(new TextureRegionDrawable(frames[index]));
                    }
                }
            });
        });
    }
}