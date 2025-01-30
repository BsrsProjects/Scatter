package org.lirox.scatter.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lirox.scatter.Auxilium;
import org.lirox.scatter.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VenomfangItem extends SwordItem {
    private Random random = new Random();
    public VenomfangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        Auxilium.defaultBooleanNbt(stack, "potionInfuse", true);
        Auxilium.defaultBooleanNbt(stack, "calculateEffectTime", true);
        Auxilium.defaultIntNbt(stack, "uses", 0);
        Auxilium.defaultIntNbt(stack, "maxUses", 50);

        if (entity instanceof PlayerEntity user) {
            ItemStack mainHand = user.getStackInHand(Hand.MAIN_HAND);
            ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);

            if (mainHand.getItem().equals(Register.VENOMFANG) && (offHand.getItem().equals(Items.POTION) || offHand.getItem().equals(Register.LARGE_POTION))) {
                int uses = mainHand.getNbt().getInt("uses");
                int maxUses = mainHand.getNbt().getInt("maxUses");
                List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(mainHand);

                if (offHand.getItem().equals(Items.POTION)) {
                    List<StatusEffectInstance> potionEffects = Auxilium.calculatePotionDurationByAmplifier(Auxilium.getAllPotionEffects(stack), 20, 80);

                    if (Auxilium.comparePotionAmplifiers(effects, potionEffects)) {
                        if (uses < maxUses) Auxilium.incrementStackNbtClamp(stack, "uses", 10, 0, maxUses);
                        else return;
                    } else {
                        mainHand.getNbt().putInt("uses", 10);
                        PotionUtil.setCustomPotionEffects(mainHand, potionEffects);
                    }

                    user.giveItemStack(new ItemStack(Items.GLASS_BOTTLE, 1));
                    offHand.decrement(1);
                } else if (offHand.getItem().equals(Register.LARGE_POTION)) {
                    List<StatusEffectInstance> potionEffects = Auxilium.calculatePotionDurationByAmplifier(PotionUtil.getCustomPotionEffects(offHand), 20, 80);

                    int count = 0;
                    if (Auxilium.stackNbtHasKey(offHand, "uses")) count = offHand.getNbt().getInt("uses");

                    if (count == 0) return;

                    int required = maxUses-uses;
                    int used = Math.min(count, required);
                    int remaining = count-used;

                    if (Auxilium.comparePotionAmplifiers(effects, potionEffects)) {
                        if (required > 0 && used > 0) {
                            Auxilium.incrementStackNbt(stack, "uses", used);
                            offHand.getNbt().putInt("uses", remaining);
                        }
                        else return;
                    } else {
                        mainHand.getNbt().putInt("uses", used);
                        PotionUtil.setCustomPotionEffects(mainHand, potionEffects);
                        offHand.getNbt().putInt("uses", remaining);
                    }
                } else return;
                user.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 0.2f, 1);
            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.hasNbt() && stack.getNbt().contains("CustomPotionEffects") && stack.getNbt().contains("uses") && stack.getNbt().getInt("uses") > 0) {
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(stack);
            for (StatusEffectInstance effect : effects) {
                if (!target.hasStatusEffect(effect.getEffectType())) {
                    target.addStatusEffect(effect);
                    Auxilium.incrementStackNbt(stack, "uses", -1);
                    if (stack.getNbt().getInt("uses") == 0) stack.getNbt().remove("CustomPotionEffects");
//                    for (int i = 0; i < 15; i++) {
//                        target.getWorld().addParticle(ParticleTypes.EFFECT, target.getX(), target.getY(), target.getZ(), random.nextDouble()-0.5, random.nextDouble()-0.5, random.nextDouble()-0.5);
//                    }
                    // TODO: Make some indicator that effects were successfully applied
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.addAll(Auxilium.generateDescriptionTooltip("venomfang", 3));
        tooltip.add(Auxilium.generateUsesTooltip(stack));
        tooltip.addAll(Auxilium.generateEffectsTooltip(PotionUtil.getCustomPotionEffects(stack)));
    }
}
