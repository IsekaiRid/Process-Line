package initprosesing.core;

import arc.scene.ui.layout.*;
import mindustry.Vars;

public class CoreHUD {

    public static void init(){

        // ambil HUD group
        Table table = new Table();

        // posisi pojok kanan atas
        table.top().right();
        table.margin(10);

        // isi UI
        table.add("[lightgray]==== Info Mod ==== []").row();

        table.add("[accent]Init Prosesing Core[]")
            .padTop(5)
            .row();

        table.add("Status: [green]Active[]")
            .padTop(5);

        // tambahkan ke HUD
        Vars.ui.hudGroup.addChild(table);
    }
}