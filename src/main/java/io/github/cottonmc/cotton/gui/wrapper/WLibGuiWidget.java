package io.github.cottonmc.cotton.gui.wrapper;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import spinnery.widget.WAnchor;
import spinnery.widget.WInterface;

/**
 * A LibGui -> Spinnery wrapper.
 */
public class WLibGuiWidget extends spinnery.widget.WWidget {
	private final WWidget widget;

	public WLibGuiWidget(WAnchor anchor, int positionX, int positionY, int positionZ, WWidget widget, WInterface linkedPanel) {
		this.linkedPanel = linkedPanel;
		this.widget = widget;
		setAnchor(anchor);

		setAnchoredPositionX(positionX);
		setAnchoredPositionY(positionY);
		setPositionZ(positionZ);
		setTheme("default");

		setSizeX(widget.getWidth());
		setSizeY(widget.getHeight());
	}

	@Override
	public double getSizeX() {
		return widget.getWidth();
	}

	@Override
	public double getSizeY() {
		return widget.getHeight();
	}

	@Override
	public void setSizeX(double sizeX) {
		super.setSizeX(sizeX);
		widget.setSize((int) sizeX, widget.getHeight());
	}

	@Override
	public void setSizeY(double sizeY) {
		super.setSizeY(sizeY);
		widget.setSize(widget.getWidth(), (int) sizeY);
	}

	@Override
	public void onCharTyped(char character) {
		super.onCharTyped(character);
		widget.onCharTyped(character);
	}

	@Override
	public void onKeyPressed(int keyPressed, int character, int keyModifier) {
		super.onKeyPressed(keyPressed, character, keyModifier);
		widget.onKeyPressed(character, keyPressed, keyModifier);
	}

	@Override
	public void onKeyReleased(int keyReleased) {
		super.onKeyReleased(keyReleased);
		widget.onKeyReleased(0, keyReleased, 0);
	}

	@Override
	public void onMouseReleased(double mouseX, double mouseY, int mouseButton) {
		super.onMouseReleased(mouseX, mouseY, mouseButton);
		int mx = (int) (mouseX - positionX);
		int my = (int) (mouseY - positionY);
		widget.onMouseUp(mx, my, mouseButton);
		widget.onClick(mx, my, mouseButton);
	}

	@Override
	public void onMouseClicked(double mouseX, double mouseY, int mouseButton) {
		super.onMouseClicked(mouseX, mouseY, mouseButton);
		int mx = (int) (mouseX - positionX);
		int my = (int) (mouseY - positionY);
		widget.onMouseDown(mx, my, mouseButton);
	}

	@Override
	public void onMouseDragged(double mouseX, double mouseY, int mouseButton, double dragOffsetX, double dragOffsetY) {
		super.onMouseDragged(mouseX, mouseY, mouseButton, dragOffsetX, dragOffsetY);
		int mx = (int) (mouseX - positionX);
		int my = (int) (mouseY - positionY);
		widget.onMouseDrag(mx, my, mouseButton, dragOffsetX, dragOffsetY);
	}

	@Override
	public void onMouseMoved(double mouseX, double mouseY) {
		super.onMouseMoved(mouseX, mouseY);
		int mx = (int) (mouseX - positionX);
		int my = (int) (mouseY - positionY);
		widget.onMouseMove(mx, my);
	}

	@Override
	public void onMouseScrolled(double mouseX, double mouseY, double mouseZ) {
		super.onMouseScrolled(mouseX, mouseY, mouseZ);
		int mx = (int) (mouseX - positionX);
		int my = (int) (mouseY - positionY);
		widget.onMouseScroll(mx, my, mouseZ);
	}

	@Override
	public void tick() {
		widget.tick();
	}

	@Override
	public void draw() {
		widget.paintBackground((int) getPositionX(), (int) getPositionY(), -1, -1);
	}
}
