package me.nanorasmus;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Ladder;
import org.bukkit.block.data.type.RedstoneWallTorch;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.util.Vector;

public class BlockRaw {
    public Material material;
    public BlockFace rotation = null;
    public Vector position;

    public BlockRaw(Block block, Vector relativeTo) {
        material = block.getType();
        BlockState state = block.getState();
        position = block.getLocation().toVector().subtract(relativeTo);
    }
}
