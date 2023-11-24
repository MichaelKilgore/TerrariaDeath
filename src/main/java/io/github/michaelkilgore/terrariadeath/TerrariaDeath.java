package io.github.michaelkilgore.terrariadeath;

import com.google.common.collect.ImmutableSet;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class TerrariaDeath extends JavaPlugin implements Listener {

    ImmutableSet<Material> materialSet = ImmutableSet.<Material>builder()
            .add(Material.IRON_BLOCK)
            .add(Material.IRON_INGOT)
            .add(Material.IRON_ORE)
            .add(Material.IRON_NUGGET)

            .add(Material.GOLD_BLOCK)
            .add(Material.GOLD_INGOT)
            .add(Material.GOLD_ORE)
            .add(Material.GOLD_NUGGET)

            .add(Material.DIAMOND)
            .add(Material.DIAMOND_ORE)
            .add(Material.DIAMOND_BLOCK)

            .add(Material.EMERALD)
            .add(Material.EMERALD_ORE)
            .add(Material.EMERALD_BLOCK)

            .add(Material.LAPIS_LAZULI)
            .add(Material.LAPIS_ORE)
            .add(Material.LAPIS_BLOCK)

            .add(Material.REDSTONE)
            .add(Material.REDSTONE_ORE)

            .build();

    @Override
    public void onEnable() {
        getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.KEEP_INVENTORY, true));

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().clear();

        Player player = event.getEntity();

        ItemStack[] inventoryContents = player.getInventory().getContents();

        // Iterate through the items in the player's inventory
        for (ItemStack item : inventoryContents) {
            if (item != null && isDroppableItem(item.getType())) {
                // Remove the non-allowed items from the inventory
                player.getInventory().removeItem(item);
                // drop that item in the world
                player.getWorld().dropItem(player.getLocation(), item);
            }
        }

        getLogger().info("Player Died!? Wopwop...");
    }

    private boolean isDroppableItem(Material material) {
        // Define the list of allowed items here
        // For example, allow only diamonds and golden apples to be dropped
        return materialSet.contains(material);
    }
}
