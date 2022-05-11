package moflop.mods.negorerouse.creativetab;

import moflop.mods.negorerouse.init.NrItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Moflop
 * @updateDate 2020/02/12
 */
public class NrTabs {
    public static final CreativeTabs Nr_Item = new CreativeTabs("nrItem")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(NrItems.ARITEMIS);
        }
    };
}
