package betterwithengineering.ie;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.blocks.BlockSteel;
import betterwithmods.common.blocks.mechanical.BlockCookingPot;
import blusunrize.immersiveengineering.api.IEProperties;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.BlockTypes_MetalsAll;
import blusunrize.immersiveengineering.common.blocks.BlockTypes_MetalsIE;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalDecoration0;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalDecoration1;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalMultiblock;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityArcFurnace;
import blusunrize.immersiveengineering.common.blocks.multiblocks.MultiblockArcFurnace;
import blusunrize.immersiveengineering.common.blocks.stone.BlockTypes_StoneDecoration;
import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BWMMultiBlockArcFurnance extends MultiblockArcFurnace {
    public static final BWMMultiBlockArcFurnance instance = new BWMMultiBlockArcFurnance();

    static ItemStack[][][] structure = new ItemStack[5][5][5];

    static {
        for (int h = 0; h < 5; h++)
            for (int l = 0; l < 5; l++)
                for (int w = 0; w < 5; w++) {
                    if (h == 0) {
                        if (l == 0 && w == 2)
                            structure[h][l][w] = BlockCookingPot.getStack(BlockCookingPot.EnumType.CRUCIBLE);
                        else if (l == 2 && (w == 0 || w == 4))
                            structure[h][l][w] = new ItemStack(BWMBlocks.STEEL_BLOCK);
                        else if (l == 0 && w == 0)
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration1, 1, BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta());
                        else if (l == 4 && w == 2)
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0, 1, BlockTypes_MetalDecoration0.HEAVY_ENGINEERING.getMeta());
                        else if (l == 4 || (l > 2 && (w == 0 || w == 4)))
                            structure[h][l][w] = new ItemStack(IEContent.blockSheetmetal, 1, BlockTypes_MetalsAll.STEEL.getMeta());
                        else
                            structure[h][l][w] = new ItemStack(IEContent.blockSheetmetalSlabs, 1, BlockTypes_MetalsAll.STEEL.getMeta());
                    } else if (h == 1) {
                        if (l == 2 && (w == 0 || w == 4))
                            structure[h][l][w] = new ItemStack(BWMBlocks.STEEL_BLOCK);
                        else if (l == 0 && w == 0)
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0, 1, BlockTypes_MetalDecoration0.RS_ENGINEERING.getMeta());
                        else if (l == 4 && w > 0 && w < 4)
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0, 1, BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta());
                        else if ((w == 0 || w == 4) && l > 2)
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0, 1, BlockTypes_MetalDecoration0.HEAVY_ENGINEERING.getMeta());
                        else if (l >= 2 && w > 0 && w < 4)
                            structure[h][l][w] = new ItemStack(IEContent.blockStoneDecoration, 1, BlockTypes_StoneDecoration.BLASTBRICK_REINFORCED.getMeta());
                    } else if (h == 2) {
                        if (l == 2 && (w == 0 || w == 4))
                            structure[h][l][w] = new ItemStack(BWMBlocks.STEEL_BLOCK);
                        else if (l == 4 && w > 0 && w < 4)
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0, 1, BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta());
                        else if (l == 4)
                            structure[h][l][w] = new ItemStack(BWMBlocks.STEEL_BLOCK);
                        else if (w > 0 && w < 4)
                            structure[h][l][w] = new ItemStack(IEContent.blockStoneDecoration, 1, BlockTypes_StoneDecoration.BLASTBRICK_REINFORCED.getMeta());
                    } else if (h == 3) {
                        if (l == 4 && w == 2)
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0, 1, BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta());
                        else if (l == 4 && (w == 1 || w == 3))
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration1, 1, BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta());
                        else if (l > 0 && w > 0 && w < 4)
                            structure[h][l][w] = new ItemStack(IEContent.blockStoneDecoration, 1, BlockTypes_StoneDecoration.BLASTBRICK_REINFORCED.getMeta());
                    } else if (h == 4) {
                        if (l > 1 && w == 2)
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0, 1, BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta());
                        else if (l == 4 && (w == 1 || w == 3))
                            structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration1, 1, BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta());
                    }

                    if (structure[h][l][w] == null) {
                        structure[h][l][w] = ItemStack.EMPTY;
                    }
                }
    }

    @Override
    public boolean isBlockTrigger(IBlockState state) {
        Block block = state.getBlock();
        if (block instanceof BlockCookingPot)
            return state.getValue(BlockCookingPot.TYPE) == BlockCookingPot.EnumType.CRUCIBLE;
        return false;
    }

    @Override
    public ItemStack[][][] getStructureManual() {
        return structure;
    }

    @Override
    public IBlockState getBlockstateFromStack(int index, ItemStack stack) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof ItemBlock)
                return ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getItemDamage());
        }
        return null;
    }

    @Override
    public boolean createStructure(World world, BlockPos pos, EnumFacing side, EntityPlayer player) {
        if (side == EnumFacing.UP || side == EnumFacing.DOWN)
            side = EnumFacing.fromAngle(player.rotationYaw);
        BlockPos startPos = pos;
        side = side.getOpposite();

        if (Utils.isOreBlockAt(world, startPos.add(0, -1, 0), "scaffoldingSteel")
                && Utils.isBlockAt(world, startPos.offset(side, 2).add(0, -1, 0), IEContent.blockMetalDecoration0, BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta())) {
            startPos = startPos.offset(side, 2);
            side = side.getOpposite();
        }

        boolean mirrored = false;
        boolean b = structureCheck(world, startPos, side, mirrored);
        if (!b) {
            mirrored = true;
            b = structureCheck(world, startPos, side, mirrored);
        }

        if (b) {
            IBlockState state = IEContent.blockMetalMultiblock.getStateFromMeta(BlockTypes_MetalMultiblock.ARC_FURNACE.getMeta());
            state = state.withProperty(IEProperties.FACING_HORIZONTAL, side);
            for (int l = 0; l < 5; l++)
                for (int w = -2; w <= 2; w++)
                    for (int h = 0; h < 5; h++)
                        if (!structure[h][l][w + 2].isEmpty()) {
                            int ww = mirrored ? -w : w;
                            BlockPos pos2 = startPos.offset(side, l).offset(side.rotateY(), ww).add(0, h, 0);

                            world.setBlockState(pos2, state);
                            TileEntity curr = world.getTileEntity(pos2);
                            if (curr instanceof TileEntityArcFurnace) {
                                TileEntityArcFurnace tile = (TileEntityArcFurnace) curr;
                                tile.formed = true;
                                tile.pos = h * 25 + l * 5 + (w + 2);
                                tile.offset = new int[]{(side == EnumFacing.WEST ? -l + 2 : side == EnumFacing.EAST ? l - 2 : side == EnumFacing.NORTH ? ww : -ww), h - 1, (side == EnumFacing.NORTH ? -l + 2 : side == EnumFacing.SOUTH ? l - 2 : side == EnumFacing.EAST ? ww : -ww)};
                                tile.mirrored = mirrored;
                                tile.markDirty();
                                world.addBlockEvent(pos2, IEContent.blockMetalMultiblock, 255, 0);
                            }
                        }
        }
        return b;
    }

    boolean structureCheck(World world, BlockPos startPos, EnumFacing dir, boolean mirror) {
        for (int l = 0; l < 5; l++)
            for (int w = -2; w <= 2; w++)
                for (int h = 0; h < 5; h++)
                    if (!structure[h][l][w + 2].isEmpty()) {
                        int ww = mirror ? -w : w;
                        BlockPos pos = startPos.offset(dir, l).offset(dir.rotateY(), ww).add(0, h, 0);

                        if (world.isAirBlock(pos))
                            return false;
                        if (OreDictionary.itemMatches(structure[h][l][w + 2], BlockCookingPot.getStack(BlockCookingPot.EnumType.CRUCIBLE), true)) {
                            IBlockState state = world.getBlockState(pos);
                            if (state.getBlock() == BWMBlocks.COOKING_POTS)
                                return state.getValue(BlockCookingPot.TYPE) == BlockCookingPot.EnumType.CRUCIBLE;
                        } else if (OreDictionary.itemMatches(structure[h][l][w + 2], new ItemStack(IEContent.blockMetalDecoration1, 1, BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta()), true)) {
                            if (!Utils.isOreBlockAt(world, pos, "scaffoldingSteel"))
                                return false;
                        } else if (OreDictionary.itemMatches(structure[h][l][w + 2], new ItemStack(IEContent.blockSheetmetal, 1, BlockTypes_MetalsAll.STEEL.getMeta()), true)) {
                            if (!Utils.isOreBlockAt(world, pos, "blockSheetmetalSteel"))
                                return false;
                        } else if (OreDictionary.itemMatches(structure[h][l][w + 2], new ItemStack(IEContent.blockSheetmetalSlabs, 1, BlockTypes_MetalsAll.STEEL.getMeta()), true)) {
                            if (!Utils.isOreBlockAt(world, pos, "slabSheetmetalSteel"))
                                return false;
                        } else if (OreDictionary.itemMatches(structure[h][l][w + 2], new ItemStack(IEContent.blockStorage, 1, BlockTypes_MetalsIE.STEEL.getMeta()), true)) {
                            if (!Utils.isOreBlockAt(world, pos, "blockSteel"))
                                return false;
                        } else {
                            Block b = Block.getBlockFromItem(structure[h][l][w + 2].getItem());
                            if (b != null)
                                if (!Utils.isBlockAt(world, pos, b, structure[h][l][w + 2].getItemDamage()))
                                    return false;
                        }
                    }
        return true;
    }

}
