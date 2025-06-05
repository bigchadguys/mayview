package net.joe.mayview.item.custom;

import net.joe.mayview.data.ModDataComponents;
import net.joe.mayview.screen.custom.PiggyBankMenu;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PiggyBankItem extends Item {
    public PiggyBankItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level level,
            @NotNull Player player,
            @NotNull InteractionHand hand
    ) {
        ItemStack stackInHand = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            level.playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PIG_AMBIENT,
                    SoundSource.PLAYERS,
                    1.0F, 1.8F
            );

            CompoundTag tag = stackInHand.getComponents()
                    .get(ModDataComponents.COIN_CONTAINER.get());
            final CompoundTag coinData = tag != null ? tag : new CompoundTag();

            HolderLookup.Provider registries = serverPlayer.serverLevel().registryAccess();

            serverPlayer.openMenu(new SimpleMenuProvider(
                    (windowId, playerInventory, openingPlayer) -> {
                        SimpleContainer coinContainer = new SimpleContainer(4);
                        if (coinData.contains(ContainerHelper.TAG_ITEMS, Tag.TAG_LIST)) {
                            ContainerHelper.loadAllItems(coinData, coinContainer.getItems(), registries);
                        }
                        return new PiggyBankMenu(windowId, playerInventory, coinContainer, stackInHand);
                    },
                    Component.translatable("container.piggy_bank")
            ));
        }

        return InteractionResultHolder.success(stackInHand);
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            @NotNull TooltipContext context,
            @NotNull List<Component> tooltipComponents,
            @NotNull TooltipFlag tooltipFlag
    ) {
        CompoundTag coinData = stack.getComponents()
                .get(ModDataComponents.COIN_CONTAINER.get());

        if (coinData != null && coinData.contains(ContainerHelper.TAG_ITEMS, Tag.TAG_LIST)) {
            var items = coinData.getList(ContainerHelper.TAG_ITEMS, Tag.TAG_COMPOUND);
            int copper  = 0, iron = 0, gold = 0, diamond = 0;

            for (var raw : items) {
                CompoundTag tag = (CompoundTag) raw;
                String id = tag.getString("id");
                int count = tag.getInt("Count");
                switch (id) {
                    case "mayview:copper_coin"   -> copper  += count;
                    case "mayview:iron_coin"     -> iron    += count;
                    case "mayview:gold_coin"     -> gold    += count;
                    case "mayview:diamond_coin"  -> diamond += count;
                }
            }

            addTooltip(tooltipComponents, "diamond", diamond, 0x1f99ca);
            addTooltip(tooltipComponents, "gold",    gold,    0xd19944);
            addTooltip(tooltipComponents, "iron",    iron,    0x849599);
            addTooltip(tooltipComponents, "copper",  copper,  0xad5635);
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private void addTooltip(List<Component> tooltipComponents, String type, int amount, int color) {
        if (amount > 0) {
            tooltipComponents.add(
                    Component.translatable("item.mayview.piggy_bank.tooltip." + type, amount)
                            .setStyle(Style.EMPTY.withColor(color))
            );
        }
    }
}
