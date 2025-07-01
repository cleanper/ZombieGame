package com.aljun.zombiegame.work.eventsubscriber.debug;

import com.aljun.zombiegame.work.ZombieGame;
import com.aljun.zombiegame.work.datamanager.datamanager.ItemStackDataManager;
import com.aljun.zombiegame.work.keyset.KeySet;
import com.aljun.zombiegame.work.zombie.goal.zombiesets.ZombieMainGoal;
import com.aljun.zombiegame.work.zombie.load.ZombieUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DEBUG {

    public static final KeySet<Boolean> DEBUG_THROW_ITEM = new KeySet<>("debug_throw_item", false);
    public static final KeySet<Boolean> DEBUG_KILLER = new KeySet<>("debug_item", false);
    public static final KeySet<Boolean> DEBUG_INFO = new KeySet<>("debug_info", false);

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        if (ZombieGame.DEBUG_MODE) {
            if (event.getEntity().level().isClientSide()) {
                return;
            }

            if (event.getEntity() == null) {
                return;
            }

            if (ItemStackDataManager.getOrDefault(event.getEntity().getMainHandItem(), DEBUG_THROW_ITEM)) {
                LivingEntity entity = (LivingEntity) event.getTarget();
                throwItem(entity, EquipmentSlot.HEAD);
                throwItem(entity, EquipmentSlot.CHEST);
                throwItem(entity, EquipmentSlot.LEGS);
                throwItem(entity, EquipmentSlot.FEET);
                throwItem(entity, EquipmentSlot.MAINHAND);
                throwItem(entity, EquipmentSlot.OFFHAND);
            } else if (ItemStackDataManager.getOrDefault(event.getEntity().getMainHandItem(), DEBUG_KILLER)) {
                killed((LivingEntity) event.getTarget());
            } else if (ItemStackDataManager.getOrDefault(event.getEntity().getMainHandItem(), DEBUG_INFO)) {
                LivingEntity entity = (LivingEntity) event.getTarget();
                if (ZombieUtils.canBeLoaded(entity)) {
                    Zombie zombie = (Zombie) entity;
                    ZombieMainGoal goal = ZombieUtils.getOrLoadMainGoal(zombie);
                    event.getEntity().sendSystemMessage(
                            Component.translatable("message.zombiegame.entity_info", zombie.getDisplayName(),
                                    Component.literal(String.valueOf(zombie.getHealth())),
                                    Component.literal(goal == null ? " <none> " : goal.getName()), Component.literal(
                                            goal == null ? " <none> " : String.valueOf(goal.getZombieAttackDamage())),
                                    Component.literal(
                                            goal == null ? " <none> " : String.valueOf(goal.getZombieArmor())),
                                    Component.literal(
                                            goal == null ? " <none> " : String.valueOf(goal.getZombieSpeed())),
                                    zombie.getItemBySlot(EquipmentSlot.HEAD).getDisplayName(),
                                    zombie.getItemBySlot(EquipmentSlot.CHEST).getDisplayName(),
                                    zombie.getItemBySlot(EquipmentSlot.LEGS).getDisplayName(),
                                    zombie.getItemBySlot(EquipmentSlot.FEET).getDisplayName(),
                                    zombie.getItemBySlot(EquipmentSlot.MAINHAND).getDisplayName(),
                                    zombie.getItemBySlot(EquipmentSlot.OFFHAND).getDisplayName()));

                }
            }
        }
    }

    private static void throwItem(LivingEntity entity, EquipmentSlot slot) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (!stack.isEmpty()) {
            entity.spawnAtLocation(stack);
            entity.setItemSlot(slot, ItemStack.EMPTY);
        }
    }

    private static void killed(LivingEntity entity) {
        if (entity.isAlive()) {
            entity.kill();
        }
    }
}