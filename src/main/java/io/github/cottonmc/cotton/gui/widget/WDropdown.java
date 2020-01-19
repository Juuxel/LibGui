package io.github.cottonmc.cotton.gui.widget;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.data.Alignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

// Inspired by vini2003's Spinnery library
public class WDropdown extends WPanel {
	private WDropdownTitle title;
	private WWidget widget;
	private boolean collapsed = false;

	public WDropdown(Text title, WWidget widget) {
		this.title = new WDropdownTitle(title);
		this.widget = widget;
		add(this.title, 0);
		add(this.widget, 1);
	}

	private void add(WWidget widget, int index) {
		children.add(index, widget);
		widget.parent = this;

		int wy = 0;
		for (WWidget child : children) {
			wy += child.getHeight();
		}

		widget.setLocation(0, wy);
		if (widget.canResize()) {
			widget.setSize(getWidth(), widget.getHeight());
		}
		expandToFit(widget);
	}

	public Text getTitle() {
		return title.label;
	}

	public WDropdown setTitle(Text title) {
		this.title.label = title;
		return this;
	}

	public WWidget getWidget() {
		return widget;
	}

	public WDropdown setWidget(WWidget widget) {
		this.widget.parent = null;
		this.widget = widget;
		add(widget, 1);
		if (collapsed) {
			children.remove(widget);
		}
		return this;
	}

	@Override
	public void setSize(int x, int y) {
		super.setSize(x, y);
		title.setSize(x, 20);
		widget.setSize(x, y - title.getHeight());
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public WDropdown setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
		if (collapsed) {
			getWidget().onHidden();
			children.remove(1);
			super.setSize(width, title.getHeight());
		} else {
			getWidget().onShown();
			children.add(widget);
			super.setSize(width, title.getHeight() + widget.getHeight());
		}
		return this;
	}

	public void toggle() {
		setCollapsed(!isCollapsed());
	}

	protected class WDropdownTitle extends WWidget {
		protected Text label;

		public WDropdownTitle(Text label) {
			this.label = label;
			setSize(4 * 18, 20);
		}

		@Override
		public boolean canResize() {
			return true;
		}

		@Override
		public void setSize(int x, int y) {
			super.setSize(x, 20);
		}

		@Override
		public void onClick(int x, int y, int button) {
			if (isWithinBounds(x, y)) {
				toggle();
				MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			}
		}

		@Override
		public void paintBackground(int x, int y, int mouseX, int mouseY) {
			ScreenDrawing.drawString(label.asFormattedString(), Alignment.CENTER, x, y, width, WLabel.DEFAULT_TEXT_COLOR); // TODO: Color
		}
	}
}
