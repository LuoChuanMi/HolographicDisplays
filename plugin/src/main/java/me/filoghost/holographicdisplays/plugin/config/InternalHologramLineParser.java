/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.config;

import me.filoghost.fcommons.Colors;
import me.filoghost.fcommons.MaterialsHelper;
import me.filoghost.holographicdisplays.core.placeholder.parsing.StringWithPlaceholders;
import me.filoghost.holographicdisplays.plugin.format.DisplayFormat;
import me.filoghost.holographicdisplays.plugin.internal.hologram.InternalHologramLine;
import me.filoghost.holographicdisplays.plugin.internal.hologram.ItemInternalHologramLine;
import me.filoghost.holographicdisplays.plugin.internal.hologram.TextInternalHologramLine;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

public class InternalHologramLineParser {

    private static final String ICON_PREFIX = "icon:";

    public static InternalHologramLine parseLine(String serializedLine) throws InternalHologramLoadException {
        if (serializedLine.toLowerCase(Locale.ROOT).startsWith(ICON_PREFIX)) {
            String serializedIcon = serializedLine.substring(ICON_PREFIX.length());
            ItemStack icon = parseItemStack(serializedIcon);
            return new ItemInternalHologramLine(serializedLine, icon);

        } else {
            String displayText = DisplayFormat.apply(serializedLine, false);
            // Apply colors only outside placeholders
            displayText = StringWithPlaceholders.withEscapes(displayText).replaceStrings(Colors::colorize);
            return new TextInternalHologramLine(serializedLine, displayText);
        }
    }

    private static ItemStack parseItemStack(String serializedItem) throws InternalHologramLoadException {
        serializedItem = serializedItem.trim();

        String basicItemData = serializedItem.replace(" ", "");

        String materialName;
        int custom = 0;

        if (basicItemData.contains(":")) {
            String[] materialAndDataValue = basicItemData.split(":");
            try {
                custom = (short) Integer.parseInt(materialAndDataValue[1]);
            } catch (NumberFormatException e) {
                throw new InternalHologramLoadException("data value \"" + materialAndDataValue[1] + "\" is not a valid number");
            }
            materialName = materialAndDataValue[0];
        } else {
            materialName = basicItemData;
        }

        Material material = MaterialsHelper.matchMaterial(materialName);
        if (material == null) {
            throw new InternalHologramLoadException("\"" + materialName + "\" is not a valid material");
        }

        ItemStack itemStack = new ItemStack(material, 1);

        if (custom != 0) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            assert itemMeta != null;
            itemMeta.setCustomModelData(custom);
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }

}
