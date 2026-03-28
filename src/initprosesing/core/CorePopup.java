package initprosesing.core;

import arc.*;
import arc.scene.actions.Actions;
import arc.scene.ui.layout.*;
import arc.graphics.Color;

public class CorePopup {

    public static void show(){

        Table root = new Table();
        root.setFillParent(true);
        root.center();

        // ===== CARD =====
        Table card = new Table();

        card.setBackground(
            new PopDialog(14f, new Color(0f, 0f, 0f, 0.75f))
        );

        card.defaults().pad(6);

        // ===== TITLE =====
        card.add("[lightgray]==== Info Mod ==== []")
            .padBottom(4)
            .row();

        card.add("[accent]Init Prosesing Core[]")
            .padBottom(8)
            .row();

        // ===== INFO SECTION =====
        Table info = new Table();

        info.left();

        info.add("[gray]Status: []")
            .left();

        info.add("[green]Active[]")
            .left()
            .row();

        info.add("[gray]Type: []")
            .left();

        info.add("[accent]Java Mod[]")
            .left()
            .row();

        info.add("[gray]Purpose: []")
            .left();

        info.add("[white]Learning Modding Java[]")
            .left()
            .row();

        card.add(info).left().row();

        // ===== SEPARATOR =====
        card.add("[darkgray]------------------------")
            .padTop(6)
            .padBottom(6)
            .row();

        // ===== LINKS =====
        Table links = new Table();
        links.left();

        links.add("[gray]Github:[]").left().row();

        links.add("[sky]github.com/IsekaiRid/Process-Line[]")
            .left()
            .row();

        links.add("[gray]YouTube:[]")
            .padTop(4)
            .left()
            .row();

        links.add("[orange]youtube.com/@ridhwanrplwibu[]")
            .left()
            .row();

        card.add(links).left().row();

        // ===== BUTTON =====
        card.button("[accent]Close[]", root::remove)
            .size(120, 45)
            .padTop(10);

        // ===== ROOT ADD =====
        root.add(card).width(320).pad(10);

        // ===== ANIMATION =====
        card.actions(
            Actions.alpha(0f),
            Actions.scaleTo(0.9f, 0.9f),
            Actions.parallel(
                Actions.fadeIn(0.3f),
                Actions.scaleTo(1f, 1f, 0.3f)
            )
        );

        Core.scene.add(root);
    }
}