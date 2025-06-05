package net.joe.mayview;

import net.joe.mayview.data.ModDataComponents;
import net.joe.mayview.item.ModCreativeModeTabs;
import net.joe.mayview.item.ModItems;
import net.joe.mayview.loot.AddCoinModifier;
import net.joe.mayview.recipe.ModRecipes;
import net.joe.mayview.screen.ModMenuTypes;
import net.joe.mayview.screen.custom.*;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(Mayview.MOD_ID)
public class Mayview {
    public static final String MOD_ID = "mayview";

    public Mayview(IEventBus modEventBus) {
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        AddCoinModifier.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modEventBus);
        ModDataComponents.register(modEventBus);
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.PIGGY_BANK_MENU.get(), PiggyBankScreen::new);
        }
    }
}