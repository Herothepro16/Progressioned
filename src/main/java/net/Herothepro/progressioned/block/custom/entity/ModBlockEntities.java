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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KilnBlockEntity>> KILN_BE =
            BLOCK_ENTITY_TYPES.register("porcelain_kiln",
                    () -> BlockEntityType.Builder.of(KilnBlockEntity::new,
                            ModBlocks.PORCELAIN_KILN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MelterBlockEntity>> MELTER_BE =
            BLOCK_ENTITY_TYPES.register("porcelain_melter",
                    () -> BlockEntityType.Builder.of(MelterBlockEntity::new,
                            ModBlocks.PORCELAIN_MELTER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HeaterBlockEntity>> HEATER_BE =
            BLOCK_ENTITY_TYPES.register("porcelain_heater",
                    () -> BlockEntityType.Builder.of(HeaterBlockEntity::new,
                            ModBlocks.PORCELAIN_HEATER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FaucetBlockEntity>> FAUCET_BE =
            BLOCK_ENTITY_TYPES.register("porcelain_faucet",
                    () -> BlockEntityType.Builder.of(FaucetBlockEntity::new,
                            ModBlocks.PORCELAIN_FAUCET.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TableBlockEntity>> TABLE_BE =
            BLOCK_ENTITY_TYPES.register("porcelain_casting_table",
                    () -> BlockEntityType.Builder.of(TableBlockEntity::new,
                            ModBlocks.PORCELAIN_CASTING_TABLE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BasinBlockEntity>> BASIN_BE =
            BLOCK_ENTITY_TYPES.register("porcelain_castin_basin",
                    () -> BlockEntityType.Builder.of(BasinBlockEntity::new,
                            ModBlocks.PORCELAIN_CASTING_BASIN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WorkstoneBlockEntity>> WORKSTONE_BE =
            BLOCK_ENTITY_TYPES.register("workstone",
                    () -> BlockEntityType.Builder.of(WorkstoneBlockEntity::new,
                            ModBlocks.WORKSTONE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}