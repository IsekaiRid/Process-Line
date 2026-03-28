package initprosesing.core;

import arc.*;
import arc.files.Fi;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Image;
import arc.scene.style.TextureRegionDrawable;
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType;

public class CoreMenuBackground {

    static TextureRegion[] frames;
    static int index = 0;
    static float timer = 0f;
    static float speed = 0.10f;
    static Image bgImage;

    public static void init() {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            Core.app.post(() -> {
                Log.info("=== INIT MENU BACKGROUND ===");

                // 🔥 DEBUG: Log semua directory mod
                debugModDirectory();

                int total = 99;
                frames = new TextureRegion[total];
                int loadedCount = 0;

                // ===== LOAD FRAME =====
                for (int i = 0; i < total; i++) {
                    // Coba beberapa format naming karena Mindustry atlas bisa beda-beda
                    String[] possibleNames = {
                        String.format("bg/set (%d)", i + 1),      // "bg/set (1)"  ← kemungkinan besar ini
                        String.format("bg/set(%d)", i + 1),       // "bg/set(1)"
                        String.format("bg/set-%d", i + 1),      // "bg/set-1"
                        String.format("bg/set_%d", i + 1),        // "bg/set_1"
                        String.format("bg/set.%d", i + 1),        // "bg/set.1"
                    };

                    TextureRegion region = null;
                    String foundName = null;

                    for (String name : possibleNames) {
                        region = Core.atlas.find(name);
                        if (region != null && region.found()) {
                            foundName = name;
                            break;
                        }
                    }

                    if (region != null && region.found()) {
                        frames[i] = region;
                        loadedCount++;
                        if (i < 3) Log.info("✅ Loaded frame " + (i + 1) + ": " + foundName);
                    } else {
                        frames[i] = null;
                        if (i < 5) Log.err("❌ Frame " + (i + 1) + " tidak ditemukan di atlas");
                    }
                }

                Log.info("📊 Total frames loaded: " + loadedCount + "/" + total);

                // ===== DEBUG ATLAS SEMUA REGION =====
                debugAtlas();

                // ===== BUAT BACKGROUND =====
                if (loadedCount > 0) {
                    bgImage = new Image();
                    bgImage.setFillParent(true);
                    
                    // Set frame pertama yang valid
                    for (int i = 0; i < frames.length; i++) {
                        if (frames[i] != null && frames[i].found()) {
                            bgImage.setDrawable(new TextureRegionDrawable(frames[i]));
                            index = i;
                            break;
                        }
                    }

                    Vars.ui.menuGroup.addChildAt(0, bgImage);
                    bgImage.toBack();

                    // ===== ANIMASI =====
                    Events.run(EventType.Trigger.update, () -> {
                        if (bgImage == null) return;
                        
                        timer += Time.delta;

                        if (timer >= speed) {
                            timer = 0f;

                            // Cari frame berikutnya yang valid
                            int attempts = 0;
                            do {
                                index = (index + 1) % frames.length;
                                attempts++;
                            } while ((frames[index] == null || !frames[index].found()) && attempts < frames.length);

                            if (frames[index] != null && frames[index].found()) {
                                ((TextureRegionDrawable) bgImage.getDrawable()).setRegion(frames[index]);
                            }
                        }
                    });

                    Log.info("🎬 Background animation started");
                } else {
                    Log.err("🚨 GAGAL: Tidak ada frame yang bisa di-load!");
                }

                Log.info("=== BACKGROUND READY ===");
            });
        });
    }

    // 🔥 DEBUG: List semua file di directory mod
    public static void debugModDirectory() {
        Log.info("=== DEBUG MOD DIRECTORY ===");
        
        // Coba beberapa path yang mungkin
        String[] paths = {
            "",                          // Root
            "assets",                    // assets/
            "sprites",                   // sprites/
            "sprites/bg",                // sprites/bg/
            "assets/sprites",            // assets/sprites/
            "assets/sprites/bg"          // assets/sprites/bg/
        };

        for (String path : paths) {
            Fi dir = Core.files.internal(path);
            Log.info("📂 Checking: '" + path + "' exists=" + dir.exists() + " isDir=" + dir.isDirectory());
            
            if (dir.exists() && dir.isDirectory()) {
                Fi[] files = dir.list();
                Log.info("   └─ Found " + files.length + " items");
                
                // List max 10 items
                int count = 0;
                for (Fi f : files) {
                    String type = f.isDirectory() ? "📁" : "📄";
                    Log.info("      " + type + " " + f.name());
                    if (++count >= 10) {
                        Log.info("      ... dan " + (files.length - 10) + " lainnya");
                        break;
                    }
                }
            }
        }
    }

    // 🔥 DEBUG: List semua region di atlas yang mengandung "bg"
    public static void debugAtlas() {
        Log.info("=== DEBUG ATLAS (semua region dengan 'bg') ===");
        
        int count = 0;
        for (TextureRegion region : Core.atlas.getRegions()) {
            if (region.name != null && region.name.toLowerCase().contains("bg")) {
                Log.info("🎯 ATLAS: " + region.name + " | found=" + region.found() + 
                         " | width=" + region.width + " | height=" + region.height);
                if (++count >= 20) {
                    Log.info("... dan " + (Core.atlas.getRegions().size - count) + " region lain dengan 'bg'");
                    break;
                }
            }
        }
        
        if (count == 0) {
            Log.warn("⚠️ Tidak ada region dengan 'bg' di atlas!");
        }
    }

    // 🔥 DEBUG: Coba load texture langsung dari file (bypass atlas)
    public static void debugDirectLoad() {
        Log.info("=== DEBUG DIRECT FILE LOAD ===");
        
        for (int i = 1; i <= 5; i++) { // Test 5 frame pertama
            String[] possiblePaths = {
                String.format("sprites/bg/set (%d).png", i),
                String.format("assets/sprites/bg/set (%d).png", i),
                String.format("bg/set (%d).png", i)
            };
            
            for (String path : possiblePaths) {
                Fi file = Core.files.internal(path);
                Log.info("🔍 Checking: " + path + " | exists=" + file.exists());
            }
        }
    }
}