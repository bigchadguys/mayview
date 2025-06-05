package net.joe.mayview.data;

import net.joe.mayview.Mayview;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Mayview.MOD_ID);

    public static final Supplier<DataComponentType<CompoundTag>> COIN_CONTAINER =
            DATA_COMPONENT_TYPES.register("coin_container", () ->
                    DataComponentType.<CompoundTag>builder()
                            .persistent(CompoundTag.CODEC)
                            .build()
            );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
