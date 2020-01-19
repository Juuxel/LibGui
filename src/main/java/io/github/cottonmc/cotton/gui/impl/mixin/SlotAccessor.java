package io.github.cottonmc.cotton.gui.impl.mixin;

import net.minecraft.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Slot.class)
public interface SlotAccessor {
	@Accessor
	void setXPosition(int xPosition);

	@Accessor
	void setYPosition(int yPosition);
}
