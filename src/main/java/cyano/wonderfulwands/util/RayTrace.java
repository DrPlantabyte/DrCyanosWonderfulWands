package cyano.wonderfulwands.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class RayTrace {
	public static MovingObjectPosition rayTraceBlocksAndEntities(World w, double maxRange, EntityLivingBase source){
		double rangeSqr = maxRange * maxRange;
		Vec3 rayOrigin = new Vec3(source.posX, source.posY + source.getEyeHeight(), source.posZ);
		Vec3 rayDirection = source.getLookVec();
		BlockPos srcPos = source.getPosition();
		AxisAlignedBB aoi = new AxisAlignedBB(srcPos.getX() - maxRange, srcPos.getY() - maxRange, srcPos.getZ() - maxRange,
				srcPos.getX() + maxRange, srcPos.getY() + maxRange, srcPos.getZ() + maxRange);
		List<Entity> entities = w.getEntitiesWithinAABBExcludingEntity(source, aoi);
		double closestDistSqr = Double.MAX_VALUE;
		Entity closestEntity = null;
		for(Entity e : entities){
			double distSqr = e.getDistanceSq(source.posX, source.posY, source.posZ);
			if(distSqr < rangeSqr){
				// e is within range
				AxisAlignedBB box = e.getEntityBoundingBox();
				if(rayIntersectsBoundingBox(rayOrigin,rayDirection, box)){
					// e is in cross-hairs
					if(distSqr < closestDistSqr){
						// e is closest entity in line of fire
						closestDistSqr = distSqr;
						closestEntity = e;
					}
				}
			}
		}
		if(closestEntity == null) {
			return w.rayTraceBlocks(rayOrigin, rayOrigin.add(mul(rayDirection, maxRange)), true, false, false);
		} else {
			Vec3 pos = new Vec3(closestEntity.posX, closestEntity.posY+closestEntity.getEyeHeight(), closestEntity.posZ);
			MovingObjectPosition entityCollision = new MovingObjectPosition(closestEntity, pos);
			return entityCollision;
		}
	}
	
	public static boolean rayIntersectsBoundingBox(Vec3 rayOrigin, Vec3 rayDirection, AxisAlignedBB box){
		if(box == null) return false;
		// algorithm from http://gamedev.stackexchange.com/questions/18436/most-efficient-aabb-vs-ray-collision-algorithms
		Vec3 inverse = new Vec3(1.0 / rayDirection.xCoord, 1.0 / rayDirection.yCoord, 1.0 / rayDirection.zCoord);
		double t1 = (box.minX - rayOrigin.xCoord)*inverse.xCoord;
		double t2 = (box.maxX- rayOrigin.xCoord)*inverse.xCoord;
		double t3 = (box.minY - rayOrigin.yCoord)*inverse.yCoord;
		double t4 = (box.maxY - rayOrigin.yCoord)*inverse.yCoord;
		double t5 = (box.minZ - rayOrigin.zCoord)*inverse.zCoord;
		double t6 = (box.maxZ - rayOrigin.zCoord)*inverse.zCoord;

		double tmin = max(max(min(t1, t2), min(t3, t4)), min(t5, t6));
		double tmax = min(min(max(t1, t2), max(t3, t4)), max(t5, t6));

		// if tmax < 0, ray (line) is intersecting AABB, but whole AABB is behing us
		if (tmax < 0)
		{
			return false;
		}

		// if tmin > tmax, ray doesn't intersect AABB
		if (tmin > tmax)
		{
			return false;
		}

		return true;
	}
	
	private static Vec3 mul(Vec3 a, double b){
		return new Vec3(a.xCoord * b, a.yCoord * b, a.zCoord * b);
	}
	private static double max(double a, double b){
		return Math.max(a, b);
	}
	private static double min(double a, double b){
		return Math.min(a, b);
	}
}
