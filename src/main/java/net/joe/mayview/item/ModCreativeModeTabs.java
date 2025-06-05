package net.joe.mayview.item;

import net.joe.mayview.Mayview;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Mayview.MOD_ID);

    public static final Supplier<CreativeModeTab> MAYVIEW =
            CREATIVE_MODE_TABS.register("mayview_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.mayview.mayview_tab"))
                    .icon(() -> new ItemStack(ModItems.JOE_COIN.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.COPPER_COIN);
                        output.accept(ModItems.IRON_COIN);
                        output.accept(ModItems.GOLD_COIN);
                        output.accept(ModItems.DIAMOND_COIN);
                        output.accept(ModItems.JOE_COIN);
                        output.accept(ModItems.COIN_POUCH);
                        output.accept(ModItems.PIGGY_BANK);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

