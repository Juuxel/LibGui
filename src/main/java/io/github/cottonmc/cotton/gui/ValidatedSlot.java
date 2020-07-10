package io.github.cottonmc.cotton.gui;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.impl.access.SlotAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.function.Predicate;

public class ValidatedSlot extends Slot {
	private static final Logger LOGGER = LogManager.getLogger();
	private final int slotNumber;
	// Original positions that will be restored when this slot is reshown
	private final int originalX, originalY;
	private boolean insertingAllowed = true;
	private boolean takingAllowed = true;
	private Predicate<ItemStack> filter;
	protected final Multimap<WItemSlot, WItemSlot.ChangeListener> listeners = HashMultimap.create();
	private boolean visible = true;

	public ValidatedSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
		if (inventory==null) throw new IllegalArgumentException("Can't make an itemslot from a null inventory!");
		this.slotNumber = index;
		this.originalX = x;
		this.originalY = y;
	}
	
	@Override
	public boolean canInsert(ItemStack stack) {
		return insertingAllowed && inventory.isValid(slotNumber, stack) && filter.test(stack);
	}
	
	@Override
	public boolean canTakeItems(PlayerEntity player) {
		return takingAllowed && inventory.canPlayerUse(player);
	}
	
	@Override
	public ItemStack getStack() {
		if (inventory==null) {
			LOGGER.warn("Prevented null-inventory from WItemSlot with slot #: {}", slotNumber);
			return ItemStack.EMPTY;
		}
		
		ItemStack result = super.getStack();
		if (result==null) {
			LOGGER.warn("Prevented null-itemstack crash from: {}", inventory.getClass().getCanonicalName());
			return ItemStack.EMPTY;
		}
		
		return result;
	}

	@Override
	public void markDirty() {
		listeners.forEach((slot, listener) -> listener.onStackChanged(slot, inventory, getInventoryIndex(), getStack()));
		super.markDirty();
	}

	/**
	 * Gets the index of this slot in its inventory.
	 *
	 * @return the inventory index
	 */
	public int getInventoryIndex() {
		return slotNumber;
	}

	/**
	 * Returns whether items can be inserted into this slot.
	 *
	 * @return true if items can be inserted, false otherwise
	 * @since 1.10.0
	 */
	public boolean isInsertingAllowed() {
		return insertingAllowed;
	}

	/**
	 * Sets whether inserting items into this slot is allowed.
	 *
	 * @param insertingAllowed true if items can be inserted, false otherwise
	 * @since 1.10.0
	 */
	public void setInsertingAllowed(boolean insertingAllowed) {
		this.insertingAllowed = insertingAllowed;
	}

	/**
	 * Returns whether items can be taken from this slot.
	 *
	 * @return true if items can be taken, false otherwise
	 * @since 1.10.0
	 */
	public boolean isTakingAllowed() {
		return takingAllowed;
	}

	/**
	 * Sets whether taking items from this slot is allowed.
	 *
	 * @param takingAllowed true if items can be taken, false otherwise
	 * @since 1.10.0
	 */
	public void setTakingAllowed(boolean takingAllowed) {
		this.takingAllowed = takingAllowed;
	}

	/**
	 * Gets the item stack filter of this slot.
	 *
	 * @return the item filter
	 * @since 2.0.0
	 */
	public Predicate<ItemStack> getFilter() {
		return filter;
	}

	/**
	 * Sets the item stack filter of this slot.
	 *
	 * @param filter the new item filter
	 * @since 2.0.0
	 */
	public void setFilter(Predicate<ItemStack> filter) {
		this.filter = filter;
	}

	/**
	 * Adds a change listener to this slot.
	 * Does nothing if the listener is already registered.
	 *
	 * @param owner    the owner of this slot
	 * @param listener the listener
	 * @throws NullPointerException if either parameter is null
	 * @since 3.0.0
	 */
	public void addChangeListener(WItemSlot owner, WItemSlot.ChangeListener listener) {
		Objects.requireNonNull(owner, "owner");
		Objects.requireNonNull(listener, "listener");
		listeners.put(owner, listener);
	}

	/**
	 * Tests whether this slot is visible.
	 *
	 * @return true if this slot is visible, false otherwise
	 * @since 2.3.0
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets whether this slot is visible.
	 *
	 * @param visible true if this slot if visible, false otherwise
	 * @since 2.3.0
	 */
	public void setVisible(boolean visible) {
		if (this.visible != visible) {
			this.visible = visible;

			SlotAccessor accessor = (SlotAccessor) this;
			if (visible) {
				accessor.setX(originalX);
				accessor.setY(originalY);
			} else {
				accessor.setX(-100000);
				accessor.setY(-100000);
			}
		}
	}
}
