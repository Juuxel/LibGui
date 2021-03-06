package io.github.cottonmc.cotton.gui.widget;

import io.github.cottonmc.cotton.gui.client.LibGuiClient;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.data.Alignment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
public class WLabel extends WWidget {
	protected Text text;
	protected Alignment alignment = Alignment.LEFT;
	protected int color;
	protected int darkmodeColor;

	public static final int DEFAULT_TEXT_COLOR = 0x404040;
	public static final int DEFAULT_DARKMODE_TEXT_COLOR = 0xbcbcbc;

	public WLabel(String text, int color) {
		this(new LiteralText(text), color);
	}
	
	public WLabel(Text text, int color) {
		this.text = text;
		this.color = color;
		this.darkmodeColor = (color==DEFAULT_TEXT_COLOR) ? DEFAULT_DARKMODE_TEXT_COLOR : color;
	}

	public WLabel(String text) {
		this(text, DEFAULT_TEXT_COLOR);
	}

	@Override
	public void paintBackground(int x, int y) {
		String translated = text.asFormattedString();
		ScreenDrawing.drawString(translated, alignment, x, y, this.getWidth(), LibGuiClient.config.darkMode ? darkmodeColor : color);
	}

	@Override
	public boolean canResize() {
		return true;
	}
	
	@Override
	public void setSize(int x, int y) {
		super.setSize(x, 20);
	}
	
	public WLabel setDarkmodeColor(int color) {
		darkmodeColor = color;
		return this;
	}
	
	public WLabel disableDarkmode() {
		this.darkmodeColor = this.color;
		return this;
	}
	
	public WLabel setColor(int color, int darkmodeColor) {
		this.color = color;
		this.darkmodeColor = darkmodeColor;
		return this;
	}
	
	public WLabel setText(Text text) {
		this.text = text;
		return this;
	}
	
	public WLabel setAlignment(Alignment align) {
		this.alignment = align;
		return this;
	}
}