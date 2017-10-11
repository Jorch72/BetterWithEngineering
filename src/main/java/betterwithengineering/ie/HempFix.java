package betterwithengineering.ie;

import betterwithengineering.BWE;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.module.Feature;
import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.plant.BlockIECrop;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;

public class HempFix extends Feature {

    public HempFix() {
        configName = "HempFix";
    }

    @SubscribeEvent
    public void overrideHempDrops(BlockEvent.HarvestDropsEvent e) {
        if (e.isCanceled())
            return;
        IBlockState state = e.getState();
        if (state.getBlock() instanceof BlockIECrop) {
            e.getDrops().clear();
            int meta = state.getBlock().getMetaFromState(state);
            if (meta >= 4) {
                e.getDrops().add(new ItemStack(IEContent.itemSeeds));
                e.getDrops().add(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.HEMP));
            }
        }
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }


    @Override
    public void init(FMLInitializationEvent event) {
        BelljarHandler.registerHandler(bwmHempHandler);
        bwmHempHandler.register(new ItemStack(BWMBlocks.HEMP), new ItemStack[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.HEMP), new ItemStack(BWMBlocks.HEMP)}, new ItemStack(Blocks.DIRT), BWMBlocks.HEMP.getDefaultState());
        BelljarHandler.DefaultPlantHandler ieHempHandler = (BelljarHandler.DefaultPlantHandler) BelljarHandler.getHandler(new ItemStack(IEContent.itemSeeds));
        if (ieHempHandler != null)
            ieHempHandler.register(new ItemStack(IEContent.itemSeeds), new ItemStack[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.HEMP), new ItemStack(IEContent.itemSeeds)}, new ItemStack(Blocks.DIRT), IEContent.blockCrop.getDefaultState());
    }

    private BelljarHandler.DefaultPlantHandler bwmHempHandler = new BelljarHandler.DefaultPlantHandler() {
        private HashSet<ComparableItemStack> validSeeds = new HashSet<>();

        @Override
        protected HashSet<ComparableItemStack> getSeedSet() {
            return validSeeds;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IBlockState[] getRenderedPlant(ItemStack seed, ItemStack soil, float growth, TileEntity tile) {
            int age = Math.min(7, Math.round(growth * 7));
            if (age > 6)
                return new IBlockState[]{BWMBlocks.HEMP.getStateFromMeta(age), BWMBlocks.HEMP.getStateFromMeta(8)};
            return new IBlockState[]{BWMBlocks.HEMP.getStateFromMeta(age)};
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getRenderSize(ItemStack seed, ItemStack soil, float growth, TileEntity tile) {
            return .7875f;
        }
    };

}
