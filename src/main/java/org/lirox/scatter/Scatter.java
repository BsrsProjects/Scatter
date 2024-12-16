package org.lirox.scatter;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scatter implements ModInitializer {
    public final static String MOD_ID = "scatter";
    public final static Logger LOG = LoggerFactory.getLogger("Scatter");
    @Override
    public void onInitialize() {
        LOG.info("BALLS?!??!?!?!!???!?!?!?");
        Register.RegisterAll();
    }
}
