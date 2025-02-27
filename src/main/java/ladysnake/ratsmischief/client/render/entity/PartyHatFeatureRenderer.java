package ladysnake.ratsmischief.client.render.entity;

import com.google.common.collect.Sets;
import ladysnake.ratsmischief.common.Mischief;
import ladysnake.ratsmischief.common.entity.RatEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Set;

public class PartyHatFeatureRenderer extends GeoLayerRenderer<RatEntity> {
    private static final Set<RatEntity.Type> DISALLOWED_TYPES = Sets.immutableEnumSet(RatEntity.Type.RAT_KID, RatEntity.Type.BIGGIE_CHEESE, RatEntity.Type.JORATO);
    private static Identifier[] TEXTURES;

    private final PartyHatEntityRenderer partyHatEntityRenderer;

    public PartyHatFeatureRenderer(IGeoRenderer<RatEntity> entityRendererIn, PartyHatEntityRenderer partyHatEntityRenderer) {
        super(entityRendererIn);
        this.partyHatEntityRenderer = partyHatEntityRenderer;
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, RatEntity ratEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (Mischief.IS_BIRTHDAY && !DISALLOWED_TYPES.contains(ratEntity.getRatType()) && !(ratEntity.hasCustomName() && ratEntity.getCustomName().getString().equalsIgnoreCase("remy"))) {
            if (TEXTURES == null) {
                TEXTURES = new Identifier[RatEntity.PartyHat.values().length];
                for (RatEntity.PartyHat hat : RatEntity.PartyHat.values()) {
                    TEXTURES[hat.ordinal()] = new Identifier(Mischief.MODID, "textures/entity/birthday_hats/" + hat.toString().toLowerCase() + ".png");
                }
            }
            Identifier hatTexture = TEXTURES[ratEntity.getPartyHat().ordinal()];
            partyHatEntityRenderer.render(getEntityModel().getModel(getEntityModel().getModelResource(ratEntity)),
                    ratEntity,
                    partialTicks,
                    RenderLayer.getEntityCutout(hatTexture),
                    matrixStackIn,
                    bufferIn,
                    bufferIn.getBuffer(RenderLayer.getEntityCutout(hatTexture)),
                    packedLightIn, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        }
    }
}
