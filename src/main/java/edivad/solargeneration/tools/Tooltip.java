package edivad.solargeneration.tools;

import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class Tooltip {

	private static final Pattern COMPILE = Pattern.compile("@", Pattern.LITERAL);

	public static void showInfoShift(SolarPanelLevel solarPanelLevel, List<ITextComponent> tooltip)
	{
		if(GuiScreen.isShiftKeyDown())
		{
			int generation = (int) Math.pow(8, solarPanelLevel.ordinal());
			int transfer = ((int) Math.pow(8, solarPanelLevel.ordinal())) * 2;
			int capacity = ((int) Math.pow(8, solarPanelLevel.ordinal())) * 1000;
		
			addInformationLocalized(tooltip, "message.solargeneration.shift_info", generation, transfer, capacity);
		}
		else
			addInformationLocalized(tooltip, "message.solargeneration.hold_shift");
	}

	public static void showInfoCtrl(int energy, List<ITextComponent> tooltip)
	{
		if(GuiScreen.isCtrlKeyDown())
			addInformationLocalized(tooltip, "message.solargeneration.ctrl_info", energy);
		else
			addInformationLocalized(tooltip, "message.solargeneration.hold_ctrl");
	}

	private static void addInformationLocalized(List<ITextComponent> tooltip, String key, Object... parameters)
	{
		String translated = I18n.format(key, parameters);
		translated = COMPILE.matcher(translated).replaceAll("\u00a7");
		String[] formatted = translated.split("\n");
		for(String line : formatted)
			tooltip.add(new TextComponentTranslation(line));
	}
}
