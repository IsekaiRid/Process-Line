package initprosesing.core;

import arc.*;
import arc.scene.actions.Actions;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.game.EventType.*;

public class CoreHUD {

    public static void init(){

        Events.on(ClientLoadEvent.class, e -> {

            Log.info("HUD INIT DIPANGGIL");

            Table root = new Table();
            root.setFillParent(true);
            root.top().right();

            // CARD
            Table card = new Table();

            // background card
            card.setBackground(Styles.black6);

            card.defaults().pad(5);

            // isi card
            card.add("[lightgray]==== Info Mod ==== []").row();

            card.add("[accent]Init Prosesing Core[]")
                .padTop(5)
                .row();

            card.add("Status: [green]Active[]")
                .padTop(5)
                .row();

            // tombol close
            card.button("Close", card::remove)
                .size(100, 40)
                .padTop(10);

            // kasih margin biar gak nempel layar
            root.add(card).pad(10);

            // animasi muncul (fade + slide)
            card.actions(
                Actions.alpha(0f),
                Actions.moveBy(0, -50),
                Actions.parallel(
                    Actions.fadeIn(0.4f),
                    Actions.moveBy(0, 50, 0.4f)
                )
            );

            Core.scene.add(root);
        });
    }
}