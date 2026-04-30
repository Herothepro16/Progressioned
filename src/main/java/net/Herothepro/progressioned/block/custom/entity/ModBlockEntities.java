package net.Herothepro.progressioned.block.custom.entity;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Progressioned.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChoppingBlockEntity>> CHOPPING_BLOCK_BE =
            BLOCK_ENTITY_TYPES.register("chopping_block",
                    () -> BlockEntityType.Builder.of(ChoppingBlockEntity::new,
                            ModBlocks.CHOPPING_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}