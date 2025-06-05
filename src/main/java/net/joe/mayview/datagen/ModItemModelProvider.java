package net.joe.mayview.datagen;

import net.joe.mayview.Mayview;
import net.joe.mayview.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Mayview.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.COPPER_COIN.get());
        basicItem(ModItems.IRON_COIN.get());
        basicItem(ModItems.GOLD_COIN.get());
        basicItem(ModItems.DIAMOND_COIN.get());
        basicItem(ModItems.JOE_COIN.get());
        basicItem(ModItems.PIGGY_BANK.get());
        basicItem(ModItems.COIN_POUCH.get());
    }
}
