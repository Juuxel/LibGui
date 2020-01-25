package io.github.cottonmc.cotton.gui.wrapper;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import spinnery.common.BaseContainer;
import spinnery.widget.WInterface;
import spinnery.widget.WPosition;
import spinnery.widget.WSize;
import spinnery.widget.WWidget;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WBetterInterface extends WInterface {
	@Environment(EnvType.CLIENT)
	@Nullable
	private BackgroundPainter backgroundPainter = null;

	private final WSpinneryWidget paintingDelegate = new WSpinneryWidget(this);

	public WBetterInterface(BaseContainer linkedContainer) {
		super(linkedContainer);
	}

	public WBetterInterface(WPosition position) {
		super(position);
	}

	public WBetterInterface(WPosition position, WSize size) {
		super(position, size);
	}

	public WBetterInterface(WPosition position, WSize size, BaseContainer linkedContainer) {
		super(position, size, linkedContainer);
	}

	@Environment(EnvType.CLIENT)
	@Nullable
	public BackgroundPainter getBackgroundPainter() {
		return backgroundPainter;
	}

	@Environment(EnvType.CLIENT)
	public void setBackgroundPainter(@Nullable BackgroundPainter backgroundPainter) {
		this.backgroundPainter = backgroundPainter;
	}

	public void setBackgroundPainter(Supplier<BackgroundPainter> painterSupplier) {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			setBackgroundPainter(painterSupplier.get());
		}
	}

	@Override
	public void draw() {
		if (backgroundPainter != null) {
			backgroundPainter.paintBackground(getX(), getY(), paintingDelegate);
		}

		for (WWidget widget : getWidgets()) {
			widget.draw();
		}
	}
}
