package initprosesing.core;

import arc.Events;
import arc.util.*;
import mindustry.game.EventType.*;

public class CoreHUD {

    public static void init(){

        Events.on(ClientLoadEvent.class, e -> {

            Log.info("HUD INIT DIPANGGIL");

            // panggil popup
            CorePopup.show();
        });
    }
}