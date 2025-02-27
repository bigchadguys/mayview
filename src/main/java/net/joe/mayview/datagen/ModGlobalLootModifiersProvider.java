package net.joe.mayview.datagen;

import net.joe.mayview.Mayview;
import net.joe.mayview.item.ModItems;
import net.joe.mayview.loot.AddCoinModifier;
import net.joe.mayview.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {

    public ModGlobalLootModifiersProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Mayview.MOD_ID);
    }

    @Override
    protected void start() {
        List<String> dirt_blocks = List.of(
                "dirt", "sand", "gravel", "suspicious_sand", "suspicious_gravel", "grass_block", "podzol", "rooted_dirt", "dirt_path", "mycelium", "farmland", "coarse_dirt"
        );

        List<String> wood_blocks = List.of(
                "oak_log", "birch_log", "spruce_log", "jungle_log", "acacia_log", "dark_oak_log", "mangrove_log", "cherry_log"
        );

        List<String> crops = List.of(
                "wheat", "beetroots", "carrots", "potatoes"
        );

        List<String> undead_entities = List.of(
                "zombie", "skeleton", "wither_skeleton", "drowned", "husk",
                "stray", "zombie_villager", "bogged"
        );

        wood_blocks.forEach(block -> add(
                block + "_drops_everoak",
                new AddItemModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("blocks/" + block)).build(),
                        LootItemRandomChanceCondition.randomChance(1f).build()
                }, ModItems.EVEROAK.get())
        ));

        crops.forEach(crop -> add(
                crop + "_drops_mush",
                new AddItemModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("blocks/" + crop)).build(),
                        LootItemRandomChanceCondition.randomChance(1f).build()
                }, ModItems.MUSH.get())
        ));

        dirt_blocks.forEach(block -> add(
                block + "_drops_quicksilver",
                new AddItemModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("blocks/" + block)).build(),
                        LootItemRandomChanceCondition.randomChance(1f).build()
                }, ModItems.QUICKSILVER.get())
        ));

        undead_entities.forEach(entity -> add(
                entity + "_drops_ectoplasm",
                new AddItemModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("entities/" + entity)).build(),
                        LootItemRandomChanceCondition.randomChance(1f).build()
                }, ModItems.ECTOPLASM.get())
        ));

        add("fishing_drops_mysterious_bubble", new AddItemModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(ResourceLocation.parse("gameplay/fishing/treasure")).build(),
                LootItemRandomChanceCondition.randomChance(1f).build()
        }, ModItems.MYSTERIOUS_BUBBLE.get()));

        add("add_coins_to_chest", new AddCoinModifier(new LootItemCondition[]{
                InvertedLootItemCondition.invert(LootTableIdCondition.builder(ResourceLocation.parse("blocks/emerald_ore"))).build(),
                InvertedLootItemCondition.invert(LootTableIdCondition.builder(ResourceLocation.parse("blocks/deepslate_emerald_ore"))).build(),
        }, ModItems.COPPER_COIN.get()));
    }
}
