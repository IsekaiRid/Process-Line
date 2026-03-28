package initprosesing.core;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class CoreUI {

    public static void init(){
        Log.info("CoreUI Initialized");

        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10f, () -> {

                BaseDialog dialog = new BaseDialog("Init Prosesing Core");

                // teks utama
                dialog.cont.add("[accent]Hallo[]").pad(10f).row();

                // sub teks biar lebih hidup
                dialog.cont.add("[lightgray]System initialized successfully[]")
                    .pad(5f)
                    .row();

                // tombol
                dialog.cont.button("OK", dialog::hide)
                    .size(120f, 50f)
                    .padTop(10f);

                dialog.show();
            });
        });
    }
}