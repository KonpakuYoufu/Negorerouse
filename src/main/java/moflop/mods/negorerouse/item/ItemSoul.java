package moflop.mods.negorerouse.item;

import moflop.mods.negorerouse.NegoreRouse;
import moflop.mods.negorerouse.creativetab.NrTabs;
import moflop.mods.negorerouse.init.NrItems;
import moflop.mods.negorerouse.utils.BladeUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

import static moflop.mods.negorerouse.utils.BladeUtils.getMcItemStack;
import static moflop.mods.negorerouse.utils.ItemUtils.NR_SOUL;

/**
 * @author Moflop,AbbyQAQ
 * @updateDate 2020/02/13
 */
public class ItemSoul extends NrItem {
    String name;

    public ItemSoul(String name){
        super("soul_" + name);
        this.setUnlocalizedName("nrSoul");
        this.name = name;
        this.setMaxStackSize(32);
        this.setCreativeTab(NrTabs.Nr_Item);
        NR_SOUL.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format(name));
        tooltip.add(I18n.format(name + ".SE"));
    }

    public static void craft(){
        ItemStack sphereSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1);
        ItemStack proudSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "proudsoul", 1);

        ResourceLocation chronos = new ResourceLocation(NegoreRouse.MODID,"soul_chronos");
        ResourceLocation erebus = new ResourceLocation(NegoreRouse.MODID,"soul_erebus");
        ResourceLocation soul_mix = new ResourceLocation(NegoreRouse.MODID,"soul_mix");

        GameRegistry.addShapedRecipe(chronos,chronos,
                new ItemStack(NrItems.CHRONOS), new Object[]{
                        "QWE",
                        "ASD",
                        "ZXC",
                        'Q', getMcItemStack("emerald_block"),
                        'W', sphereSoul,
                        'E', getMcItemStack("gold_ingot"),
                        'A', getMcItemStack("obsidian"),
                        'S', getMcItemStack("ender_eye"),
                        'D', getMcItemStack("golden_pickaxe"),
                        'Z', getMcItemStack("iron_block"),
                        'X', proudSoul,
                        'C', getMcItemStack("redstone_block")});

        GameRegistry.addShapedRecipe(erebus,erebus,
                new ItemStack(NrItems.EREBUS), new Object[]{
                        " W ",
                        "ASD",
                        " X ",
                        'W', sphereSoul,
                        'A', getMcItemStack("netherrack"),
                        'S', getMcItemStack("nether_brick_fence"),
                        'D', getMcItemStack("obsidian"),
                        'X', proudSoul});

        GameRegistry.addShapedRecipe(soul_mix,soul_mix,
                new ItemStack(NrItems.SOUL_MIX), new Object[]{
                        " XZ",
                        "DSD",
                        "ZD ",
                        'X', sphereSoul,
                        'Z', new ItemStack(NrItems.EREBUS),
                        'D', proudSoul,
                        'S', new ItemStack(NrItems.CHRONOS)});


    }

}
