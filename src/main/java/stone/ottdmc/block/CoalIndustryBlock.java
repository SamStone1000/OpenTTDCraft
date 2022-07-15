package stone.ottdmc.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import stone.ottdmc.block.entity.CoalIndustryBlockEntity;

public class CoalIndustryBlock extends IndustryBlock {

	public CoalIndustryBlock(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BlockEntity createBlockEntity(BlockView var1) {
		return new CoalIndustryBlockEntity();
	}

}
