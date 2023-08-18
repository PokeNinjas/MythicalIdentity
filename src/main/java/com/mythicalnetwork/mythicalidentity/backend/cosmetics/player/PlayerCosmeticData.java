package com.mythicalnetwork.mythicalidentity.backend.cosmetics.player;

import com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe.Cosmetic;
import com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe.CosmeticInstance;
import com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe.Outfit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents the data for a player's cosmetics.
 * Contains a list of unlocked cosmetics, and an equipped Outfit.
 */
public class PlayerCosmeticData {

    private List<Cosmetic> unlockedCosmetics;
    private Outfit equippedOutfit;
    private UUID player;

    public PlayerCosmeticData(UUID player) {
        this.player = player;
        equippedOutfit = null;
        unlockedCosmetics = new ArrayList<>();
    }

    public PlayerCosmeticData(UUID player, List<Cosmetic> unlockedCosmetics, Outfit equippedOutfit) {
        this.player = player;
        this.unlockedCosmetics = unlockedCosmetics;
        this.equippedOutfit = equippedOutfit;
    }

    public List<Cosmetic> getUnlockedCosmetics() {
        return unlockedCosmetics;
    }

    public Outfit getEquippedOutfit() {
        return equippedOutfit;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setUnlockedCosmetics(List<Cosmetic> unlockedCosmetics) {
        this.unlockedCosmetics = unlockedCosmetics;
    }

    public void setEquippedOutfit(Outfit equippedOutfit) {
        this.equippedOutfit = equippedOutfit;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public void unlockCosmetic(Cosmetic cosmetic) {
        unlockedCosmetics.add(cosmetic);
    }

    public void lockCosmetic(Cosmetic cosmetic) {
        unlockedCosmetics.remove(cosmetic);
    }

    public boolean hasUnlockedCosmetic(Cosmetic cosmetic) {
        return unlockedCosmetics.contains(cosmetic);
    }

    public void equipOutfit(Outfit outfit) {
        equippedOutfit = outfit;
    }

    public void unequipOutfit() {
        equippedOutfit = null;
    }

    public boolean hasEquippedOutfit() {
        return equippedOutfit != null;
    }

    public boolean hasEquippedCosmetic(Cosmetic cosmetic) {
        if (equippedOutfit == null) {
            return false;
        }
        for (CosmeticInstance equippedCosmetic : equippedOutfit.getCosmetics()) {
            if (equippedCosmetic.getCosmetic().getId().equals(cosmetic.getId())) {
                return true;
            }
        }
        return false;
    }
}
