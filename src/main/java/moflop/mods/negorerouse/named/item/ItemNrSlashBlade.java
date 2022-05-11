package moflop.mods.negorerouse.named.item;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.TagPropertyAccessor;
import mods.flammpfeil.slashblade.specialattack.IJustSpecialAttack;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.EnumSet;
import java.util.List;

import static moflop.mods.negorerouse.utils.BladeUtils.NrNamedBlades;
import static moflop.mods.negorerouse.utils.BladeUtils.getCustomBlade;
import static moflop.mods.negorerouse.utils.ItemUtils.NR_BLADE;

/**
 * @author Cat,AbbyQAQ,Moflop,520
 * @updateDate 2020/02/13
 */
public class ItemNrSlashBlade extends ItemSlashBladeNamed {
    public ItemNrSlashBlade(ToolMaterial par2EnumToolMaterial, float baseAttackModifiers,String name) {
        super(par2EnumToolMaterial, baseAttackModifiers);
        this.setUnlocalizedName("moflop.negorerouse." + name);
        this.setRegistryName(name);
        ForgeRegistries.ITEMS.register(this);
        NR_BLADE.add(this);
    }

    public static TagPropertyAccessor.TagPropertyBoolean isInCreativeTab = new TagPropertyAccessor.TagPropertyBoolean("isInCreativeTab");
    public static TagPropertyAccessor.TagPropertyBoolean isNrBlade = new TagPropertyAccessor.TagPropertyBoolean("isNrBlade{");

    /** 显示神刀 */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformationSwordClass(ItemStack par1ItemStack,
                                         EntityPlayer par2EntityPlayer, List par3List, boolean par4) {

        EnumSet<SwordType> swordType = getSwordType(par1ItemStack);
        NBTTagCompound tag = getItemTagCompound(par1ItemStack);

        if(swordType.contains(SwordType.Enchanted)){
            if(swordType.contains(SwordType.Bewitched)){
                if (isNrBlade.get(tag)){
                    par3List.add(String.format("§6%s", I18n.format("moflop.info.negoreRouse")));
                } else if(tag.hasUniqueId("Owner")) {
                    par3List.add(String.format("§6%s", I18n.format("flammpfeil.swaepon.info.bewitched")));
                } else {
                    par3List.add(String.format("§5%s", I18n.format("flammpfeil.swaepon.info.bewitched")));
                }
            }else{
                par3List.add(String.format("§3%s", I18n.format("flammpfeil.swaepon.info.magic")));
            }
        }else{
            par3List.add(String.format("§8%s", I18n.format("flammpfeil.swaepon.info.noname")));
        }
    }

    /** 隐藏创造栏的SA */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformationSpecialAttack(ItemStack par1ItemStack,
                                            EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        EnumSet<SwordType> swordType = getSwordType(par1ItemStack);

        if(swordType.contains(SwordType.Bewitched)){
            NBTTagCompound tag = getItemTagCompound(par1ItemStack);

            String key = "flammpfeil.slashblade.specialattack." + getSpecialAttack(par1ItemStack).toString();

            if (isInCreativeTab.get(tag)){
                par3List.add("SA:???");
            }else{
                par3List.add(String.format("SA:%s",  I18n.format(key)));
            }
        }
    }

    /** 隐藏创造栏的伤害 */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformationMaxAttack(ItemStack par1ItemStack,
                                        EntityPlayer par2EntityPlayer, List par3List, boolean par4) {

        NBTTagCompound tag = getItemTagCompound(par1ItemStack);
        float repair = RepairCount.get(tag);
        EnumSet<SwordType> swordType = getSwordType(par1ItemStack);

        par3List.add("");
        par3List.add("§4RankAttackDamage");
        String header;
        String template;

        if(swordType.contains(SwordType.FiercerEdge)){
            header = "§6B-A§r/§4S-SSS§r/§5Limit";
        }else{
            header = "§6B-SS§r/§4SSS§r/§5Limit";
        }

        if (isInCreativeTab.get(tag)){
            template = "§6???§r/§4???§r/§5???";
        }else{
            template = "§6+%.1f§r/§4+%.1f§r/§5+%.1f";
        }


        float baseModif = getBaseAttackModifiers(tag);

        float maxBonus = RefineBase + repair;
        float level = par2EntityPlayer.experienceLevel;
        float ba = baseModif;
        float sss = (baseModif + Math.min(maxBonus,level));

        par3List.add(header);
        par3List.add(String.format(template,ba , sss , (baseModif + maxBonus)));

    }

    /** 关闭创造栏SA */
    public void doChargeAttack(ItemStack stack, EntityPlayer par3EntityPlayer,boolean isJust){
        NBTTagCompound tag = getItemTagCompound(stack);
        if (isInCreativeTab.get(tag)) {
            return;
        }

        SpecialAttackBase sa = getSpecialAttack(stack);
        if(isJust && sa instanceof IJustSpecialAttack){
            ((IJustSpecialAttack)sa).doJustSpacialAttack(stack,par3EntityPlayer);
        }else {
            sa.doSpacialAttack(stack, par3EntityPlayer);
        }


        IsCharged.set(tag, true);

    }

    /** 防止拔刀剑重复注册 */
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (this.isInCreativeTab(tab)) {
            for(String bladename : NrNamedBlades){
                ItemStack blade = getCustomBlade(bladename);
                NBTTagCompound tag = getItemTagCompound(blade);
                BaseAttackModifier.set(tag,0.0F);
                isInCreativeTab.set(tag,true);
                if(blade.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                    blade.setItemDamage(0);
                }
                if(!blade.isEmpty()) {
                    subItems.add(blade);
                }
            }
        }
    }
}
