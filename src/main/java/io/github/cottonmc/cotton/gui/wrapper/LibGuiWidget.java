package io.github.cottonmc.cotton.gui.wrapper;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;

/**
 * A LibGui -> vanilla wrapper.
 */
public class LibGuiWidget implements Drawable, Element {
	private final int x;
	private final int y;
	private final WWidget widget;

	public LibGuiWidget(int x, int y, WWidget widget) {
		this.x = x;
		this.y = y;
		this.widget = widget;
	}

	public LibGuiWidget(int x, int y, int width, int height, WWidget widget) {
		this.x = x;
		this.y = y;
		this.widget = widget;
		if (widget.canResize()) {
			widget.setSize(width, height);
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float delta) {
		widget.paintBackground(x, y, mouseX, mouseY);
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		widget.onMouseMove((int) (mouseX - x), (int) (mouseY - y));
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		widget.onMouseDown((int) (mouseX - x), (int) (mouseY - y), button);
		return true;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		widget.onMouseUp((int) (mouseX - x), (int) (mouseY - y), button);
		widget.onClick((int) (mouseX - x), (int) (mouseY - y), button);
		return true;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		widget.onMouseDrag((int) (mouseX - x), (int) (mouseY - y), button, deltaX, deltaY);
		return true;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		widget.onMouseScroll((int) (mouseX - x), (int) (mouseY - y), amount);
		return true;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		widget.onKeyPressed(keyCode, scanCode, modifiers);
		return true;
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		widget.onKeyReleased(keyCode, scanCode, modifiers);
		return true;
	}

	@Override
	public boolean charTyped(char chr, int keyCode) {
		widget.onCharTyped(chr);
		return true;
	}

	@Override
	public boolean changeFocus(boolean lookForwards) {
		return false;
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return widget.isWithinBounds((int) (mouseX - x), (int) (mouseY - y));
	}
}
