package initprosesing.core;

import arc.*;
import arc.scene.actions.Actions;
import arc.scene.ui.layout.*;
import mindustry.ui.Styles;
import arc.graphics.Color;
import initprosesing.core.CoreDrawable;

public class CorePopup {

    public static void show(){

        Table root = new Table();
        root.setFillParent(true);
        root.center();

        Table card = new Table();
        card.setBackground(
            new CoreDrawable(12f, Color.valueOf("1e1e1e"))
        );

        card.defaults().pad(6);

        card.add("[lightgray]==== Info Mod ==== []").row();

        card.add("[accent]Init Prosesing Core[]")
            .padTop(5)
            .row();

        card.add("Status: [green]Active[]")
            .padTop(5)
            .row();

        card.button("Close", root::remove)
            .size(100, 40)
            .padTop(10);

        root.add(card).pad(10);

        // animasi
        card.actions(
            Actions.alpha(0f),
            Actions.moveBy(0, -40),
            Actions.parallel(
                Actions.fadeIn(0.3f),
                Actions.moveBy(0, 40, 0.3f)
            )
        );

        Core.scene.add(root);
    }
}