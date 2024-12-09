/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2024 Grafnus
 */

package grafnus.portalshard.util.skulls;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

/**
 * SkullFactory is a utility class for creating skull items in Minecraft with various textures and player profiles.
 *
 * <p>This class provides methods to generate player heads based on player UUIDs, player names,
 * offline players, and custom textures. It also includes helper methods to parse base64 strings
 * and create random player profiles.
 */
public class SkullFactory {
    /**
     * Get a playerhead of the given player.
     *
     * @param pUUID The UUID of the player.
     * @return A playerhead of the given player if the player can be found, otherwise a default playerhead.
     */
    public static ItemStack getPlayerHead(UUID pUUID) {
        if (pUUID != null) {
            return getPlayerHead(Bukkit.getPlayer(pUUID));
        }
        return new ItemStack(Material.PLAYER_HEAD);
    }

    /**
     * Get a playerhead of the given player by their UUID as a string.
     *
     * @param pStrUUID The UUID of the player as a string.
     * @return A playerhead of the given player if the player can be found, otherwise a default playerhead.
     */
    public static ItemStack getPlayerHead(String pStrUUID) {
        if (pStrUUID != null) {
            return getPlayerHead(UUID.fromString(pStrUUID));
        }
        return new ItemStack(Material.PLAYER_HEAD);
    }

    /**
     * Get a playerhead of the given OfflinePlayer.
     *
     * @param p the OfflinePlayer to get the playerhead from
     * @return a playerhead of the given player if the player can be found, otherwise a default playerhead
     */
    public static ItemStack getPlayerHead(OfflinePlayer p) {
        if (p != null) {
            return getPlayerHead(p.getPlayer());
        }
        return new ItemStack(Material.PLAYER_HEAD);
    }
    /**
     * Get a playerhead of the given player.
     *
     * @param p the player to get the playerhead from
     * @return a playerhead of the given player if the player can be found, otherwise a default playerhead
     */
    public static ItemStack getPlayerHead(Player p) {
        if (p != null) {
            return getPlayerHead(p.getPlayerProfile());
        }
        return new ItemStack(Material.PLAYER_HEAD);
    }

    /**
     * Returns a playerhead ItemStack of the given player profile.
     *
     * @param profile the player profile of the associated player
     * @return a playerhead of the given player if the player can be found, otherwise a default playerhead
     */
    public static ItemStack getPlayerHead(PlayerProfile profile) {

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sMeta = (SkullMeta) head.getItemMeta();
        if (sMeta == null) {
            throw new RuntimeException("Error! SkullMeta of newly created PLAYER_HEAD item is null!");
        }

        sMeta.setOwnerProfile(profile);
        head.setItemMeta(sMeta);
        return head;
    }

    /**
     * Creates a random player profile.
     *
     * <p>This method generates a random UUID and uses it to create a new player profile. The player name is set to the first 16 characters of the UUID.
     *
     * @return a random player profile
     */
    private static PlayerProfile createRandoommPlayerProfile() {
        UUID uuid = UUID.randomUUID();
        return Bukkit.createPlayerProfile(uuid, uuid.toString().substring(0, 16));
    }


    /**
     * Returns a playerhead with a custom texture.
     *
     * <p>Pass a base64 encoded URL of a skin, and this method will create a playerhead with that skin.
     *
     * @param b64Texture the base64 encoded URL of the skin
     * @return the playerhead with the custom skin, or a default one if the input is invalid
     */
    public static ItemStack getCustomTextureSkull(String b64Texture) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta sMeta = (SkullMeta) head.getItemMeta();
        if (sMeta == null) {
            Bukkit.getLogger().warning("This is wierd... SkullMeta of newly created PLAYER_HEAD item is null! Defaulting to Steve. Report this issue!");
            return head;
        }
        PlayerProfile playerProfile = createRandoommPlayerProfile();

        PlayerTextures textures = playerProfile.getTextures();
        URL skinURL;
        try {
            skinURL = getUrlFromBase64(b64Texture);
        } catch (MalformedURLException e) {
            Bukkit.getLogger().warning("Trying to create a Skull with an invalid Base64 skin data! Defaulting to Steve!");
            return head;
        }

        textures.setSkin(skinURL);

        playerProfile.setTextures(textures);
        sMeta.setOwnerProfile(playerProfile);
        head.setItemMeta(sMeta);
        return head;
    }


    /**
     * Tries to parse a base64 string as a URL.
     *
     * <p>The base64 string is expected to be a JSON object with only one key, "textures", which is an object containing another key, "SKIN", which is an object containing the key "url", which is a string containing the URL of the skin.
     *
     * <p>If the base64 string is invalid, a MalformedURLException is thrown.
     *
     * @param base64 The base64 string containing the skin url to parse
     * @return The URL represented contained in the base64 string
     * @throws MalformedURLException If the base64 string does not contain a valid skin url
     */
    public static URL getUrlFromBase64(String base64) throws MalformedURLException {
        try {
            String decoded = new String(Base64.getDecoder().decode(base64));
            // We simply remove the "beginning" and "ending" part of the JSON, so we're left with only the URL. You could use a proper
            // JSON parser for this, but that's not worth it. The String will always start exactly with this stuff anyway
            return new URL(decoded.substring("{\"textures\":{\"SKIN\":{\"url\":\"".length(), decoded.length() - "\"}}}".length()));
        } catch (Throwable t) {
            throw new MalformedURLException("Invalid base64 string: " + base64);
        }
    }

}
