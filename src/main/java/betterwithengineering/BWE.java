package betterwithengineering;

import betterwithengineering.base.CompatLoader;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = BWE.MODID, name = BWE.NAME, version = BWE.VERSION, dependencies = BWE.DEPS, acceptedMinecraftVersions = "1.12,1.12.1,1.12.2")
@Mod.EventBusSubscriber(modid = BWE.MODID)
public class BWE {
    public static final String MODID = "betterwithengineering";
    public static final String NAME = "Better With Engineering";
    public static final String DEPS = "after:thermalexpansion;after:immersiveengineering;required-after:betterwithmods";

    public static final String VERSION = "1.2";
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        CompatLoader.construct();
        CompatLoader.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        CompatLoader.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CompatLoader.postInit(event);
    }

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            net.minecraftforge.common.config.ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }

    @Config(modid = MODID, name = MODID)
    public static class ConfigManager {

        public static class ImmersiveEngineering {

            public static class Steel {
                @Config.Name("Enabled")
                public boolean enabled = false;
                       
                @Config.Name("Soul Urn to Steel Ratio")
                public int ratio = 4;
            }

            public static class MechPower {
                @Config.Name("Enabled")
                @Config.Comment("Enabled Mechanical Power Feature")
                public boolean enabled = true;

                @Config.Name("RF Scale")
                @Config.Comment("Scale for Axle -> Kinetic Dynamo RF generation")
                public double rfScale = 0.5;
            }


            @Config.Comment("Rebalance IE Steel to be Soul Forged Steel")
            public Steel steel = new Steel();

            @Config.Comment("Allow IE Windmill and Waterwheel to output to wooden axles")
            public MechPower mechPower = new MechPower();

            @Config.Comment("Force IE Hemp to drop BWM Hemp items")
            public boolean overrideIndustrialHempDrops = true;
          
               
           @Config.Comment("Add Treated Wood IE Siding, Moulding and Corners")
           public static boolean addTreatedWood= true;
        }

        @Config.Name("immersiveengineering")
        public static ImmersiveEngineering immersiveEngineering = new ImmersiveEngineering();

        public static class ThermalExpansion {

            @Config.Name("Hardcore Induction Smelter")
            @Config.Comment("Change Recipes for the Induction Smelter to be harder ")
            public boolean hardcoreInductionSmelter = false;

        }



        @Config.Name("thermalexpansion")
        public static ThermalExpansion thermalExpansion = new ThermalExpansion();


        public static class ExNihilo {
            @Config.Name("Support Mech Power")
            @Config.Comment("Allow BWM axles to power Ex Nihilo machines")
            public boolean supportMechPower = true;

            @Config.Name("Speed of Rotation")
            @Config.Comment("Speed of rotation when a BWM axle is used in an Ex Nihilo machine")
            public int rotationSpeed = 10;
        }
        @Config.Name("exnihilocreatio")
        public static ExNihilo exNihilo = new ExNihilo();
    }
}
