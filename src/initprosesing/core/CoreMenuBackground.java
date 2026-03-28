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
    static float speed = 0.08f; // Kecepatan animasi (detik per frame)
    static Image bgImage;
    static boolean isLoaded = false;

    public static void init() {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            // Double post untuk memastikan UI sudah siap sepenuhnya
            Core.app.post(() -> {
                Core.app.post(() -> {
                    Log.info("=== INIT MENU BACKGROUND ===");
                    
                    int totalFrames = 99;
                    frames = new TextureRegion[totalFrames];
                    int loadedCount = 0;

                    // Cari info mod untuk debug
                    Mods.LoadedMod mod = findOurMod();
                    if (mod != null) {
                        Log.info("📦 Mod ditemukan: " + mod.name);
                        Log.info("📍 Lokasi: " + mod.file.absolutePath());
                    }

                    // 🔥 STRATEGI 1: Load dari Atlas (kalau sprite sudah di-pack)
                    loadedCount = loadFromAtlas(totalFrames);
                    
                    // 🔥 STRATEGI 2: Kalau atlas gagal, load langsung dari JAR/Classpath
                    if (loadedCount == 0) {
                        Log.info("🔄 Atlas tidak ketemu, coba load dari file...");
                        loadedCount = loadFromInternalFiles(totalFrames);
                    }

                    Log.info("📊 Total loaded: " + loadedCount + "/" + totalFrames);

                    if (loadedCount > 0) {
                        createBackground();
                        isLoaded = true;
                        Log.info("✅ Background berhasil di-load!");
                    } else {
                        Log.err("🚨 GAGAL LOAD BACKGROUND!");
                        Log.info("💡 Tips: Pastikan file ada di sprites/bg/set (1).png s/d set (99).png");
                        // Debug: Coba test satu file spesifik
                        testFileAccess();
                    }
                });
            });
        });
    }

    static Mods.LoadedMod findOurMod() {
        if (Vars.mods == null) return null;
        for (Mods.LoadedMod m : Vars.mods.list()) {
            String name = m.name.toLowerCase();
            String fileName = m.file.name().toLowerCase();
            if (name.contains("prosesing") || name.contains("init") || 
                fileName.contains("process") || fileName.contains("line")) {
                return m;
            }
        }
        return null;
    }

    static int loadFromAtlas(int total) {
        int count = 0;
        Log.info("=== Loading dari Atlas ===");
        
        for (int i = 0; i < total; i++) {
            // Coba berbagai format nama yang mungkin di atlas
            String[] possibleNames = {
                String.format("bg/set-%d", i + 1),        // bg/set-1
                String.format("bg/set_%d", i + 1),        // bg/set_1
                String.format("bg/set%d", i + 1),         // bg/set1
                String.format("bg/set (%d)", i + 1),      // bg/set (1)
                String.format("bg/set(%d)", i + 1),       // bg/set(1)
            };

            for (String name : possibleNames) {
                TextureRegion region = Core.atlas.find(name);
                if (region != null && region.found()) {
                    frames[i] = region;
                    count++;
                    if (i < 3) Log.info("✅ Atlas: " + name);
                    break;
                }
            }
        }
        return count;
    }

    static int loadFromInternalFiles(int total) {
        int count = 0;
        Log.info("=== Loading dari Internal Files (JAR) ===");
        
        for (int i = 0; i < total; i++) {
            // Format nama file sesuai yang ada di JAR: set (1).png
            String fileName = String.format("sprites/bg/set (%d).png", i + 1);
            
            try {
                Fi file = Core.files.internal(fileName);
                
                if (file.exists()) {
                    Texture tex = new Texture(file);
                    // Set filter untuk smooth scaling
                    tex.setFilter(Texture.TextureFilter.linear, Texture.TextureFilter.linear);
                    frames[i] = new TextureRegion(tex);
                    count++;
                    
                    if (i < 3) Log.info("✅ File: " + fileName);
                } else {
                    // Coba format alternatif tanpa spasi
                    String altName = String.format("sprites/bg/set-%d.png", i + 1);
                    Fi altFile = Core.files.internal(altName);
                    
                    if (altFile.exists()) {
                        Texture tex = new Texture(altFile);
                        tex.setFilter(Texture.TextureFilter.linear, Texture.TextureFilter.linear);
                        frames[i] = new TextureRegion(tex);
                        count++;
                        if (i < 3) Log.info("✅ File: " + altName);
                    }
                }
            } catch (Exception e) {
                if (i < 5) Log.err("❌ Error load frame " + (i + 1) + ": " + e.getClass().getSimpleName());
            }
        }
        return count;
    }

    static void createBackground() {
        bgImage = new Image();
        bgImage.setFillParent(true);
        
        // Set frame pertama yang valid
        TextureRegion firstFrame = null;
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] != null) {
                firstFrame = frames[i];
                index = i;
                break;
            }
        }

        if (firstFrame != null) {
            bgImage.setDrawable(new TextureRegionDrawable(firstFrame));
            
            // Tambahkan ke menuGroup di belakang semua UI
            if (Vars.ui.menuGroup != null) {
                Vars.ui.menuGroup.addChildAt(0, bgImage);
                bgImage.toBack();
                Log.info("🎬 Background ditambahkan ke menu");
            } else {
                Log.err("❌ menuGroup null!");
                return;
            }

            // Setup animation loop
            Events.run(EventType.Trigger.update, () -> {
                if (!isLoaded || bgImage == null) return;
                
                timer += Time.delta;

                if (timer >= speed) {
                    timer = 0f;
                    
                    // Cari frame berikutnya yang valid (skip yang null)
                    int nextIndex = index;
                    int attempts = 0;
                    do {
                        nextIndex = (nextIndex + 1) % frames.length;
                        attempts++;
                    } while ((frames[nextIndex] == null || !frames[nextIndex].found()) 
                             && attempts < frames.length);
                    
                    // Update jika frame valid
                    if (frames[nextIndex] != null && frames[nextIndex].found()) {
                        index = nextIndex;
                        // Update drawable dengan frame baru
                        ((TextureRegionDrawable) bgImage.getDrawable()).setRegion(frames[index]);
                    }
                }
            });
        }
    }

    static void testFileAccess() {
        Log.info("=== FILE ACCESS TEST ===");
        String[] testPaths = {
            "sprites/bg/set (1).png",
            "sprites/bg/set-1.png",
            "sprites/bg/set_1.png",
            "sprites/set (1).png"
        };
        
        for (String path : testPaths) {
            Fi file = Core.files.internal(path);
            Log.info("Path: " + path + " | exists=" + file.exists() + " | type=" + 
                     (file.isDirectory() ? "dir" : "file"));
        }
    }
}