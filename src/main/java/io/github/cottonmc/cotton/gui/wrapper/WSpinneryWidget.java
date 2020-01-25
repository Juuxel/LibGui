package io.github.cottonmc.cotton.gui.wrapper;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WInterfaceHolder;
import spinnery.widget.WSize;

/**
 * A Spinnery -> LibGui wrapper.
 */
public class WSpinneryWidget extends WWidget {
	private final WInterface widget;
	private final WInterfaceHolder holder;

	public WSpinneryWidget(WInterface widget) {
		this.widget = widget;
		this.holder = new WInterfaceHolder();
		holder.add(widget);
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
		holder.tick();
	}

	@Override
	public WWidget onMouseDown(int x, int y, int button) {
		holder.onMouseClicked(x, y, button);
		return this;
	}

	@Override
	public void onMouseMove(int x, int y) {
		holder.mouseMoved(x, y);
	}

	@Override
	public void onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
		holder.onMouseDragged(x, y, button, (int) deltaX, (int) deltaY);
	}

	@Override
	public WWidget onMouseUp(int x, int y, int button) {
		holder.onMouseReleased(x, y, button);
		return this;
	}

	@Override
	public void onMouseScroll(int x, int y, double amount) {
		holder.onMouseScrolled(x, y, amount);
	}

	@Override
	public void onClick(int x, int y, int button) {
		requestFocus();
	}

	@Override
	public void onCharTyped(char ch) {
		holder.onCharTyped(ch, 0);
	}

	@Override
	public void onKeyPressed(int ch, int key, int modifiers) {
		holder.keyPressed(key, ch, modifiers);
	}

	@Override
	public void onKeyReleased(int ch, int key, int modifiers) {
		holder.onKeyReleased(ch, key, modifiers);
	}

	@Override
	public void paintBackground(int x, int y, int mouseX, int mouseY) {
		RenderSystem.pushMatrix();
		RenderSystem.translatef(x, y, 0);
		widget.draw();
		RenderSystem.popMatrix();
	}
}
