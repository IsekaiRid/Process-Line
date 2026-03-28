package initprosesing;

import mindustry.mod.*;
import arc.util.*;
import initprosesing.core.CoreMenuBackground;
import initprosesing.core.CoreHUD;

public class InitProsesingCore extends Mod {

    public InitProsesingCore(){
        Log.info("=== InitProsesingCore Loaded ===");
    }

    @Override
    public void loadContent(){
    CoreMenuBackground.init();
    CoreHUD.init();
    }
}