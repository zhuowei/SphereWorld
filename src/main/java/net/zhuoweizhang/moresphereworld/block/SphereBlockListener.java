package net.zhuoweizhang.moresphereworld.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

import net.zhuoweizhang.moresphereworld.MoreSphereWorldPlugin;
import net.zhuoweizhang.moresphereworld.Sphere;
import net.zhuoweizhang.moresphereworld.config.SphereWorldConfig;

public class SphereBlockListener implements Listener {
    public MoreSphereWorldPlugin plugin;

    public SphereBlockListener(MoreSphereWorldPlugin instance) {
	plugin = instance;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
	// our block ?
	if (event.isCancelled())
	    return;
	if (event.getBlock().getWorld().getName().equalsIgnoreCase(SphereWorldConfig.world)) {
	    if (SphereWorldConfig.floorprotect) {
		if (event.getBlock().getY() == 1) {
		    event.setCancelled(true);
		    event.getBlock().setType(Material.STATIONARY_WATER);
		}
	    }
	    if (SphereWorldConfig.potprotect || SphereWorldConfig.sphereprotect) {
		if (event.getBlock().getTypeId() == SphereWorldConfig.glassblock) {
		    // our world ?
		    if (event.getBlock().getWorld().getName().equalsIgnoreCase(SphereWorldConfig.world)) {
			// Check if it is in one of our Spheres
			Block b = event.getBlock();
			for (Sphere s : plugin.spheres.getSphereList()) {
			    if (s.getX() > b.getX()
				    - SphereWorldConfig.maxradius
				    && s.getX() < b.getX()
					    + SphereWorldConfig.maxradius + 16) {
				if (s.getZ() > b.getZ()
					- SphereWorldConfig.maxradius
					&& s.getZ() < b.getZ()
						+ SphereWorldConfig.maxradius
						+ 16) {
				    // Block is near this Sphere check if it is
				    // a
				    // "Unbreakable"
				    if (s.getV().distance(new Vector((double) b.getX(), (double) b.getY(), (double) b.getZ())) < s.getSize()) {
					if (s.getV().distance(new Vector((double) b.getX(), (double) b.getY(), (double) b.getZ())) > s.getSize() - 1.1) {
					    // This is on the Edge.
					    if (SphereWorldConfig.sphereprotect
						    || (SphereWorldConfig.potprotect && b.getY() < 65)) {
						event.getPlayer().sendMessage("Protected Block.. only OP can Break it..");
						if (event.getPlayer().isOp())
						    return;
						event.setCancelled(true);
					    }
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	}
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
	if (event.isCancelled())
	    return;
	if (event.getBlock().getWorld().getName().equalsIgnoreCase(SphereWorldConfig.world)) {
	    if (event.getBlockPlaced().getY() == 1) {
		event.setCancelled(true);
		event.getBlockPlaced().setType(Material.STATIONARY_WATER);
	    }
	}
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
	if (event.isCancelled())
	    return;
	if (event.getBlock().getWorld().getName().equalsIgnoreCase(SphereWorldConfig.world)) {
	    /*if (plugin.isGenerating) {
		event.setCancelled(true);
		return;
	    }*/
	    if (SphereWorldConfig.floorprotect) {
		if (event.getBlock().getY() == 1) {
		    event.setCancelled(true);
		    event.getBlock().setType(Material.STATIONARY_WATER);
		}
	    }
	}
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
	if (event.isCancelled())
	    return;
	if (event.getBlock().getWorld().getName().equalsIgnoreCase(SphereWorldConfig.world)) {
	    /*if (plugin.isGenerating) {
		event.setCancelled(true);
		return;
	    }*/
	    if (SphereWorldConfig.floorprotect) {
		if (event.getToBlock().getY() == 1) {
		    event.setCancelled(true);
		    event.getToBlock().setType(Material.STATIONARY_WATER);
		}
	    }
	}
    }
}
