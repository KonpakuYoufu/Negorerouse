package moflop.mods.negorerouse.item.event;

import moflop.mods.negorerouse.init.NrItems;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Cat
 * @updateDate 2020/02/14
 */
public class SoulDropsEvent {

    public SoulDropsEvent(){
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onPassiveDrop(LivingDropsEvent event) {
        if (event.getSource().getDamageType().equals("player") && event.getEntityLiving().world.rand.nextFloat() < 0.2F) {
            if (event.getEntityLiving() instanceof EntityEnderman) {
                event.getEntityLiving().dropItem(NrItems.ARITEMIS, 2);
            }

            if (event.getEntityLiving() instanceof EntityBlaze) {
                event.getEntityLiving().dropItem(NrItems.HERCULES, 1);
            }
        }

    }

}
