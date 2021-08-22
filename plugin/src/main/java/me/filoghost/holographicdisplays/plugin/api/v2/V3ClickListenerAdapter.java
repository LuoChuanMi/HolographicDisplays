/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.api.v2;

import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import me.filoghost.holographicdisplays.api.hologram.line.ClickListener;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class V3ClickListenerAdapter implements ClickListener {

    private final TouchHandler v2TouchHandler;

    public V3ClickListenerAdapter(TouchHandler v2TouchHandler) {
        this.v2TouchHandler = v2TouchHandler;
    }

    @Override
    public void onClick(@NotNull Player player) {
        v2TouchHandler.onTouch(player);
    }

    public TouchHandler getV2TouchHandler() {
        return v2TouchHandler;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof V3ClickListenerAdapter)) {
            return false;
        }

        V3ClickListenerAdapter other = (V3ClickListenerAdapter) obj;
        return this.v2TouchHandler.equals(other.v2TouchHandler);
    }

    @Override
    public final int hashCode() {
        return v2TouchHandler.hashCode();
    }

    @Override
    public final String toString() {
        return v2TouchHandler.toString();
    }

}
