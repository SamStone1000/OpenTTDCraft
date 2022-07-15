package stone.ottdmc.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import stone.ottdmc.block.entity.PowerIndustryBlockEntity;

public class PowerIndustryBlock extends IndustryBlock {

	public PowerIndustryBlock(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BlockEntity createBlockEntity(BlockView var1) {
		return new PowerIndustryBlockEntity();
	}

}
