package nikoisntcat.client.modules.impl.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.events.impl.Render3DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.modules.impl.combat.KillAuraModule;
import nikoisntcat.client.screens.EaseInOutCubicTween;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.RotationUtil;
import nikoisntcat.client.utils.math.Tween;
import nikoisntcat.client.utils.math.TweenState;
import nikoisntcat.client.utils.render.RenderUtil;
import org.joml.Vector2f;

public class NameTagsModule extends Module {
    private final List<AbstractClientPlayerEntity> playersWithNameTags;
    private final Map<Entity, Vec3d> entityRenderPositions = new HashMap<>();
    private final Map<Entity, Tween> entityTweens = new HashMap<>();

    public NameTagsModule() {
        super("NameTags", 0, Category.RENDER);
        this.playersWithNameTags = new ArrayList<>();
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (PlayerUtil.nullCheck()) return;

        float delta = event.method1423();
        Vec3d playerInterpolatedPos = RenderUtil.method1570(mc.player, delta);

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof LivingEntity livingEntity)) continue;
            if (!PlayerUtil.isValidTarget(livingEntity)) continue;

            try {
                float alpha = -1.0F;
                LivingEntity mainTarget = KillAuraModule.target;
                LivingEntity secondaryTarget = KillAuraModule.secondaryTarget;
                Vec3d renderPos;

                if (entity == mainTarget) {
                    Tween tween = entityTweens.computeIfAbsent(entity, e -> new EaseInOutCubicTween(1500, 1.0));
                    if (tween.method1760(TweenState.SECOND)) tween.setType(TweenState.FIRST);
                    alpha = tween.get().floatValue();

                    if (secondaryTarget != null && secondaryTarget != entity) {
                        tween.timer.setCurrentMs(0L);
                        entityRenderPositions.remove(entity);
                        alpha = 1.0F;
                    }

                    Vec3d playerPrevInterpolated = RenderUtil.method1569(
                            new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ),
                            mc.player.getPos(),
                            delta
                    );

                    float fovMultiplier = mc.player.getFovMultiplier(true, mc.options.getFovEffectScale().getValue().floatValue());
                    Vec3d offset = RotationUtil.method1526(new Vector2f(mc.player.getYaw(delta), mc.player.getPitch(delta) - 25.0F));
                    Vec3d targetPos = playerPrevInterpolated.add(offset.multiply(3.0 / fovMultiplier));

                    Vec3d previousPos = entityRenderPositions.getOrDefault(entity, targetPos);
                    renderPos = RenderUtil.method1569(previousPos, targetPos, tween.get());
                    entityRenderPositions.put(entity, renderPos);

                    if (entity instanceof AbstractClientPlayerEntity playerEntity) {
                        playersWithNameTags.add(playerEntity);
                    }

                } else {
                    float fadeFactor = 1.0F;
                    Tween tween = entityTweens.get(entity);

                    if (tween != null) {
                        if (tween.getTweenType() == TweenState.FIRST) {
                            tween.setType(TweenState.SECOND);
                            tween.update();
                        }
                        fadeFactor = 1.0F - tween.get().floatValue();
                    } else if (mainTarget == null) {
                        EaseInOutCubicTween newTween = new EaseInOutCubicTween(1000, 1.0);
                        newTween.setType(TweenState.SECOND);
                        entityTweens.put(entity, newTween);
                        fadeFactor = 1.0F - newTween.get().floatValue();
                    }

                    Vec3d entityPrevInterpolated = RenderUtil.method1569(
                            new Vec3d(entity.prevX, entity.prevY + livingEntity.getStandingEyeHeight(), entity.prevZ),
                            entity.getPos().add(0.0, livingEntity.getStandingEyeHeight(), 0.0),
                            delta
                    );

                    Vec3d previousPos = entityRenderPositions.getOrDefault(entity, entityPrevInterpolated);
                    renderPos = RenderUtil.method1569(previousPos, entityPrevInterpolated, fadeFactor);
                    entityRenderPositions.put(entity, renderPos);

                    if (entity instanceof AbstractClientPlayerEntity playerEntity && playersWithNameTags.contains(entity)) {
                        alpha = 1.0F - fadeFactor;
                        if (alpha <= 0.001F) playersWithNameTags.remove(playerEntity);
                    }
                }

                double distanceSquared = playerInterpolatedPos.squaredDistanceTo(renderPos);
                float scale = (float) Math.max(2.0, Math.sqrt(distanceSquared / 7.5));

                RenderUtil.method1575(event.method1427(), renderPos.x, renderPos.y, renderPos.z, livingEntity, scale, null, alpha);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onTick() {
    }
}
