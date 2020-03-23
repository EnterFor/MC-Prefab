package com.wuest.prefab.Structures.Gui;

import com.wuest.prefab.Events.ClientEventHandler;
import com.wuest.prefab.Gui.GuiLangKeys;
import com.wuest.prefab.Structures.Config.BulldozerConfiguration;
import com.wuest.prefab.Structures.Messages.StructureTagMessage.EnumStructureConfiguration;
import com.wuest.prefab.Tuple;
import javafx.util.Pair;
import net.minecraft.client.gui.widget.button.AbstractButton;

/**
 * @author WuestMan
 */
public class GuiBulldozer extends GuiStructure {

	protected BulldozerConfiguration configuration;

	/**
	 * Initializes a new instance of the {@link GuiBulldozer} class.
	 */
	public GuiBulldozer() {
		super("Bulldozer");

		this.structureConfiguration = EnumStructureConfiguration.Bulldozer;
	}

	@Override
	protected void Initialize() {
		this.configuration = ClientEventHandler.playerConfig.getClientConfig("Bulldozer", BulldozerConfiguration.class);
		this.configuration.pos = this.pos;

		// Get the upper left hand corner of the GUI box.
		int grayBoxX = this.getCenteredXAxis() - 125;
		int grayBoxY = this.getCenteredYAxis() - 83;

		// Create the done and cancel buttons.
		this.btnBuild = this.createAndAddButton(grayBoxX + 10, grayBoxY + 136, 90, 20, GuiLangKeys.translateString(GuiLangKeys.GUI_BUTTON_BUILD));

		this.btnCancel = this.createAndAddButton(grayBoxX + 147, grayBoxY + 136, 90, 20, GuiLangKeys.translateString(GuiLangKeys.GUI_BUTTON_CANCEL));
	}

	@Override
	protected Tuple<Integer, Integer> getAdjustedXYValue() {
		return new Tuple<>(this.getCenteredXAxis() - 125, this.getCenteredYAxis() - 83);
	}

	@Override
	protected void postButtonRender(int x, int y) {
		this.minecraft.fontRenderer.drawSplitString(GuiLangKeys.translateString(GuiLangKeys.GUI_BULLDOZER_DESCRIPTION), x + 10, y + 10, 230, this.textColor);

		this.minecraft.fontRenderer.drawSplitString(GuiLangKeys.translateString(GuiLangKeys.GUI_CLEARED_AREA), x + 10, y + 40, 230, this.textColor);
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
	 */
	@Override
	public void buttonClicked(AbstractButton button) {
		assert this.minecraft != null;
		this.configuration.houseFacing = this.minecraft.player.getHorizontalFacing().getOpposite();
		this.performCancelOrBuildOrHouseFacing(this.configuration, button);
	}

}
