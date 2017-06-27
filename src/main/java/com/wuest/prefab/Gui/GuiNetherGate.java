package com.wuest.prefab.Gui;

import java.awt.Color;
import java.io.IOException;

import com.wuest.prefab.Prefab;
import com.wuest.prefab.Config.NetherGateConfiguration;
import com.wuest.prefab.Proxy.Messages.StructureTagMessage;
import com.wuest.prefab.Proxy.Messages.StructureTagMessage.EnumStructureConfiguration;
import com.wuest.prefab.Render.StructureRenderHandler;
import com.wuest.prefab.StructureGen.CustomStructures.StructureNetherGate;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.config.GuiButtonExt;

/**
 * 
 * @author WuestMan
 *
 */
public class GuiNetherGate extends GuiStructure
{
	private static final ResourceLocation structureTopDown = new ResourceLocation("prefab", "textures/gui/nether_gate_top_down.png");
	protected NetherGateConfiguration configuration;
	
	public GuiNetherGate(int x, int y, int z)
	{
		super(x, y, z, true);
		this.structureConfiguration = EnumStructureConfiguration.NetherGate;
	}
	
	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
	 */
	@Override
	public void drawScreen(int x, int y, float f) 
	{
		int grayBoxX = this.getCenteredXAxis() - 213;
		int grayBoxY = this.getCenteredYAxis() - 83;
		
		this.drawDefaultBackground();
		
		// Draw the control background.
		this.mc.getTextureManager().bindTexture(structureTopDown);
		GuiTabScreen.drawModalRectWithCustomSizedTexture(grayBoxX + 250, grayBoxY, 1, 164, 108, 164, 108);
		
		this.drawControlBackgroundAndButtonsAndLabels(grayBoxX, grayBoxY, x, y);

		// Draw the text here.
		this.mc.fontRenderer.drawString(GuiLangKeys.translateString(GuiLangKeys.GUI_STRUCTURE_FACING), grayBoxX + 10, grayBoxY + 10, this.textColor);
		
		// Draw the text here.
		this.mc.fontRenderer.drawSplitString(GuiLangKeys.translateString(GuiLangKeys.GUI_BLOCK_CLICKED), grayBoxX + 147, grayBoxY + 10, 95, this.textColor);
		this.mc.fontRenderer.drawSplitString(GuiLangKeys.translateString(GuiLangKeys.GUI_STRUCTURE_FACING_PLAYER), grayBoxX + 147, grayBoxY + 60, 95, this.textColor);
		
		if (!Prefab.proxy.proxyConfiguration.enableStructurePreview)
		{
			this.btnVisualize.enabled = false;
		}
	}
	
	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
	 */
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		this.performCancelOrBuildOrHouseFacing(this.configuration, button);
		
		if (button == this.btnVisualize)
		{
			StructureNetherGate structure = StructureNetherGate.CreateInstance(StructureNetherGate.ASSETLOCATION, StructureNetherGate.class);
			StructureRenderHandler.setStructure(structure, EnumFacing.NORTH, this.configuration);
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	protected void Initialize() 
	{
		this.configuration = new NetherGateConfiguration();
		this.configuration.pos = this.pos;

		// Get the upper left hand corner of the GUI box.
		int grayBoxX = this.getCenteredXAxis() - 213;
		int grayBoxY = this.getCenteredYAxis() - 83;

		// Create the buttons.
		this.btnHouseFacing = new GuiButtonExt(3, grayBoxX + 10, grayBoxY + 20, 90, 20, GuiLangKeys.translateFacing(this.configuration.houseFacing));
		this.buttonList.add(this.btnHouseFacing);

		this.btnVisualize = new GuiButtonExt(4, grayBoxX + 10, grayBoxY + 50, 90, 20, GuiLangKeys.translateString(GuiLangKeys.GUI_BUTTON_PREVIEW));
		this.buttonList.add(this.btnVisualize);
		
		// Create the done and cancel buttons.
		this.btnBuild = new GuiButtonExt(1, grayBoxX + 10, grayBoxY + 136, 90, 20, GuiLangKeys.translateString(GuiLangKeys.GUI_BUTTON_BUILD));
		this.buttonList.add(this.btnBuild);

		this.btnCancel = new GuiButtonExt(2, grayBoxX + 147, grayBoxY + 136, 90, 20, GuiLangKeys.translateString(GuiLangKeys.GUI_BUTTON_CANCEL));
		this.buttonList.add(this.btnCancel);
	}
}
