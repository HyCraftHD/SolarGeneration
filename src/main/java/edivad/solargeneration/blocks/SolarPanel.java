package edivad.solargeneration.blocks;

import java.util.List;

import edivad.solargeneration.Main;
import edivad.solargeneration.tile.TileEntityAdvancedSolarPanel;
import edivad.solargeneration.tile.TileEntityHardenedSolarPanel;
import edivad.solargeneration.tile.TileEntityLeadstoneSolarPanel;
import edivad.solargeneration.tile.TileEntityRedstoneSolarPanel;
import edivad.solargeneration.tile.TileEntityResonantSolarPanel;
import edivad.solargeneration.tile.TileEntitySignalumSolarPanel;
import edivad.solargeneration.tile.TileEntityUltimateSolarPanel;
import edivad.solargeneration.tools.SolarPanelLevel;
import edivad.solargeneration.tools.Tooltip;
import edivad.solargeneration.tools.inter.IRestorableTileEntity;
//import cofh.thermalfoundation.item.ItemWrench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SolarPanel extends Block {

	// SolarPanel Level
	// 0-SolarPanelLeadstone
	// 1-SolarPanelHardened
	// 2-SolarPanelRedstone
	// 3-SolarPanelSignalum
	// 4-SolarPanelResonant
	// 5-SolarPanelAdvanced
	// 6-SolarPanelUltimate
	private final SolarPanelLevel levelSolarPanel;
	private static final VoxelShape BOX = VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

	public SolarPanel(SolarPanelLevel levelSolarPanel)
	{
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5F, 30F));

		this.levelSolarPanel = levelSolarPanel;

		setRegistryName(getResourceLocation(levelSolarPanel));
	}

	public static ResourceLocation getResourceLocation(SolarPanelLevel levelSolarPanel)
	{
		return new ResourceLocation(Main.MODID, "solar_panel_" + levelSolarPanel.name().toLowerCase());
	}

	public SolarPanelLevel getLevelSolarPanel()
	{
		return this.levelSolarPanel;
	}
	
	//TODO: 1.14
	/*@Override
	public int getItemsToDropCount(IBlockState state, int fortune, World worldIn, BlockPos pos, Random random)
	{
		return 1;
	}*/
	
	
	@Override
	public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return false;
	}

	//TODO: 1.14
	/*@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}*/
	
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return BOX;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return BOX;
	}
	
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(worldIn.isRemote)
			return true;

		/*if(playerIn.isSneaking())
		{
			if(ItemStack.areItemsEqual(playerIn.getHeldItemMainhand(), ItemWrench.wrenchBasic))
			{
				dismantleBlock(worldIn, pos);
				return true;
			}
		
		}*/

		TileEntity te = worldIn.getTileEntity(pos);
		if(!(te instanceof INamedContainerProvider))
			return false;

		NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
		return true;
	}

	/*private void dismantleBlock(World worldIn, BlockPos pos)
	{
		ItemStack itemStack = new ItemStack(this);
	
		TileEntitySolarPanel localTileEntity = (TileEntitySolarPanel) worldIn.getTileEntity(pos);
		int internalEnergy = localTileEntity.getEnergy();
		if(internalEnergy > 0)
		{
			if(itemStack.getTag() == null)
			{
				itemStack.setTag(new NBTTagCompound());
			}
			itemStack.getTag().setInt("energy", internalEnergy);
		}
	
		worldIn.removeBlock(pos, false);
	
		EntityItem entityItem = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
		entityItem.motionX = 0;
		entityItem.motionZ = 0;
		worldIn.spawnEntity(entityItem);
	}*/
	
	
	/*@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		TileEntity tileEntity = world.getTileEntity(pos);

		// Always check this!!
		if(tileEntity instanceof IRestorableTileEntity)
		{
			ItemStack stack = new ItemStack(this);
			CompoundNBT tagCompound = new CompoundNBT();
			((IRestorableTileEntity) tileEntity).writeRestorableToNBT(tagCompound);

			stack.setTag(tagCompound);
			drops.add(stack);
		}
		
		return drops;
	}

	@Override
	public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune)
	{
		TileEntity tileEntity = world.getTileEntity(pos);

		// Always check this!!
		if(tileEntity instanceof IRestorableTileEntity)
		{
			ItemStack stack = new ItemStack(this);
			CompoundNBT tagCompound = new CompoundNBT();
			((IRestorableTileEntity) tileEntity).writeRestorableToNBT(tagCompound);

			stack.setTag(tagCompound);
			drops.add(stack);
		}
		else
		{
			super.getDrops(state, drops, world, pos, fortune);
		}
	}*/

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid)
	{
		if(willHarvest)
			return true;
		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}
	
	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack)
	{
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.removeBlock(pos, false);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		TileEntity tileEntity = worldIn.getTileEntity(pos);

		// Always check this!!
		if(tileEntity instanceof IRestorableTileEntity)
		{
			CompoundNBT tagCompound = stack.getTag();
			if(tagCompound != null)
			{
				((IRestorableTileEntity) tileEntity).readRestorableFromNBT(tagCompound);
			}
		}
	}
	
	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		switch (this.levelSolarPanel)
		{
			case Leadstone:
				return new TileEntityLeadstoneSolarPanel();
			case Hardened:
				return new TileEntityHardenedSolarPanel();
			case Redstone:
				return new TileEntityRedstoneSolarPanel();
			case Signalum:
				return new TileEntitySignalumSolarPanel();
			case Resonant:
				return new TileEntityResonantSolarPanel();
			case Advanced:
				return new TileEntityAdvancedSolarPanel();
			case Ultimate:
				return new TileEntityUltimateSolarPanel();
			default:
				return null;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT tagCompound = stack.getTag();
		int energy = 0;
		if(tagCompound != null)
			energy = tagCompound.getInt("energy");
		Tooltip.showInfoCtrl(energy, tooltip);
		Tooltip.showInfoShift(this.levelSolarPanel, tooltip);
	}
}
