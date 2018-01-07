package betterwithengineering;


import betterwithmods.module.Feature;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ThermalExpansion extends Feature {
    public static final String MODID = "thermalexpansion";

    public ThermalExpansion() {
        configName = "ThermalExpansion";
    }

    @Override
    public void setupConfig() {
        super.setupConfig();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        removeSmelterRecipe(new ItemStack(Items.BUCKET), ItemStack.EMPTY);
        addSmelterRecipe(6000, new ItemStack(Items.BUCKET), ItemStack.EMPTY, new ItemStack(Items.IRON_NUGGET, 7));

    }

    public static ItemStack SAND = new ItemStack(Blocks.SAND);
    public static ItemStack SOUL_SAND = new ItemStack(Blocks.SOUL_SAND);

    @Override
    public void postInit(FMLPostInitializationEvent event) {

//        if (BWE.ConfigManager.thermalExpansion.hardcoreInductionSmelter) {
//            ItemStack HELL_FIRE_INGOT = ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.CONCENTRATED_HELLFIRE);
//            ItemStack DIAMOND_INGOT = ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.DIAMOND_INGOT);
//            Map<List<SmelterManager.ComparableItemStackSmelter>, SmelterManager.SmelterRecipe> recipes = ReflectionHelper.getPrivateValue(SmelterManager.class, null, "recipeMap");
//
//            Iterator<SmelterManager.SmelterRecipe> iterator = recipes.values().iterator();
//            while (iterator.hasNext()) {
//                SmelterManager.SmelterRecipe recipe = iterator.next();
//                if (recipe.getPrimaryOutput().isItemEqual(DIAMOND_INGOT)) {
//                    iterator.remove();
//                } else if (recipe.getSecondaryInput().isItemEqual(SAND)) {
//                    ReflectionHelper.setPrivateValue(SmelterManager.SmelterRecipe.class, recipe, HELL_FIRE_INGOT, "secondaryInput");
//                    ReflectionHelper.setPrivateValue(SmelterManager.SmelterRecipe.class, recipe, HELL_FIRE_INGOT, "secondaryInput");
//                } else if (recipe.getSecondaryInput().isItemEqual(SOUL_SAND)) {
//                    ReflectionHelper.setPrivateValue(SmelterManager.SmelterRecipe.class, recipe, HELL_FIRE_INGOT, "secondaryInput");
//                }
//            }
//        }
    }

    public void addSmelterRecipe(int energy, ItemStack primary, ItemStack secondary, ItemStack output) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("energy", energy);
        tag.setTag("primaryInput", primary.serializeNBT());
        tag.setTag("secondaryInput", secondary.serializeNBT());
        tag.setTag("primaryOutput", output.serializeNBT());
        FMLInterModComms.sendMessage(MODID, ADD_SMELTER_RECIPE, tag);
    }

    public void removeSmelterRecipe(ItemStack primary, ItemStack secondary) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("primaryInput", primary.serializeNBT());
        tag.setTag("secondaryInput", secondary.serializeNBT());

        FMLInterModComms.sendMessage(MODID, REMOVE_SMELTER_RECIPE, tag);
    }

    public static final String ADD_SMELTER_RECIPE = "addsmelterrecipe";
    public static final String REMOVE_SMELTER_RECIPE = "removesmelterrecipe";
}
