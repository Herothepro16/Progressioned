package net.Herothepro.progressioned.item.custom;

import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public class ClayBucketFluidHandler implements IFluidHandlerItem {
    private ItemStack container;

    public ClayBucketFluidHandler(ItemStack container) {
        this.container = container;
    }

    @Override
    public ItemStack getContainer() {
        return container;
    }

    private Fluid containedFluid() {
        if (container.is(ModItems.CLAY_WATER_BUCKET.get())) return Fluids.WATER;
        if (container.is(ModItems.CLAY_LAVA_BUCKET.get())) return Fluids.LAVA;
        return Fluids.EMPTY;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        Fluid fluid = containedFluid();
        return fluid == Fluids.EMPTY ? FluidStack.EMPTY : new FluidStack(fluid, 1000);
    }

    @Override
    public int getTankCapacity(int tank) {
        return 1000;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return stack.getFluid() == Fluids.WATER || stack.getFluid() == Fluids.LAVA;
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if (!container.is(ModItems.CLAY_BUCKET.get())) return 0;
        if (resource.getAmount() < 1000) return 0;

        ItemStack filled;
        if (resource.getFluid() == Fluids.WATER) filled = new ItemStack(ModItems.CLAY_WATER_BUCKET.get());
        else if (resource.getFluid() == Fluids.LAVA) filled = new ItemStack(ModItems.CLAY_LAVA_BUCKET.get());
        else return 0;

        if (action.execute()) container = filled;
        return 1000;
    }

    @Override
    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.getFluid() != containedFluid()) return FluidStack.EMPTY;
        return drain(resource.getAmount(), action);
    }

    @Override
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        Fluid fluid = containedFluid();
        if (fluid == Fluids.EMPTY || maxDrain < 1000) return FluidStack.EMPTY;

        if (action.execute()) {
            container = fluid == Fluids.WATER
                    ? new ItemStack(ModItems.CLAY_BUCKET.get())
                    : ItemStack.EMPTY;
        }
        return new FluidStack(fluid, 1000);
    }
}