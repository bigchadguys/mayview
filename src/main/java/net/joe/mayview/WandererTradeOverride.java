package net.joe.mayview;

import net.joe.mayview.item.ModItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;
import java.util.Optional;

import static net.joe.mayview.Mayview.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class WandererTradeOverride {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onWandererTrades(WandererTradesEvent event) {
        var copperCoin = ModItems.COPPER_COIN.get();
        var ironCoin   = ModItems.IRON_COIN.get();
        var goldCoin   = ModItems.GOLD_COIN.get();

        List<VillagerTrades.ItemListing> generic = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rare    = event.getRareTrades();

        generic.replaceAll(listing -> (trader, random) -> {
            MerchantOffer originalOffer = listing.getOffer(trader, random);
            if (originalOffer == null) return null;

            ItemStack resultStack = originalOffer.getResult().copy();
            ItemCost baseCost = originalOffer.getItemCostA();
            Optional<ItemCost> secondaryCost = originalOffer.getItemCostB();

            if (resultStack.getItem() == Items.EMERALD) {
                resultStack = new ItemStack(copperCoin, resultStack.getCount() * 4);
            }

            {
                ItemStack baseStack = baseCost.itemStack();
                if (baseStack.getItem() == Items.EMERALD) {
                    int emeraldCount = baseStack.getCount();
                    int ironCount = emeraldCount * 4;
                    if (ironCount > 8) {
                        int goldCount = (ironCount + 7) / 8;
                        baseCost = new ItemCost(goldCoin, goldCount);
                    } else {
                        baseCost = new ItemCost(ironCoin, ironCount);
                    }
                }
            }

            return new MerchantOffer(
                    baseCost,
                    secondaryCost,
                    resultStack,
                    originalOffer.getUses(),
                    originalOffer.getMaxUses(),
                    originalOffer.getXp(),
                    originalOffer.getPriceMultiplier()
            );
        });

        rare.replaceAll(listing -> (trader, random) -> {
            MerchantOffer originalOffer = listing.getOffer(trader, random);
            if (originalOffer == null) return null;

            ItemStack resultStack = originalOffer.getResult().copy();
            ItemCost baseCost = originalOffer.getItemCostA();
            Optional<ItemCost> secondaryCost = originalOffer.getItemCostB();

            if (resultStack.getItem() == Items.EMERALD) {
                resultStack = new ItemStack(copperCoin, resultStack.getCount() * 4);
            }

            {
                ItemStack baseStack = baseCost.itemStack();
                if (baseStack.getItem() == Items.EMERALD) {
                    int emeraldCount = baseStack.getCount();
                    int ironCount = emeraldCount * 4;
                    if (ironCount > 8) {
                        int goldCount = (ironCount + 7) / 8;
                        baseCost = new ItemCost(goldCoin, goldCount);
                    } else {
                        baseCost = new ItemCost(ironCoin, ironCount);
                    }
                }
            }

            return new MerchantOffer(
                    baseCost,
                    secondaryCost,
                    resultStack,
                    originalOffer.getUses(),
                    originalOffer.getMaxUses(),
                    originalOffer.getXp(),
                    originalOffer.getPriceMultiplier()
            );
        });
    }
}
