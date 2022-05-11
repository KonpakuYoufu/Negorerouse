package moflop.mods.negorerouse.common;

import moflop.mods.negorerouse.init.NrBlades;
import moflop.mods.negorerouse.init.NrItems;
import moflop.mods.negorerouse.init.NrSEs;
import moflop.mods.negorerouse.item.ItemSoul;
import moflop.mods.negorerouse.item.event.SoulDropsEvent;
import moflop.mods.negorerouse.specialeffects.event.NrSEEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Moflop
 * @updateDate 2020/02/12
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event){
        new NrItems();
        new NrSEs();
        new NrBlades();
    }

    public void init(FMLInitializationEvent event){
        new NrSEEvent();
        new SoulDropsEvent();
        ItemSoul.craft();
    }

    public void postInit(FMLPostInitializationEvent event){

    }
}
