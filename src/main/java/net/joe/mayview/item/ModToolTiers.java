package net.joe.mayview.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {

    public static final Tier HONEY_DIAMOND = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, 8.0f, 3.0f, 10, ()-> Ingredient.of(ModItems.HONEY_DIAMOND.get()));

    public static final Tier SPECTRITE = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 8.0f, 3.0f, 10, ()-> Ingredient.of(ModItems.SPECTRITE_INGOT.get()));

    public static final Tier ECOLITE = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 8.0f, 3.0f, 10, ()-> Ingredient.of(ModItems.ECOLITE_INGOT.get()));

    public static final Tier TECTRITE = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 8.0f, 3.0f, 10, ()-> Ingredient.of(ModItems.TECTRITE_INGOT.get()));

    public static final Tier HYDRITE = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 8.0f, 3.0f, 10, ()-> Ingredient.of(ModItems.HYDRITE_INGOT.get()));

    public static final Tier PETRAFITE = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 8.0f, 3.0f, 10, ()-> Ingredient.of(ModItems.PETRAFITE_INGOT.get()));


}
