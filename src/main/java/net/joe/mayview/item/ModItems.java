package net.joe.mayview.item;

import net.joe.mayview.Mayview;
import net.joe.mayview.item.custom.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Mayview.MOD_ID);
    public static final DeferredItem<Item> COPPER_COIN = ITEMS.registerItem("copper_coin", Item::new, new Item.Properties().stacksTo(99));
    public static final DeferredItem<Item> IRON_COIN = ITEMS.registerItem("iron_coin", Item::new, new Item.Properties().stacksTo(99));
    public static final DeferredItem<Item> GOLD_COIN = ITEMS.registerItem("gold_coin", Item::new, new Item.Properties().stacksTo(99));
    public static final DeferredItem<Item> DIAMOND_COIN = ITEMS.registerItem("diamond_coin", Item::new, new Item.Properties().stacksTo(99));
    public static final DeferredItem<Item> JOE_COIN = ITEMS.registerSimpleItem("joe_coin");
    public static final DeferredItem<Item> COIN_POUCH = ITEMS.registerItem("coin_pouch", CoinPouchItem::new, new Item.Properties());
    public static final DeferredItem<Item> PIGGY_BANK = ITEMS.registerItem("piggy_bank", PiggyBankItem::new, new Item.Properties().stacksTo(1));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
