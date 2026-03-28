package initprosesing.core;

import arc.Events;
import arc.scene.ui.layout.*;
import mindustry.Vars;
import mindustry.game.EventType.*;

public class CoreHUD {

    public static void init(){

        Events.on(ClientLoadEvent.class, e -> {

            Table table = new Table();

            table.top().right();
            table.margin(10);

            table.add("[lightgray]==== Info Mod ==== []").row();

            table.add("[accent]Init Prosesing Core[]")
                .padTop(5)
                .row();

            table.add("Status: [green]Active[]")
                .padTop(5);

            Vars.ui.hudGroup.addChild(table);
        });
    }
}