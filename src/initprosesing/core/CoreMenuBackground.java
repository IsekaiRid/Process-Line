package initprosesing.core;

import arc.*;
import arc.files.Fi;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Image;
import arc.scene.style.TextureRegionDrawable;
import arc.util.*;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;

public class CoreMenuBackground {

    static TextureRegion[] frames;
    static int index = 0;
    static float timer = 0f;

    static float speed = 0.10f;

    public static void init(){

        Events.on(EventType.ClientLoadEvent.class, e -> {

            Core.app.post(() -> {

                Log.info("=== INIT MENU BACKGROUND ===");

                int total = 99; // sesuaikan jumlah file
                frames = new TextureRegion[total];

                // ===== LOAD FRAME =====
                for(int i = 0; i < total; i++){

                    // 🔥 SESUAI FILE KAMU: set (1).png
                    String name = String.format("bg/set-(%d)", i + 1);

                    TextureRegion region = Core.atlas.find(name);

                    if(region == null || !region.found()){
                        Log.err("❌ Frame tidak ditemukan: " + name);
                    }else{
                        Log.info("✅ Loaded: " + name);
                    }

                    frames[i] = region;
                }

                // ===== DEBUG ATLAS =====
                Log.info("=== LIST ATLAS (bg) ===");
                Core.atlas.getRegions().each(r -> {
                    if(r.name.contains("bg")){
                        Log.info("FOUND: " + r.name);
                    }
                });

                // ===== TEST SATU FRAME =====
                TextureRegion test = Core.atlas.find("bg/set-(1)");

                if(test == null || !test.found()){
                    Log.err("🚨 TEST GAGAL: frame pertama tidak kebaca!");
                }else{
                    Log.info("🔥 TEST BERHASIL: frame pertama OK!");
                }

                // ===== LOG ISI ASSETS =====
                logAssets();

                // ===== BUAT BACKGROUND =====
                Image bg = new Image();
                bg.setFillParent(true);

                Vars.ui.menuGroup.addChild(bg);
                bg.toBack();

                // ===== ANIMASI =====
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

                Log.info("=== BACKGROUND READY ===");
            });
        });
    }

    // ===== FUNCTION LOG ASSETS =====
    public static void logAssets(){
        Core.app.post(() -> {

            Log.info("=== LIST FILE ASSETS ===");

            Fi assets = Core.files.internal("assets");

            if(!assets.exists()){
                Log.err("❌ Folder assets tidak ditemukan!");
                return;
            }

            listFiles(assets, "assets/");
        });
    }

    public static void listFiles(Fi dir, String prefix){
        for(Fi f : dir.list()){
            if(f.isDirectory()){
                Log.info("📁 " + prefix + f.name());
                listFiles(f, prefix + f.name() + "/");
            }else{
                Log.info("📄 " + prefix + f.name());
            }
        }
    }
}