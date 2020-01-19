package io.github.cottonmc.cotton.gui.wrapper;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;

/**
 * A vanilla -> LibGui wrapper.
 */
public class WVanillaWidget<W extends Drawable & Element> extends WWidget {
	private final W widget;

	public WVanillaWidget(W widget) {
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
	public void onClick(int x, int y, int button) {
		if (host != null) { // If inside an actual LibGui gui, not Spinnery
			requestFocus();
		}
	}

	@Override
	public WWidget onMouseDown(int x, int y, int button) {
		widget.mouseClicked(x, y, button);
		return this;
	}

	@Override
	public WWidget onMouseUp(int x, int y, int button) {
		widget.mouseReleased(x, y, button);
		return this;
	}

	@Override
	public void onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
		widget.mouseDragged(x, y, button, deltaX, deltaY);
	}

	@Override
	public void onMouseScroll(int x, int y, double amount) {
		widget.mouseScrolled(x, y, amount);
	}

	@Override
	public void onMouseMove(int x, int y) {
		widget.mouseMoved(x, y);
	}

	@Override
	public void onCharTyped(char ch) {
		widget.charTyped(ch, 0);
	}

	@Override
	public void onKeyPressed(int ch, int key, int modifiers) {
		widget.keyPressed(ch, key, modifiers);
	}

	@Override
	public void onKeyReleased(int ch, int key, int modifiers) {
		widget.keyReleased(ch, key, modifiers);
	}

	@Override
	public void paintBackground(int x, int y, int mouseX, int mouseY) {
		RenderSystem.pushMatrix();
		RenderSystem.translatef(x, y, 0);
		widget.render(mouseX, mouseY, 0);
		RenderSystem.popMatrix();
	}
}
