package initprosesing.core;

import arc.Events;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.ui.dialogs.*;

public class CoreUI {

    public static void init(){
        Log.info("CoreUI Initialized");

        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10f, () -> {

                BaseDialog dialog = new BaseDialog("");
                dialog.clear();

                // buat card di tengah
                dialog.cont.pane(t -> {

                    t.defaults().pad(5);

                    // judul kecil
                    t.add("[lightgray]==== Info Mod ==== []")
                        .row();

                    // judul utama
                    t.add("[accent]Init Prosesing Core[]")
                        .padTop(10)
                        .row();

                    // isi
                    t.add("System initialized successfully")
                        .padTop(10)
                        .row();

                    // tombol
                    t.button("OK", dialog::hide)
                        .size(120f, 50f)
                        .padTop(15);

                }).width(300f).center();

                dialog.show();
            });
        });
    }
}