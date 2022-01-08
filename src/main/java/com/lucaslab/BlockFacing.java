package com.lucaslab;

import org.bukkit.Location;

public class BlockFacing {

    public static final BlockFacing NORTH = new BlockFacing("North");
    public static final BlockFacing SOUTH = new BlockFacing("South");
    public static final BlockFacing EAST = new BlockFacing("East");
    public static final BlockFacing WEST = new BlockFacing("West");

    public static final BlockFacing NORTH_WEST = new BlockFacing("Northwest");
    public static final BlockFacing NORTH_EAST = new BlockFacing("Northeast");
    public static final BlockFacing SOUTH_WEST = new BlockFacing("Southwest");
    public static final BlockFacing SOUTH_EAST = new BlockFacing("Southeast");

    private String direction;
    private BlockFacing(String direction) {
        this.direction = direction;
    }
    public boolean equals(BlockFacing facing) {
        return this.direction.equals(facing.direction);
    }
    public String getDirection() {
        return this.direction;
    }
    public String toString() {
        return direction;
    }

    public static final BlockFacing[] axis = { BlockFacing.SOUTH, BlockFacing.WEST, BlockFacing.NORTH, BlockFacing.EAST };
    public static final BlockFacing[] radial = { BlockFacing.SOUTH, BlockFacing.SOUTH_WEST, BlockFacing.WEST, BlockFacing.NORTH_WEST, BlockFacing.NORTH, BlockFacing.NORTH_EAST, BlockFacing.EAST, BlockFacing.SOUTH_EAST };

    public static BlockFacing locationToFace(Location location) {
        return yawToFace(location.getYaw());
    }

    public static BlockFacing yawToFace(float yaw) {
        return yawToFace(yaw, true);
    }

    public static BlockFacing yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections) {
            return radial[Math.round(yaw / 45f) & 0x7];
        } else {
            return axis[Math.round(yaw / 90f) & 0x3];
        }
    }
}
