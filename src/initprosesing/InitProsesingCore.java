package initprosesing;

import mindustry.mod.*;
import arc.util.*;
// import initprosesing.core.CoreUI;
import initprosesing.core.CoreHUD;

public class InitProsesingCore extends Mod {

    public InitProsesingCore(){
        Log.info("=== InitProsesingCore Loaded ===");
    }

    @Override
    public void loadContent(){
    Log.info("Loading InitProsesingCore content...");
    CoreHUD.init();
    }
}