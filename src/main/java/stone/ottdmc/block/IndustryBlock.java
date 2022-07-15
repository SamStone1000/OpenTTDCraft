package stone.ottdmc.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;

public abstract class IndustryBlock extends BlockWithEntity {

	public IndustryBlock(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
