package net.joe.mayview.screen.custom;

import net.joe.mayview.data.ModDataComponents;
import net.joe.mayview.item.ModItems;
import net.joe.mayview.screen.ModMenuTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.core.component.DataComponentPatch;
import org.jetbrains.annotations.NotNull;

public class PiggyBankMenu extends AbstractContainerMenu {
    private static final int HOTBAR_SLOT_COUNT             = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT    = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT   = PLAYER_INVENTORY_ROW_COUNT * PLAYER_INVENTORY_COLUMN_COUNT;
    private static final int VANILLA_SLOT_COUNT            = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX      = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT       = 4;

    private final SimpleContainer coinContainer;
    private final ItemStack    piggyBankStack;
    private final Player       owner;

    public PiggyBankMenu(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(windowId, playerInventory, new SimpleContainer(TE_INVENTORY_SLOT_COUNT), ItemStack.EMPTY);
    }

    public PiggyBankMenu(int syncId,
                         @NotNull Inventory playerInventory,
                         @NotNull SimpleContainer inventory,
                         @NotNull ItemStack stack) {
        super(ModMenuTypes.PIGGY_BANK_MENU.get(), syncId);
        this.coinContainer  = inventory;
        this.piggyBankStack = stack;
        this.owner          = playerInventory.player;

        for (int col = 0; col < HOTBAR_SLOT_COUNT; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 98));
        }
        for (int row = 0; row < PLAYER_INVENTORY_ROW_COUNT; row++) {
            for (int col = 0; col < PLAYER_INVENTORY_COLUMN_COUNT; col++) {
                int index = col + row * PLAYER_INVENTORY_COLUMN_COUNT + HOTBAR_SLOT_COUNT;
                this.addSlot(new Slot(playerInventory, index, 8 + col * 18, 40 + row * 18));
            }
        }

        this.addSlot(new DynamicRestrictedSlot(inventory, 0,  53, 12, ModItems.COPPER_COIN.get(),  99) {
            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                super.onTake(player, stack);
                PiggyBankMenu.this.compressCoins();
                PiggyBankMenu.this.writeDataToStack();
            }

            @Override
            public void set(@NotNull ItemStack stack) {
                super.set(stack);
                PiggyBankMenu.this.compressCoins();
                PiggyBankMenu.this.writeDataToStack();
            }
        });
        this.addSlot(new DynamicRestrictedSlot(inventory, 1,  71, 12, ModItems.IRON_COIN.get(),    99) {
            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                super.onTake(player, stack);
                PiggyBankMenu.this.compressCoins();
                PiggyBankMenu.this.writeDataToStack();
            }

            @Override
            public void set(@NotNull ItemStack stack) {
                super.set(stack);
                PiggyBankMenu.this.compressCoins();
                PiggyBankMenu.this.writeDataToStack();
            }
        });
        this.addSlot(new DynamicRestrictedSlot(inventory, 2,  89, 12, ModItems.GOLD_COIN.get(),    99) {
            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                super.onTake(player, stack);
                PiggyBankMenu.this.compressCoins();
                PiggyBankMenu.this.writeDataToStack();
            }

            @Override
            public void set(@NotNull ItemStack stack) {
                super.set(stack);
                PiggyBankMenu.this.compressCoins();
                PiggyBankMenu.this.writeDataToStack();
            }
        });
        this.addSlot(new DynamicRestrictedSlot(inventory, 3, 107, 12, ModItems.DIAMOND_COIN.get(), 99) {
            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                super.onTake(player, stack);
                PiggyBankMenu.this.compressCoins();
                PiggyBankMenu.this.writeDataToStack();
            }

            @Override
            public void set(@NotNull ItemStack stack) {
                super.set(stack);
                PiggyBankMenu.this.compressCoins();
                PiggyBankMenu.this.writeDataToStack();
            }
        });
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        if (player != owner) {
            return false;
        }
        for (ItemStack invStack : player.getInventory().items) {
            if (invStack == piggyBankStack) {
                return true;
            }
        }
        for (ItemStack off : player.getInventory().offhand) {
            if (off == piggyBankStack) {
                return true;
            }
        }
        return false;
    }

    private void compressCoins() {
        final int MAX_PER_SLOT = 99;
        int[] counts = new int[4];

        for (int i = 0; i < coinContainer.getContainerSize(); i++) {
            ItemStack stack = coinContainer.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.COPPER_COIN.get())    counts[0] += stack.getCount();
                else if (stack.getItem() == ModItems.IRON_COIN.get())    counts[1] += stack.getCount();
                else if (stack.getItem() == ModItems.GOLD_COIN.get())    counts[2] += stack.getCount();
                else if (stack.getItem() == ModItems.DIAMOND_COIN.get()) counts[3] += stack.getCount();
            }
        }

        for (int tier = 0; tier < 3; tier++) {
            int convertible = counts[tier] / 8;
            int spaceAtNext = MAX_PER_SLOT - counts[tier + 1];
            int actual = Math.min(convertible, spaceAtNext);
            counts[tier]     -= actual * 8;
            counts[tier + 1] += actual;
        }

        for (int i = 0; i < coinContainer.getContainerSize(); i++) {
            coinContainer.setItem(i, ItemStack.EMPTY);
        }
        if (counts[0] > 0) coinContainer.setItem(0, new ItemStack(ModItems.COPPER_COIN.get(),  counts[0]));
        if (counts[1] > 0) coinContainer.setItem(1, new ItemStack(ModItems.IRON_COIN.get(),    counts[1]));
        if (counts[2] > 0) coinContainer.setItem(2, new ItemStack(ModItems.GOLD_COIN.get(),    counts[2]));
        if (counts[3] > 0) coinContainer.setItem(3, new ItemStack(ModItems.DIAMOND_COIN.get(), counts[3]));
    }

    private void writeDataToStack() {
        if (piggyBankStack.isEmpty()) {
            return;
        }
        if (!(owner instanceof ServerPlayer serverOwner)) {
            return;
        }
        CompoundTag tag = new CompoundTag();
        HolderLookup.Provider regs = owner.level().registryAccess();
        ContainerHelper.saveAllItems(tag, coinContainer.getItems(), regs);
        piggyBankStack.applyComponentsAndValidate(
                DataComponentPatch.builder()
                        .set(ModDataComponents.COIN_CONTAINER.get(), tag)
                        .build()
        );
        serverOwner.inventoryMenu.broadcastChanges();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack  = sourceSlot.getItem();
        ItemStack copyOfSource = sourceStack.copy();

        if (index < VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(
                    sourceStack,
                    TE_INVENTORY_FIRST_SLOT_INDEX,
                    TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT,
                    false
            )) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(
                    sourceStack,
                    VANILLA_FIRST_SLOT_INDEX,
                    VANILLA_SLOT_COUNT,
                    false
            )) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);

        compressCoins();
        writeDataToStack();
        return copyOfSource;
    }

    @Override
    public void removed(@NotNull Player playerIn) {
        super.removed(playerIn);
        if (!piggyBankStack.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            HolderLookup.Provider regs = playerIn.level().registryAccess();
            ContainerHelper.saveAllItems(tag, coinContainer.getItems(), regs);
            piggyBankStack.applyComponentsAndValidate(
                    DataComponentPatch.builder()
                            .set(ModDataComponents.COIN_CONTAINER.get(), tag)
                            .build()
            );
        }
    }
}
