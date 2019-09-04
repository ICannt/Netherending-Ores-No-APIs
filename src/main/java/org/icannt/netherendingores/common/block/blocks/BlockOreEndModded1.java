package org.icannt.netherendingores.common.block.blocks;

import java.util.Random;

import org.icannt.netherendingores.common.block.BlockVariantBase;
import org.icannt.netherendingores.common.block.data.BlockDataOreEndModded1;
import org.icannt.netherendingores.common.registry.BlockRecipeData;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by ICannt on 19/08/17.
 */
public class BlockOreEndModded1 extends BlockVariantBase {

    private static final PropertyEnum<BlockDataOreEndModded1> VARIANT = PropertyEnum.create("blocks", BlockDataOreEndModded1.class);
    
    public BlockOreEndModded1() {
        super(Material.ROCK, MapColor.GRAY, "ore_end_modded_1");
        for (BlockDataOreEndModded1 variant : BlockDataOreEndModded1.values()) {
        	this.setHarvestLevel("pickaxe", variant.getHarvestLevel(), getStateFromMeta(variant.ordinal()));
        }
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }   

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (BlockDataOreEndModded1 type : BlockDataOreEndModded1.values()) {
			list.add(new ItemStack(this, 1, type.ordinal()));
		}
    }

	@SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, BlockDataOreEndModded1.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
    	

    	
        return getMetaFromState(state);
    }
    
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {

    	
    	return 1;
    	
    }
        
    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
    	
    	int ordinal = getOrdinal(state);

    	if (BlockRecipeData.values()[ordinal].getDropItemNotBlock()) {
    		BlockRecipeData.values()[ordinal].getItemDropped();
    	}
    	
    	return Item.getItemFromBlock(this);
    	
    }
    
    //
    public int getOrdinal(IBlockState state) {
    	return BlockDataOreEndModded1.values()[getMetaFromState(state)].getBlockRecipeDataOrdinal();
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(VARIANT).getLight();
    }  

    @Override
    public float getBlockHardness(IBlockState state, World worldIn, BlockPos pos) {
        return state.getValue(VARIANT).getHardness();
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return world.getBlockState(pos).getValue(VARIANT).getResistance() / 5F;
    }
    
    @SideOnly(Side.CLIENT)
    public void initItemBlockModels() {
    	for (BlockDataOreEndModded1 variant : BlockDataOreEndModded1.values()) {
    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), variant.ordinal(), new ModelResourceLocation(Item.getItemFromBlock(this).getRegistryName(), "blocks=" + variant.getName()));
    	}
    }

}
