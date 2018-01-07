package betterwithengineering.ie;

import betterwithengineering.BWE;
import betterwithmods.api.capabilities.CapabilityMechanicalPower;
import betterwithmods.api.tile.IAxle;
import betterwithmods.api.tile.IAxleTick;
import betterwithmods.api.tile.IMechanicalPower;
import betterwithmods.common.blocks.mechanical.BlockAxle;
import betterwithmods.common.blocks.mechanical.tile.TileAxle;
import betterwithmods.module.Feature;
import betterwithmods.util.MechanicalUtil;
import blusunrize.immersiveengineering.api.energy.IRotationAcceptor;
import blusunrize.immersiveengineering.common.blocks.wooden.TileEntityWatermill;
import blusunrize.immersiveengineering.common.blocks.wooden.TileEntityWindmill;
import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MechPower extends Feature {
    private final ResourceLocation IE_MECH_POWER = new ResourceLocation(BWE.MODID, "MECH_POWER");

    public MechPower() {
        configName = "IE Mechanical Power";
        TileAxle.tickHandlers.add(new AxleRF());
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<TileEntity> event) {
        TileEntity tile = event.getObject();
        if (tile instanceof TileEntityWindmill) {
            event.addCapability(IE_MECH_POWER, new WindmillCap((TileEntityWindmill) tile));
        }
        if (tile instanceof TileEntityWatermill) {
            event.addCapability(IE_MECH_POWER, new WaterwheelCap((TileEntityWatermill) tile));
        }
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    public static class WindmillCap implements ICapabilityProvider, IMechanicalPower {
        private TileEntityWindmill windmill;

        public WindmillCap(TileEntityWindmill windmill) {
            this.windmill = windmill;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityMechanicalPower.MECHANICAL_POWER && (facing != null && facing.getAxis().isHorizontal());
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (hasCapability(capability, facing)) {
                return CapabilityMechanicalPower.MECHANICAL_POWER.cast(this);
            }
            return null;
        }

        @Override
        public int getMechanicalOutput(EnumFacing facing) {
            if (windmill.getFacing().equals(facing)) {
                return (int) (windmill.perTick * 1000) - 0b11;
            }
            return 0;
        }

        @Override
        public int getMechanicalInput(EnumFacing facing) {
            return 0;
        }

        @Override
        public int getMaximumInput(EnumFacing facing) {
            return 0;
        }

        @Override
        public int getMinimumInput(EnumFacing facing) {
            return 0;
        }

        @Override
        public Block getBlock() {
            return windmill.getBlockType();
        }

        @Override
        public World getBlockWorld() {
            return windmill.getWorld();
        }

        @Override
        public BlockPos getBlockPos() {
            return windmill.getPos();
        }
    }


    public static class WaterwheelCap implements ICapabilityProvider, IMechanicalPower {
        private TileEntityWatermill watermill;

        public WaterwheelCap(TileEntityWatermill watermill) {
            this.watermill = watermill;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityMechanicalPower.MECHANICAL_POWER && (facing != null && facing.getAxis().isHorizontal());
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (hasCapability(capability, facing)) {
                return CapabilityMechanicalPower.MECHANICAL_POWER.cast(this);
            }
            return null;
        }

        @Override
        public int getMechanicalOutput(EnumFacing facing) {
            if (watermill.getFacing().equals(facing) || watermill.getFacing().getOpposite().equals(facing)) {
                return watermill.perTick != 0 ? 1 : 0;
            }
            return 0;
        }

        @Override
        public int getMechanicalInput(EnumFacing facing) {
            return 0;
        }

        @Override
        public int getMaximumInput(EnumFacing facing) {
            return 0;
        }

        @Override
        public int getMinimumInput(EnumFacing facing) {
            return 0;
        }

        @Override
        public Block getBlock() {
            return watermill.getBlockType();
        }

        @Override
        public World getBlockWorld() {
            return watermill.getWorld();
        }

        @Override
        public BlockPos getBlockPos() {
            return watermill.getPos();
        }
    }


    public class AxleRF implements IAxleTick {
        @Override
        public void tick(World world, BlockPos pos, IAxle axle) {
            for (EnumFacing facing : axle.getDirections()) {
                TileEntity acc = Utils.getExistingTileEntity(world, pos.offset(facing.getOpposite()));
                if (acc instanceof IRotationAcceptor) {
                    if (!world.isRemote) {
                        IRotationAcceptor dynamo = (IRotationAcceptor) acc;
                        dynamo.inputRotation(axle.getMechanicalOutput(facing) * BWE.ConfigManager.mechPower.rfScale, facing.getOpposite());
                    }
                }
            }
        }
    }
}
