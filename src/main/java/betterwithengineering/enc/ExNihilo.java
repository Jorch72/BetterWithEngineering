package betterwithengineering.enc;

import betterwithengineering.BWE;
import betterwithmods.common.blocks.mechanical.tile.TileAxle;
import betterwithmods.module.Feature;
import exnihilocreatio.rotationalPower.CapabilityRotationalMember;
import exnihilocreatio.rotationalPower.IRotationalPowerMember;
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
import java.util.Optional;

public class ExNihilo {

    public static class MechPower extends Feature {
        public static final ResourceLocation ROT_POWER = new ResourceLocation(BWE.MODID, "rotational_power");

        @SubscribeEvent
        public void attachCapability(AttachCapabilitiesEvent<TileEntity> event) {
            TileEntity tile = event.getObject();
            if (tile instanceof TileAxle) {
                event.addCapability(ROT_POWER, new AxleProvider((TileAxle) tile));
            }
        }

        @Override
        public boolean hasSubscriptions() {
            return true;
        }

        public static class AxleProvider implements ICapabilityProvider, IRotationalPowerMember {


            private TileAxle tile;

            public AxleProvider(TileAxle tile) {

                this.tile = tile;
            }

            @Override
            public float getOwnRotation() {
                return tile.getPower();
            }

            @Override
            public float getEffectivePerTickRotation(EnumFacing enumFacing) {
                if (enumFacing.getAxis() == EnumFacing.Axis.X) {
                    return enumFacing.getAxisDirection().getOffset() *  tile.getPower() * BWE.ConfigManager.exNihilo.rotationSpeed;
                } else {
                    return -enumFacing.getAxisDirection().getOffset() * tile.getPower() * BWE.ConfigManager.exNihilo.rotationSpeed;
                }
            }

            @Override
            public void setEffectivePerTickRotation(float v) {

            }

            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
                if (capability == CapabilityRotationalMember.ROTIONAL_MEMBER && tile.getAxis().apply(facing))
                    return true;
                return false;
            }

            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
                if (capability == CapabilityRotationalMember.ROTIONAL_MEMBER && tile.getAxis().apply(facing))
                    return CapabilityRotationalMember.ROTIONAL_MEMBER.cast(this);
                return null;
            }
        }

    }


}
