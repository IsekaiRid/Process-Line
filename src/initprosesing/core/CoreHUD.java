package initprosesing.core;

import arc.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.ui.Styles;

public class CoreHUD {

    public static void init(){

        Events.on(ClientLoadEvent.class, e -> {

            Log.info("HUD INIT DIPANGGIL");

            Table table = new Table();

            // WAJIB!!!
            table.setFillParent(true);

            table.top().right();
            table.margin(10);

            table.add("[lightgray]==== Info Mod ==== []").row();

            table.add("[accent]Init Prosesing Core[]")
                .padTop(5)
                .row();

            table.add("Status: [green]Active[]")
                .padTop(5);

            Core.scene.add(table);
        });
    }
}