package moflop.mods.negorerouse.init;

import mods.flammpfeil.slashblade.SlashBlade;
import moflop.mods.negorerouse.creativetab.NrTabs;
import moflop.mods.negorerouse.named.*;
import moflop.mods.negorerouse.named.item.ItemNrSlashBlade;
import moflop.mods.negorerouse.named.item.ItemNrSlashBladeTLS;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

/**
 * @author Cat
 * @updateDate 2020/02/14
 */
public class NrBlades {
    public static Item NR_BLADE;

    public NrBlades(){
        if (Loader.isModLoaded("lastsmith")) {
            NR_BLADE = new ItemNrSlashBladeTLS(Item.ToolMaterial.IRON, 4.0f,"nrSlashBlade")
                    .setMaxDamage(40)
                    .setCreativeTab(NrTabs.Nr_Item);
        } else {
            NR_BLADE = new ItemNrSlashBlade(Item.ToolMaterial.IRON, 4.0f,"nrSlashBlade")
                    .setMaxDamage(40)
                    .setCreativeTab(NrTabs.Nr_Item);
        }


        loadBlade();
    }

    public void loadBlade(){
        loadBlade(new Artemis());
        loadBlade(new Hercules());
        loadBlade(new Nier());
        loadBlade(new Chronos());
        loadBlade(new Erebus());
        loadBlade(new ChronosN());
        loadBlade(new Protogenoi());
    }

    public void loadBlade(Object blade) {
        SlashBlade.InitEventBus.register(blade);
    }

}
