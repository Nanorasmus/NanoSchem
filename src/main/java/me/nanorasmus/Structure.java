package me.nanorasmus;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Ladder;
import org.bukkit.block.data.type.RedstoneWallTorch;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Structure {
    List<List<List<BlockRaw>>> structure;

    public Structure(Location pos1, Location pos2, Location relativeTo) {
        structure = new ArrayList<>();

        Vector max = Vector.getMaximum(pos1.toVector(), pos2.toVector());
        Vector min = Vector.getMinimum(pos1.toVector(), pos2.toVector());

        for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
            List<List<BlockRaw>> layer = new ArrayList<>();
            for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                List<BlockRaw> row = new ArrayList<>();
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    row.add(new BlockRaw(new Location(pos1.getWorld(), x, y, z).getBlock(), relativeTo.toVector()));
                }
                layer.add(row);
            }
            structure.add(layer);
        }
    }

    public Structure(StructureRaw structureRaw) {
        this.structure = structureRaw.structure;
    }

    public StructureRaw Export() {
        StructureRaw out = new StructureRaw();
        out.structure = structure;
        return out;
    }

    public void Build(Location relativeTo) {
        World world = relativeTo.getWorld();

        for (int y = 0; y < structure.size(); y++) {

            List<List<BlockRaw>> layer = structure.get(y);
            Collections.shuffle(layer);

            for (int x = 0; x < layer.size(); x++) {

                List<BlockRaw> row = layer.get(x);
                Collections.shuffle(row);

                for (int z = 0; z < row.size(); z++) {

                    BlockRaw raw = row.get(z);
                    Block block = new Location(world, raw.position.getBlockX(), raw.position.getBlockY(), raw.position.getBlockZ()).add(relativeTo).getBlock();
                    block.setType(raw.material);
                }
            }
        }
    }
}
