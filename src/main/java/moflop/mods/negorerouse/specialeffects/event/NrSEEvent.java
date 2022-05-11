package moflop.mods.negorerouse.specialeffects.event;

import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade.ComboSequence;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import moflop.mods.negorerouse.init.NrSEs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Cat
 * @updateDate 2020/02/13
 */
public class NrSEEvent {

    public NrSEEvent(){
        SlashBladeHooks.EventBus.register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    /** se:神谕 */
    @SubscribeEvent
    public void oracleUpdate(SlashBladeEvent.OnUpdateEvent event) {

        if(!SpecialEffects.isPlayer(event.entity)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

        switch (SpecialEffects.isEffective(player,event.blade, NrSEs.ORACLE)){
            /** 任何时候可触发 */
            case None:
                return;
            /** 未达到所需等级 */
            case NonEffective:
                break;
            /** 达到所需等级 */
            case Effective:
                break;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.LUCK,20 * 5,0));
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,20 * 1,0));

    }

    /** se:爆裂性 */
    @SubscribeEvent
    public void burstDriveUpdate(SlashBladeEvent.OnUpdateEvent event) {
        if (SpecialEffects.isPlayer(event.entity)) {
            EntityPlayer player = (EntityPlayer)event.entity;
            ItemStack blade = event.blade;
            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
            if (!ItemSlashBlade.IsBroken.get(tag)) {
                switch(SpecialEffects.isEffective(player, event.blade, NrSEs.BURST_DRIVE)) {
                    case None:
                        return;
                    case NonEffective:
                        return;
                    case Effective:
                        double d0 = player.getRNG().nextGaussian() * 0.02D;
                        double d1 = player.getRNG().nextGaussian() * 0.02D;
                        double d2 = player.getRNG().nextGaussian() * 0.02D;
                        double d3 = 10.0D;
                        event.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, player.posX + (double)(player.getRNG().nextFloat() * player.width * 2.0F) - (double)player.width - d0 * d3, player.posY, player.posZ + (double)(player.getRNG().nextFloat() * player.width * 2.0F) - (double)player.width - d2 * d3, d0, d1, d2);
                    default:
                        ComboSequence seq = ItemSlashBlade.getComboSequence(tag);
                        if (this.useBlade(seq)) {
                            PotionEffect haste = player.getActivePotionEffect(MobEffects.SPEED);
                            int check = haste != null ? (haste.getAmplifier() != 1 ? 3 : 4) : 2;
                            if (player.swingProgressInt == check) {
                                this.doAddAttack(event.blade, player, seq);
                            }
                        }
                }
            }
        }
    }
    public void doAddAttack(ItemStack stack, EntityPlayer player, ComboSequence setCombo) {
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
        World world = player.world;
        if (!ItemSlashBlade.ProudSoul.tryAdd(tag, -2, false)) {
            stack.setItemDamage(stack.getMaxDamage() + 1);
        } else {
            if (!world.isRemote) {
                float baseModif = ((ItemSlashBlade)stack.getItem()).getBaseAttackModifiers(tag);
                int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                float magicDamage = baseModif + (float)level;
                int rank = StylishRankManager.getStylishRank(player);
                if (5 <= rank) {
                    magicDamage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.5F + (float)level / 5.0F);
                }

                EntityDrive entityDrive = new EntityDrive(world, player, magicDamage, false, 90.0F - setCombo.swingDirection);
                if (entityDrive != null) {
                    entityDrive.setInitialSpeed(1.5F);
                    entityDrive.setLifeTime(10);
                    world.spawnEntity(entityDrive);
                }
            }

        }
    }

    /** se:负向神力 */
    @SubscribeEvent
    public void reversePowerUpdate(SlashBladeEvent.OnUpdateEvent event) {

        if(!SpecialEffects.isPlayer(event.entity)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

        switch (SpecialEffects.isEffective(player,event.blade, NrSEs.REVERSE_POWER)){
            /** 任何时候可触发 */
            case None:
                return;
            /** 未达到所需等级 */
            case NonEffective:
                return;
            /** 达到所需等级 */
            case Effective:
                break;
        }

        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 3, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 * 10, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20 * 7, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 2, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK,20 * 5,0));
        if (player.world.rand.nextFloat() <= 0.2F){
            player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH,20 * 1,0));
        }
    }

    /** se:绝对神力 */
    @SubscribeEvent
    public void absolutePowerUpdate(SlashBladeEvent.OnUpdateEvent event) {

        if(!SpecialEffects.isPlayer(event.entity)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

        switch (SpecialEffects.isEffective(player,event.blade, NrSEs.ABSOLUTE_POWER)){
            /** 任何时候可触发 */
            case None:
                return;
            /** 未达到所需等级 */
            case NonEffective:
                return;
            /** 达到所需等级 */
            case Effective:
                break;
        }

        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 5, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 10, 0));

    }

    /** se:回溯 */
    @SubscribeEvent
    public void back(LivingDeathEvent event) {
        if (event.getSource().getImmediateSource() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();
            if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSlashBlade){
                ItemStack blade = player.getHeldItem(EnumHand.MAIN_HAND);
                NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
                if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

                switch (SpecialEffects.isEffective(player,blade, NrSEs.BACK)){
                    /** 任何时候可触发 */
                    case None:
                        return;
                    /** 未达到所需等级 */
                    case NonEffective:
                        return;
                    /** 达到所需等级 */
                    case Effective:
                        break;
                }

                blade.setItemDamage(blade.getItemDamage() + 1);
            }
        }


    }

    private boolean useBlade(ComboSequence sequence){
        if(sequence.useScabbard) return false;
        if(sequence == ItemSlashBlade.ComboSequence.None) return false;
        if(sequence == ItemSlashBlade.ComboSequence.Noutou) return false;
        return true;
    }



}
