package apollointhehouse.noburnout.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockRedstoneTorch;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(value = BlockRedstoneTorch.class, remap = false)
public abstract class BlockRedstoneTorchMixin {
	@Shadow
	protected abstract boolean isPoweredByBlock(World world, int i, int j, int k);

	@Shadow
	private boolean torchActive;

	/**
	 * @author Apollointhehouse
	 * @reason Removes redstone torch burnout
	 */
	@Overwrite
	public void updateTick(World world, int x, int y, int z, Random rand) {
		boolean isPowered = this.isPoweredByBlock(world, x, y, z);

		if (isPowered && torchActive) {
			world.setBlockAndMetadataWithNotify(x, y, z, Block.torchRedstoneIdle.id, world.getBlockMetadata(x, y, z));
		} else if (!torchActive && !isPowered) {
			world.setBlockAndMetadataWithNotify(x, y, z, Block.torchRedstoneActive.id, world.getBlockMetadata(x, y, z));
		}
	}
}
