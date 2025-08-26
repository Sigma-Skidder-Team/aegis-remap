package nikoisntcat.client.modules.impl.render;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.events.impl.Render3DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ColorSetting;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.math.TimerUtil;

public class BlockESPModule extends Module {
    private final TimerUtil field1649;
    public BooleanSetting field1650;
    private Thread field1651;
    public BooleanSetting field1652 = new BooleanSetting("Bed", false);
    public ColorSetting field1653;
    public final List<BlockPos> field1654;
    public final List<BlockPos> field1655;

    @Override
    public void onTick() {
        if (!PlayerUtil.nullCheck()) {
            if (this.field1649.passed(2000L)) {
                this.field1651 = new Thread(() -> {
                    this.field1655.clear();
                    int var1 = mc.player.getChunkPos().x;
                    int var2 = mc.player.getChunkPos().z;

                    for (int var3 = var1 - 8; var3 <= var1 + 8; var3++) {
                        for (int var4 = var2 - 8; var4 <= var2 + 8; var4++) {
                            mc.world.getChunk(var3, var4).getBlockEntities().forEach((pos, entity) -> {
                                Block var3x = Class228.method1490(pos);
                                if (var3x instanceof BedBlock && this.field1652.getValue() || var3x instanceof ChestBlock && this.field1650.getValue()) {
                                    this.field1655.add(pos);
                                }
                            });
                        }
                    }
                });
                this.field1651.start();
                this.field1649.update();
            }
        }
    }

    @Override
    public void onEnable() {
        this.field1649.setCurrentMs(0L);
    }

    public BlockESPModule() {
        super("BlockESP", 0, false, Category.RENDER);
        this.field1650 = new BooleanSetting("Chest", false);
        this.field1653 = new ColorSetting("Color", true);
        this.field1654 = new ArrayList<>();
        this.field1655 = new ArrayList<>();
        this.field1651 = null;
        this.field1649 = new TimerUtil();
    }

    @Override
    public void onRender3D(Render3DEvent render3DEvent) {
        if (this.field1651 != null && !this.field1651.isAlive()) {
            this.field1654.clear();
            this.field1654.addAll(this.field1655);
        }

        this.field1654.forEach(e -> render3DEvent.method1425(Vec3d.of(e), this.field1653.method1908()));
    }
}
