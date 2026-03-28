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
            new PopDialog(14f, new Color(0f, 0f, 0f, 0.9f))
        );

        card.margin(12);
        card.defaults().pad(4);

        // ===== TITLE =====
        card.add("[lightgray]==== Info Mod ==== []")
            .center()
            .row();

        card.add("[accent]Init Prosesing Core[]")
            .center()
            .padBottom(6)
            .row();

        // ===== INFO SECTION =====
        Table info = new Table();
        info.left();
        info.defaults().pad(5);

        info.add("[gray]Status:[]");
        info.add("[green]Active[]").row();

        info.add("[gray]Type:[]");
        info.add("[accent]Java Mod[]").row();

        info.add("[gray]Purpose:[]");
        info.add("[white]Learning Modding Java[]").row();

        card.add(info)
            .left()
            .growX()
            .row();

        // ===== SEPARATOR =====
        card.add()
            .height(2)
            .growX()
            .padTop(6)
            .padBottom(6)
            .row();

        // ===== LINKS =====
        Table links = new Table();
        links.left();
        links.defaults().pad(2).left();

        links.add("[gray]Github:[]").row();

        links.labelWrap("[sky]github.com/IsekaiRid/Process-Line[]")
            .width(280)
            .row();

        links.add("[gray]YouTube:[]")
            .padTop(4)
            .row();

        links.labelWrap("[orange]youtube.com/@ridhwanrplwibu[]")
            .width(280)
            .row();

        card.add(links)
            .left()
            .growX()
            .row();

        // ===== BUTTON =====
        card.button("[accent]Close[]", () -> {

            // animasi keluar
            card.actions(
                Actions.parallel(
                    Actions.fadeOut(0.25f),
                    Actions.scaleTo(0.9f, 0.9f, 0.25f)
                ),
                Actions.run(root::remove)
            );

        })
        .size(120, 45)
        .padTop(10)
        .center();

        // ===== ROOT ADD =====
        root.add(card)
            .width(340)
            .pad(10);

        // ===== ANIMASI MASUK =====
        card.actions(
            Actions.alpha(0f),
            Actions.scaleTo(0.8f, 0.8f),
            Actions.parallel(
                Actions.fadeIn(0.3f),
                Actions.scaleTo(1f, 1f, 0.3f)
            )
        );

        Core.scene.add(root);
    }
}