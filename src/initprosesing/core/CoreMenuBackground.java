package initprosesing.core;

import arc.*;
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

    static float speed = 0.08f;

    public static void init(){

        Events.on(EventType.ClientLoadEvent.class, e -> {

            Core.app.post(() -> {

                Log.info("INIT MENU BACKGROUND");

                int total = 99;
                frames = new TextureRegion[total];

                for(int i = 0; i < total; i++){
                    String name = String.format("bg/ezgif-frame-%03d", i + 1);

                    TextureRegion region = Core.atlas.find(name);

                    if(region == null || !region.found()){
                        Log.err("❌ Frame tidak ditemukan: " + name);
                    }

                    frames[i] = region;
                }

                Image bg = new Image();
                bg.setFillParent(true);

                Vars.ui.menuGroup.addChild(bg);
                bg.toBack();

                Events.run(EventType.Trigger.update, () -> {

                    timer += Time.delta;

                    if(timer >= speed){
                        timer = 0f;

                        index = (index + 1) % frames.length;

                        TextureRegion frame = frames[index];

                        if(frame != null && frame.found()){
                            bg.setDrawable(new TextureRegionDrawable(frame));
                        }
                    }
                });

            });
        });
    }
}