package de.skeletoneye.creative.api.data;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import lombok.Getter;

@Getter
public class BlockLocation extends DataObject
{
    public static BlockLocation fromEntityLocation(Entity entity, String name)
    {
        return BlockLocation.fromLocation(entity.getLocation(), name);
    }

    public static BlockLocation fromLocation(Location loc, String name)
    {
        return new BlockLocation(name, loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public static BlockLocation fromPlayerTarget(Player player, String name)
    {
        return BlockLocation.fromPlayerTarget(player, 8, name);
    }

    public static BlockLocation fromPlayerTarget(Player player, int maxDistance, String name)
    {
        HashSet<Material> airSet = new HashSet<>();
        airSet.add(Material.AIR);
        return BlockLocation.fromLocation(player.getTargetBlock(airSet, maxDistance).getLocation(), name);
    }

    private int x;
    private int y;
    private int z;

    public BlockLocation(String name, World context, int x, int y, int z)
    {
        super(name, context);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Block toBlock(World world)
    {
        return this.toLocation(this.getContext()).getBlock();
    }

    public Location toLocation(World world)
    {
        return new Location(world, this.getX(), this.getY(), this.getZ());
    }
}
