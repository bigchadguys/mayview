package net.joe.mayview.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import net.joe.mayview.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.IRON_COIN.get())
                .requires(ModItems.COPPER_COIN.get(), 8)
                .unlockedBy("has_copper_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_COIN.get()))
                .save(recipeOutput, "iron_from_copper");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COPPER_COIN.get(), 8)
                .requires(ModItems.IRON_COIN.get())
                .unlockedBy("has_iron_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRON_COIN.get()))
                .save(recipeOutput, "copper_from_iron");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GOLD_COIN.get())
                .requires(ModItems.IRON_COIN.get(), 8)
                .unlockedBy("has_iron_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRON_COIN.get()))
                .save(recipeOutput, "gold_from_iron");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.IRON_COIN.get(), 8)
                .requires(ModItems.GOLD_COIN.get())
                .unlockedBy("has_gold_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLD_COIN.get()))
                .save(recipeOutput, "iron_from_gold");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DIAMOND_COIN.get())
                .requires(ModItems.GOLD_COIN.get(), 8)
                .unlockedBy("has_gold_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLD_COIN.get()))
                .save(recipeOutput, "diamond_from_gold");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GOLD_COIN.get(), 8)
                .requires(ModItems.DIAMOND_COIN.get())
                .unlockedBy("has_diamond_coin", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DIAMOND_COIN.get()))
                .save(recipeOutput, "gold_from_diamond");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PIGGY_BANK.get())
                .pattern("PPP")
                .pattern("P P")
                .pattern("PPP")
                .define('P', Items.PORKCHOP)
                .unlockedBy("has_porkchop", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PORKCHOP))
                .save(recipeOutput, "piggy_bank_from_pork");
    }
}
