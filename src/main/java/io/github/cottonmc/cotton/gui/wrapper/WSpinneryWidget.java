package io.github.cottonmc.cotton.gui.wrapper;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WSize;

/**
 * A Spinnery -> LibGui wrapper.
 */
public class WSpinneryWidget extends WWidget {
	private final WInterface widget;

	public WSpinneryWidget(WInterface widget) {
		this.widget = widget;
	}

	@Override
	public boolean canResize() {
		return true;
	}

	@Override
	public boolean canFocus() {
		return true;
	}

	@Override
	public int getWidth() {
		return widget.getWidth();
	}

	@Override
	public int getHeight() {
		return widget.getHeight();
	}

	@Override
	public void setSize(int x, int y) {
		widget.setSize(WSize.of(x, y));
	}

	@Override
	public void tick() {
		widget.tick();
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.tick();
		}
	}

	@Override
	public WWidget onMouseDown(int x, int y, int button) {
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.onMouseClicked(x, y, button);
		}
		return this;
	}

	@Override
	public void onMouseMove(int x, int y) {
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.scanFocus(x, y);
			child.onMouseMoved(x, y);
		}
	}

	@Override
	public void onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.onMouseDragged(x, y, button, deltaX, deltaY);
		}
	}

	@Override
	public WWidget onMouseUp(int x, int y, int button) {
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.onMouseReleased(x, y, button);
		}
		return this;
	}

	@Override
	public void onMouseScroll(int x, int y, double amount) {
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.onMouseScrolled(x, y, amount);
		}
	}

	@Override
	public void onClick(int x, int y, int button) {
		requestFocus();
	}

	@Override
	public void onCharTyped(char ch) {
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.onCharTyped(ch);
		}
	}

	@Override
	public void onKeyPressed(int ch, int key, int modifiers) {
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.onKeyPressed(key, ch, modifiers);
		}
	}

	@Override
	public void onKeyReleased(int ch, int key, int modifiers) {
		for (spinnery.widget.WWidget child : widget.getWidgets()) {
			child.onKeyReleased(key);
		}
	}

	@Override
	public void paintBackground(int x, int y, int mouseX, int mouseY) {
		RenderSystem.pushMatrix();
		RenderSystem.translatef(x, y, 0);
		widget.draw();
		RenderSystem.popMatrix();
	}
}
