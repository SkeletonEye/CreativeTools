package de.skeletoneye.creative.api.data;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import lombok.Getter;

@Getter
public class EntityLocation extends DataObject
{
    public static EntityLocation fromEntityLocation(Entity entity, String name)
    {
        return EntityLocation.fromLocation(entity.getLocation(), name);
    }

    public static EntityLocation fromLocation(Location loc, String name)
    {
        return new EntityLocation(name, loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    private EntityLocation(String name, World context, double x, double y, double z, float yaw, float pitch)
    {
        super(name, context);

        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void teleport(Entity entity)
    {
        entity.teleport(this.toLocation());
    }

    public Location toLocation()
    {
        return new Location(this.getContext(), this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
    }
}
