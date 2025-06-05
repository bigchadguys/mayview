package net.joe.mayview;

import net.joe.mayview.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.Optional;

import static net.joe.mayview.Mayview.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class TraderSpawnFix {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof WanderingTrader trader)) return;
        if (!(event.getLevel() instanceof ServerLevel)) return;

        MerchantOffers offers = trader.getOffers();

        for (int i = 0; i < offers.size(); i++) {
            MerchantOffer offer = offers.get(i);

            ItemStack buyAStack = offer.getBaseCostA();
            ItemStack buyBStack = offer.getCostB();
            ItemStack sellStack = offer.getResult().copy();

            if (sellStack.getItem() == Items.EMERALD) {
                sellStack = new ItemStack(ModItems.COPPER_COIN.get(), sellStack.getCount() * 4);
            }

            ItemCost costA;
            if (buyAStack.getItem() == Items.EMERALD) {
                int count = Math.min(buyAStack.getCount() * 4, 8);
                costA = new ItemCost(ModItems.IRON_COIN.get(), count);
            } else {
                costA = new ItemCost(buyAStack.getItem(), buyAStack.getCount());
            }

            Optional<ItemCost> costB;
            if (!buyBStack.isEmpty() && buyBStack.getItem() == Items.EMERALD) {
                int count = Math.min(buyBStack.getCount() * 4, 8);
                costB = Optional.of(new ItemCost(ModItems.IRON_COIN.get(), count));
            } else if (!buyBStack.isEmpty()) {
                costB = Optional.of(new ItemCost(buyBStack.getItem(), buyBStack.getCount()));
            } else {
                costB = Optional.empty();
            }

            MerchantOffer newOffer = new MerchantOffer(
                    costA,
                    costB,
                    sellStack,
                    offer.getUses(),
                    offer.getMaxUses(),
                    offer.getXp(),
                    offer.getPriceMultiplier()
            );

            offers.set(i, newOffer);
        }
    }
}
