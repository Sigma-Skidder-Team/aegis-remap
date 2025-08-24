package nikoisntcat.client.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Class279 {
    public BlockPos field2175;
    public double field2176;
    public Vec3d field2177;
    public boolean field2178;

    public Class279(Vec3d pos, boolean onGround, double fallDistance, BlockPos currentPos) {
        this.field2177 = pos;
        this.field2178 = onGround;
        this.field2176 = fallDistance;
        this.field2175 = currentPos;
    }
}
