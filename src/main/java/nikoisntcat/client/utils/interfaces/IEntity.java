package nikoisntcat.client.utils.interfaces;

import net.minecraft.util.math.Vec3d;

public interface IEntity {
    Vec3d getAllowedMovement(Vec3d movement);
}