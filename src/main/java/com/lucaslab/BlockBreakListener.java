package com.lucaslab;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.logging.Logger;

public class BlockBreakListener implements Listener {
    public static final Logger LOG = Logger.getLogger("BlockBreakListener");


    @EventHandler
    public void damage(BlockBreakEvent event) {
        LOG.info("Breaking Block of type [" +  event.getBlock().getType() + "] BlockData [" + event.getBlock().getBlockData().getAsString() +"]");
        if(event.getBlock().getType() == Material.PINK_TERRACOTTA) {
            StuffMaker.createCherryBlossom(event.getBlock().getLocation(), 7);
        }





    }
}
