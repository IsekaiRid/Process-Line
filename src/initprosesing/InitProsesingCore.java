package initprosesing;

import mindustry.mod.*;
import arc.util.*;
import initprosesing.core.CoreUI;

public class InitProsesingCore extends Mod {

    public InitProsesingCore(){
        Log.info("=== InitProsesingCore Loaded ===");
    }

    @Override
    public void loadContent(){
        Log.info("Loading InitProsesingCore content...");

        // panggil sistem core
        CoreUI.init();
    }
}