package moflop.mods.negorerouse.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @author Moflop
 * @updateDate 2020/02/12
 */
public class NrItem extends Item {
    public NrItem(String name){
        super();
        this.setRegistryName(name);
        ForgeRegistries.ITEMS.register(this);
    }
}
