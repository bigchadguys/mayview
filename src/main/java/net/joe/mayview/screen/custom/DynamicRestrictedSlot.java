package net.joe.mayview.screen.custom;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class DynamicRestrictedSlot extends Slot {
    private final Predicate<ItemStack> allowedPredicate;
    private final int maxStackSize;

    public DynamicRestrictedSlot(Container container, int index, int xPosition, int yPosition,
                                 Predicate<ItemStack> allowedPredicate, int maxStackSize) {
        super(container, index, xPosition, yPosition);
        this.allowedPredicate = allowedPredicate;
        this.maxStackSize = maxStackSize;
    }

    public DynamicRestrictedSlot(Container container, int index, int xPosition, int yPosition,
                                 Item allowedItem, int maxStackSize) {
        this(container, index, xPosition, yPosition, stack -> stack.getItem() == allowedItem, maxStackSize);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return allowedPredicate.test(stack);
    }

    @Override
    public int getMaxStackSize() {
        return maxStackSize;
    }
}
