package initprosesing.core;

import arc.*;
import arc.files.Fi;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Image;
import arc.scene.style.TextureRegionDrawable;
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mods;

public class CoreMenuBackground {

    static TextureRegion[] frames;
    static int index = 0;
    static float timer = 0f;
    static float speed = 0.10f;
    static Image bgImage;
    static boolean useFileLoading = false;

    public static void init() {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            Core.app.post(() -> {
                Log.info("=== INIT MENU BACKGROUND ===");

                // 🔥 CARI ROOT MOD
                Fi modRoot = findModRoot();
                debugModDirectory(modRoot);

                int total = 99;
                frames = new TextureRegion[total];
                int loadedCount = 0;

                // STRATEGI 1: Atlas (jika sprite sudah dipack)
                Log.info("=== STRATEGI 1: Load dari Atlas ===");
                loadedCount = loadFromAtlas(total);

                // STRATEGI 2: File langsung (bypass atlas)
                if (loadedCount == 0 && modRoot != null) {
                    Log.info("=== STRATEGI 2: Load dari File (Bypass Atlas) ===");
                    loadedCount = loadFromFiles(modRoot, total);
                    useFileLoading = true;
                }

                Log.info("📊 Total frames loaded: " + loadedCount + "/" + total);

                // Setup background dan animasi...
                if (loadedCount > 0) {
                    bgImage = new Image();
                    bgImage.setFillParent(true);
                    
                    for (int i = 0; i < frames.length; i++) {
                        if (frames[i] != null && frames[i].found()) {
                            bgImage.setDrawable(new TextureRegionDrawable(frames[i]));
                            index = i;
                            break;
                        }
                    }

                    Vars.ui.menuGroup.addChildAt(0, bgImage);
                    bgImage.toBack();

                    Events.run(EventType.Trigger.update, () -> {
                        if (bgImage == null) return;
                        timer += Time.delta;
                        if (timer >= speed) {
                            timer = 0f;
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

                    Log.info("🎬 Animation started (Mode: " + (useFileLoading ? "FILE" : "ATLAS") + ")");
                } else {
                    Log.err("🚨 GAGAL: Tidak ada frame yang bisa di-load!");
                }
            });
        });
    }

    static Fi findModRoot() {
        if (Vars.mods == null) return null;
        for (Mods.LoadedMod mod : Vars.mods.list()) {
            if (mod.name.toLowerCase().contains("init") || mod.name.toLowerCase().contains("prosesing")) {
                Log.info("✅ Mod found: " + mod.file.absolutePath());
                return mod.file;
            }
        }
        return null;
    }

    static int loadFromAtlas(int total) {
        int loaded = 0;
        for (int i = 0; i < total; i++) {
            String[] names = {
                String.format("bg/set (%d)", i + 1),
                String.format("bg/set-%d", i + 1),
                String.format("bg/set_%d", i + 1)
            };
            for (String name : names) {
                TextureRegion r = Core.atlas.find(name);
                if (r != null && r.found()) {
                    frames[i] = r;
                    loaded++;
                    if (i < 3) Log.info("✅ Atlas: " + name);
                    break;
                }
            }
        }
        return loaded;
    }

    static int loadFromFiles(Fi modRoot, int total) {
        Fi[] paths = {
            modRoot.child("assets/sprites/bg"),
            modRoot.child("sprites/bg"),
            modRoot.child("assets/bg")
        };
        
        Fi bgFolder = null;
        for (Fi f : paths) {
            if (f.exists()) { bgFolder = f; break; }
        }
        
        if (bgFolder == null) return 0;

        int loaded = 0;
        for (int i = 0; i < total; i++) {
            Fi file = bgFolder.child(String.format("set (%d).png", i + 1));
            if (file.exists()) {
                frames[i] = new TextureRegion(new Texture(file));
                loaded++;
                if (i < 3) Log.info("✅ File: set (" + (i+1) + ").png");
            }
        }
        return loaded;
    }

    static void debugModDirectory(Fi root) {
        if (root == null) return;
        Log.info("📦 Mod: " + root.absolutePath());
        for (Fi f : root.list()) {
            Log.info("   " + (f.isDirectory() ? "📁" : "📄") + " " + f.name());
        }
    }
}