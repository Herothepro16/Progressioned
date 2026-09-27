package net.Herothepro.progressioned.fluid;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, Progressioned.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Progressioned.MOD_ID);

    public static final List<FluidHolder<FluidType>> REGISTERED_FLUIDS = new ArrayList<>();

    public static final FluidHolder<FluidType> MOLTEN_IRON = registerFluid("molten_iron", 0xff9a1616);
    public static final FluidHolder<FluidType> MOLTEN_COPPER = registerFluid("molten_copper", 0xffe77c56);
    public static final FluidHolder<FluidType> MOLTEN_GOLD = registerFluid("molten_gold", 0xfff3de37);

    public static FluidHolder<FluidType> registerFluid(String name, int color) {
        DeferredHolder<FluidType, FluidType> type = FLUID_TYPES.register(name, () -> new FluidType(
                FluidType.Properties.create()
                        .canSwim(false)
                        .canDrown(false)
                        .canConvertToSource(false)
                        .lightLevel(15)
                        .density(3000)
                        .viscosity(6000)
                        .temperature(1300)
                        .motionScale(0.002335)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                        .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.LAVA_EXTINGUISH)
        ));

        @SuppressWarnings("unchecked")
        DeferredHolder<Fluid, BaseFlowingFluid.Source>[] source = new DeferredHolder[1];
        @SuppressWarnings("unchecked")
        DeferredHolder<Fluid, BaseFlowingFluid.Flowing>[] flowing = new DeferredHolder[1];
        @SuppressWarnings("unchecked")
        DeferredBlock<LiquidBlock>[] block = new DeferredBlock[1];
        @SuppressWarnings("unchecked")
        DeferredItem<BucketItem>[] bucket = new DeferredItem[1];

        BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(
                type,
                () -> source[0].get(),
                () -> flowing[0].get()
        )
                .block(() -> block[0].get())
                .bucket(() -> bucket[0].get())
                .tickRate(30)
                .levelDecreasePerBlock(2)
                .slopeFindDistance(2);

        source[0] = FLUIDS.register(name, () -> new BaseFlowingFluid.Source(properties));
        flowing[0] = FLUIDS.register("flowing_" + name, () -> new BaseFlowingFluid.Flowing(properties));
        block[0] = ModBlocks.BLOCKS.register(name, () -> new LiquidBlock(source[0].get(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA)));
        bucket[0] = ModItems.ITEMS.register(name + "_bucket", () -> new BucketItem(source[0].get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

        FluidHolder<FluidType> container = new FluidHolder<>(type, source[0], flowing[0], block[0], bucket[0], color);
        REGISTERED_FLUIDS.add(container);
        return container;
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}