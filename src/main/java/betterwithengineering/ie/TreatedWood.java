package betterwithengineering.ie;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.blocks.mini.BlockCorner;
import betterwithmods.common.blocks.mini.BlockMoulding;
import betterwithmods.common.blocks.mini.BlockSiding;
import betterwithmods.common.blocks.mini.ItemBlockMini;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.module.Feature;
import betterwithmods.module.gameplay.SawRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import static betterwithmods.common.BWOreDictionary.registerOre;


public class TreatedWood extends Feature {

    protected final String modid;

    public TreatedWood() {
        configName = "TreatedWood";
        modid = "immersiveengineering";
    }

    public String[] woods = { "horizontal", "vertical", "packaged"};

    public static Block SIDING = new BlockSiding(Material.WOOD) {
        @Override
        public int getUsedTypes() {
            return 3;
        }

    }.setRegistryName("ie_siding");
    public static Block MOULDING = new BlockMoulding(Material.WOOD) {
        @Override
        public int getUsedTypes() {
            return 3;
        }

    }.setRegistryName("ie_moulding");
    public static Block CORNER = new BlockCorner(Material.WOOD) {
        @Override
        public int getUsedTypes() {
            return 3;
        }
    }.setRegistryName("ie_corner");


    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BWMBlocks.registerBlock(SIDING, new ItemBlockMini(SIDING));
        BWMBlocks.registerBlock(MOULDING, new ItemBlockMini(MOULDING));
        BWMBlocks.registerBlock(CORNER, new ItemBlockMini(CORNER));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void preInitClient(FMLPreInitializationEvent event) {
        BWMBlocks.setInventoryModel(SIDING);
        BWMBlocks.setInventoryModel(MOULDING);
        BWMBlocks.setInventoryModel(CORNER);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        Block treated_wood = getBlock(modid + ":treated_wood");
        Block treated_slab = getBlock(modid + ":treated_wood_slab");
        for (int i = 0; i < 3; i++) {
            SawRecipes.addSawRecipe(treated_wood, i, new ItemStack(SIDING, 2, i));
            SawRecipes.addSawRecipe(SIDING, i, new ItemStack(MOULDING, 2, i));
            SawRecipes.addSawRecipe(MOULDING, i, new ItemStack(CORNER, 2, i));
            SawRecipes.addSawRecipe(CORNER, i, ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GEAR, 2));
            SawRecipes.addSawRecipe(treated_slab, i, new ItemStack(MOULDING, 2, i));

        }

        registerOre("sidingTreatedWood", new ItemStack(SIDING, 1, OreDictionary.WILDCARD_VALUE));
        registerOre("sidingWood", new ItemStack(SIDING, 1, OreDictionary.WILDCARD_VALUE));

        registerOre("slabTreatedWood", new ItemStack(SIDING, 1, OreDictionary.WILDCARD_VALUE));

        registerOre("mouldingTreatedWood", new ItemStack(MOULDING, 1, OreDictionary.WILDCARD_VALUE));
        registerOre("mouldingWood", new ItemStack(MOULDING, 1, OreDictionary.WILDCARD_VALUE));

        registerOre("cornerTreatedWood", new ItemStack(CORNER, 1, OreDictionary.WILDCARD_VALUE));
        registerOre("cornerWood", new ItemStack(CORNER, 1, OreDictionary.WILDCARD_VALUE));

    }

    public Block getBlock(String location) {
        return Block.REGISTRY.getObject(new ResourceLocation(location));
    }
}
