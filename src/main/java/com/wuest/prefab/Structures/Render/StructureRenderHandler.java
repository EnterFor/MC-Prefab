package com.wuest.prefab.Structures.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import com.wuest.prefab.Gui.GuiLangKeys;
import com.wuest.prefab.Proxy.CommonProxy;
import com.wuest.prefab.Structures.Base.BuildBlock;
import com.wuest.prefab.Structures.Base.Structure;
import com.wuest.prefab.Structures.Config.StructureConfiguration;
import javafx.geometry.Pos;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.ILightReader;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author WuestMan
 * This class was derived from Botania's MultiBlockRenderer.
 * Most changes are for extra comments for myself as well as to use my blocks class structure.
 * http://botaniamod.net/license.php
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class StructureRenderHandler {
	// player's overlapping on structures and other things.
	public static StructureConfiguration currentConfiguration;
	public static Structure currentStructure;
	public static Direction assumedNorth;
	public static boolean rendering = false;
	public static boolean showedMessage = false;
	private static int dimension;

	/**
	 * Resets the structure to show in the world.
	 *
	 * @param structure     The structure to show in the world, pass null to clear out the client.
	 * @param assumedNorth  The assumed norther facing for this structure.
	 * @param configuration The configuration for this structure.
	 */
	public static void setStructure(Structure structure, Direction assumedNorth, StructureConfiguration configuration) {
		StructureRenderHandler.currentStructure = structure;
		StructureRenderHandler.assumedNorth = assumedNorth;
		StructureRenderHandler.currentConfiguration = configuration;
		StructureRenderHandler.showedMessage = false;

		Minecraft mc = Minecraft.getInstance();

		if (mc.world != null) {
			StructureRenderHandler.dimension = mc.world.getDimension().getType().getId();
		}
	}

	/**
	 * This is to render the currently bound structure.
	 *
	 * @param player The player to render the structure for.
	 * @param src    The ray trace for where the player is currently looking.
	 */
	public static void renderPlayerLook(PlayerEntity player, RayTraceResult src, MatrixStack matrixStack) {
		if (StructureRenderHandler.currentStructure != null
				&& StructureRenderHandler.dimension == player.world.getDimension().getType().getId()
				&& StructureRenderHandler.currentConfiguration != null
				&& CommonProxy.proxyConfiguration.serverConfiguration.enableStructurePreview) {
			rendering = true;
			boolean didAny = false;

			IRenderTypeBuffer.Impl entityVertexConsumer = Minecraft.getInstance().func_228019_au_().func_228489_c_();
			ArrayList<Pair<BlockState, BlockPos>> entityModels = new ArrayList<>();

			for (BuildBlock buildBlock : StructureRenderHandler.currentStructure.getBlocks()) {
				Block foundBlock = Registry.BLOCK.getOrDefault(buildBlock.getResourceLocation());

				if (foundBlock != null) {
					// Get the unique block state for this block.
					BlockState blockState = foundBlock.getDefaultState();
					buildBlock = BuildBlock.SetBlockState(
							StructureRenderHandler.currentConfiguration,
							player.world,
							StructureRenderHandler.currentConfiguration.pos,
							StructureRenderHandler.assumedNorth,
							buildBlock,
							foundBlock,
							blockState,
							StructureRenderHandler.currentStructure);

					// In order to get the proper relative position I also need the structure's original facing.
					BlockPos pos = buildBlock.getStartingPosition().getRelativePosition(
							StructureRenderHandler.currentConfiguration.pos,
							StructureRenderHandler.currentStructure.getClearSpace().getShape().getDirection(),
							StructureRenderHandler.currentConfiguration.houseFacing);

					BlockRenderType blockRenderType = blockState.getRenderType();

					if (blockRenderType == BlockRenderType.ENTITYBLOCK_ANIMATED) {
						entityModels.add(new Pair<>(buildBlock.getBlockState(), pos));
						continue;
					}

					if (StructureRenderHandler.renderComponentInWorld(player.world, buildBlock, entityVertexConsumer, matrixStack, pos, blockRenderType)) {
						didAny = true;
					}
				}
			}

			for (Pair<BlockState, BlockPos> pair : entityModels)
			{
				StructureRenderHandler.renderBlock(matrixStack, new Vec3d(pair.getValue()), pair.getKey(), entityVertexConsumer, BlockRenderType.ENTITYBLOCK_ANIMATED);
			}

			ShaderHelper.useShader(ShaderHelper.alphaShader, shader -> {
				// getUniformLocation
				int alpha = GlStateManager.func_227680_b_(shader, "alpha");
				ShaderHelper.FLOAT_BUF.position(0);
				ShaderHelper.FLOAT_BUF.put(0, 0.4F);

				// uniform1
				GlStateManager.func_227681_b_(alpha, ShaderHelper.FLOAT_BUF);
			});

			// Draw function.
			entityVertexConsumer.func_228461_a_();

			ShaderHelper.releaseShader();

			if (!didAny) {
				// Nothing was generated, tell the user this through a chat message and re-set the structure information.
				StructureRenderHandler.setStructure(null, Direction.NORTH, null);
				player.sendMessage(
						new TranslationTextComponent(GuiLangKeys.GUI_PREVIEW_COMPLETE)
								.setStyle(new Style().setColor(TextFormatting.GREEN)));
			} else if (!StructureRenderHandler.showedMessage) {
				player.sendMessage(new TranslationTextComponent(GuiLangKeys.GUI_PREVIEW_NOTICE).setStyle(new Style().setColor(TextFormatting.GREEN)));
				StructureRenderHandler.showedMessage = true;
			}
		}
	}

	private static boolean renderComponentInWorld(World world, BuildBlock buildBlock, IRenderTypeBuffer entityVertexConsumer, MatrixStack matrixStack, BlockPos pos, BlockRenderType blockRenderType) {
		// Don't render this block if it's going to overlay a non-air/water block.
		BlockState targetBlock = world.getBlockState(pos);
		if (targetBlock.getMaterial() != Material.AIR && targetBlock.getMaterial() != Material.WATER) {
			return false;
		}

		StructureRenderHandler.doRenderComponent(buildBlock, pos, entityVertexConsumer, matrixStack, blockRenderType);

		if (buildBlock.getSubBlock() != null) {
			Block foundBlock = Registry.BLOCK.getOrDefault(buildBlock.getSubBlock().getResourceLocation());
			BlockState blockState = foundBlock.getDefaultState();

			BuildBlock subBlock = BuildBlock.SetBlockState(
					StructureRenderHandler.currentConfiguration,
					world, StructureRenderHandler.currentConfiguration.pos,
					assumedNorth,
					buildBlock.getSubBlock(),
					foundBlock,
					blockState,
					StructureRenderHandler.currentStructure);

			BlockPos subBlockPos = subBlock.getStartingPosition().getRelativePosition(
					StructureRenderHandler.currentConfiguration.pos,
					StructureRenderHandler.currentStructure.getClearSpace().getShape().getDirection(),
					StructureRenderHandler.currentConfiguration.houseFacing);

			BlockRenderType subBlockRenderType = subBlock.getBlockState().getRenderType();

			return StructureRenderHandler.renderComponentInWorld(world, subBlock, entityVertexConsumer, matrixStack, subBlockPos, subBlockRenderType);
		}

		return true;
	}

	private static void doRenderComponent(BuildBlock buildBlock, BlockPos pos, IRenderTypeBuffer entityVertexConsumer, MatrixStack matrixStack, BlockRenderType blockRenderType) {
		BlockState state = buildBlock.getBlockState();
		StructureRenderHandler.renderBlock(matrixStack, new Vec3d(pos), state, entityVertexConsumer, blockRenderType);
	}

	private static void renderBlock(MatrixStack matrixStack, Vec3d pos, BlockState state, IRenderTypeBuffer entityVertexConsumer, BlockRenderType blockRenderType) {
		Minecraft minecraft = Minecraft.getInstance();
		Vec3d projectedView = minecraft.getRenderManager().info.getProjectedView();
		double renderPosX = projectedView.getX();
		double renderPosY = projectedView.getY();
		double renderPosZ = projectedView.getZ();

		// push
		matrixStack.func_227860_a_();

		// Translate function.
		matrixStack.func_227861_a_(-renderPosX, -renderPosY, -renderPosZ);

		BlockRendererDispatcher renderer = minecraft.getBlockRendererDispatcher();

		matrixStack.func_227861_a_(pos.getX(), pos.getY(), pos.getZ() + 1);

		ClientWorld world = Minecraft.getInstance().world;
		IModelData model = renderer.getModelForState(state).getModelData(world, new BlockPos(pos), state, ModelDataManager.getModelData(world, new BlockPos(pos)));
		IBakedModel bakedModel = renderer.getModelForState(state);

		// getColor function.
		int color = minecraft.getBlockColors().func_228054_a_(state, null, null, 0);
		float r = (float)(color >> 16 & 255) / 255.0F;
		float g = (float)(color >> 8 & 255) / 255.0F;
		float b = (float)(color & 255) / 255.0F;

		if (blockRenderType == BlockRenderType.MODEL) {
			renderer.getBlockModelRenderer().func_228804_a_(
					matrixStack.func_227866_c_(),
					entityVertexConsumer.getBuffer(Atlases.func_228784_i_()),
					state,
					bakedModel,
					r,
					g,
					b,
					0xF000F0,
					OverlayTexture.field_229196_a_);
		}
		else if (blockRenderType == BlockRenderType.ENTITYBLOCK_ANIMATED) {
			// TODO: render the animated (chests) after all normal model blocks are rendered.
			renderer.renderBlock(
					state,
					matrixStack,
					entityVertexConsumer,
					15728880,
					OverlayTexture.field_229196_a_,
					model);
		}

		// pop
		matrixStack.func_227865_b_();
	}

	private  static void  renderModel(MatrixStack matrixStack, Vec3d pos, BlockState state, IRenderTypeBuffer entityVertexConsumer) {

	}


}
