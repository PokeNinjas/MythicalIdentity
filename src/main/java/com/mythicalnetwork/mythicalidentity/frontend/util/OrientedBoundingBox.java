package com.mythicalnetwork.mythicalidentity.frontend.util;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class OrientedBoundingBox {

    // TOPOLOGY

    // Y ^       8   +-------+   7     axisY   axisZ
    //   |          /|      /|             | /
    //   |     4   +-------+ | 3           |/
    //   |  Z      | |     | |             +-- axisX
    //   |   /   5 | +-----|-+  6       Center
    //   |  /      |/      |/
    //   | /   1   +-------+   2
    //   |/
    //   +--------------------> X

    // DEFINITIVE PROPERTIES

    // Center position of the cuboid
    public Vec3 center;

    // Extent defines the half size in all directions
    public Vec3 extent;

    // Orthogonal basis vectors define orientation
    public Vec3 axisX;
    public Vec3 axisY;
    public Vec3 axisZ;

    // DERIVED PROPERTIES
    public Vec3 scaledAxisX;
    public Vec3 scaledAxisY;
    public Vec3 scaledAxisZ;
    public Matrix3f rotation = new Matrix3f();
    public Vec3 vertex1;
    public Vec3 vertex2;
    public Vec3 vertex3;
    public Vec3 vertex4;
    public Vec3 vertex5;
    public Vec3 vertex6;
    public Vec3 vertex7;
    public Vec3 vertex8;
    public Vec3[] vertices;

    // 1. CONSTRUCT

    public OrientedBoundingBox(Vec3 center, double width, double height, double depth, float yaw, float pitch) {
        this.center = center;
        this.extent = new Vec3(width/2.0, height/2.0, depth/2.0);
        this.axisZ = Vec3.directionFromRotation(yaw, pitch).normalize();
        this.axisY = Vec3.directionFromRotation(yaw + 90, pitch).reverse().normalize();
        this.axisX = axisZ.cross(axisY);
    }

    public OrientedBoundingBox(Vec3 center, Vec3 size, float yaw, float pitch) {
        this(center,size.x, size.y, size.z, yaw, pitch);
    }

    public OrientedBoundingBox(AABB box) {
        this.center = new Vec3((box.maxX + box.minX) / 2.0, (box.maxY + box.minY) / 2.0, (box.maxZ + box.minZ) / 2.0);
        this.extent = new Vec3(Math.abs(box.maxX - box.minX) / 2.0, Math.abs(box.maxY - box.minY) / 2.0, Math.abs(box.maxZ - box.minZ) / 2.0);
        this.axisX = new Vec3(1, 0, 0);
        this.axisY = new Vec3(0, 1, 0);
        this.axisZ = new Vec3(0, 0, 1);
    }

    public OrientedBoundingBox(OrientedBoundingBox obb) {
        this.center = obb.center;
        this.extent = obb.extent;
        this.axisX = obb.axisX;
        this.axisY = obb.axisY;
        this.axisZ = obb.axisZ;
    }

    public OrientedBoundingBox copy() {
        return new OrientedBoundingBox(this);
    }

    // 2. CONFIGURE

    public OrientedBoundingBox offsetAlongAxisX(double offset) {
        this.center = this.center.add(axisX.scale(offset));
        return this;
    }

    public OrientedBoundingBox offsetAlongAxisY(double offset) {
        this.center = this.center.add(axisY.scale(offset));
        return this;
    }

    public OrientedBoundingBox offsetAlongAxisZ(double offset) {
        this.center = this.center.add(axisZ.scale(offset));
        return this;
    }

    public OrientedBoundingBox offset(Vec3 offset) {
        this.center = this.center.add(offset);
        return this;
    }

    public OrientedBoundingBox scale(double scale) {
        this.extent = this.extent.scale(scale);
        return this;
    }

    // 3. UPDATE

    public OrientedBoundingBox updateVertex() {
        rotation.set(0,0, (float) axisX.x);
        rotation.set(0,1, (float) axisX.y);
        rotation.set(0,2, (float) axisX.z);
        rotation.set(1,0, (float) axisY.x);
        rotation.set(1,1, (float) axisY.y);
        rotation.set(1,2, (float) axisY.z);
        rotation.set(2,0, (float) axisZ.x);
        rotation.set(2,1, (float) axisZ.y);
        rotation.set(2,2, (float) axisZ.z);

        scaledAxisX = axisX.scale(extent.x);
        scaledAxisY = axisY.scale(extent.y);
        scaledAxisZ = axisZ.scale(extent.z);

        vertex1 = center.subtract(scaledAxisZ).subtract(scaledAxisX).subtract(scaledAxisY);
        vertex2 = center.subtract(scaledAxisZ).add(scaledAxisX).subtract(scaledAxisY);
        vertex3 = center.subtract(scaledAxisZ).add(scaledAxisX).add(scaledAxisY);
        vertex4 = center.subtract(scaledAxisZ).subtract(scaledAxisX).add(scaledAxisY);
        vertex5 = center.add(scaledAxisZ).subtract(scaledAxisX).subtract(scaledAxisY);
        vertex6 = center.add(scaledAxisZ).add(scaledAxisX).subtract(scaledAxisY);
        vertex7 = center.add(scaledAxisZ).add(scaledAxisX).add(scaledAxisY);
        vertex8 = center.add(scaledAxisZ).subtract(scaledAxisX).add(scaledAxisY);

        vertices = new Vec3[]{
                vertex1,
                vertex2,
                vertex3,
                vertex4,
                vertex5,
                vertex6,
                vertex7,
                vertex8
        };

        return this;
    }

    public void rotate(float yaw, float pitch) {
        this.axisZ = Vec3.directionFromRotation(yaw, pitch).normalize();
        this.axisY = Vec3.directionFromRotation(yaw + 90, pitch).reverse().normalize();
        this.axisX = axisZ.cross(axisY);
        updateVertex();
    }

    // 4. CHECK INTERSECTIONS

    public boolean contains(Vec3 point) {
        var distance = new Vector3f(point.subtract(center));
        distance.transform(rotation);
        return Math.abs(distance.x()) < extent.x &&
                Math.abs(distance.y()) < extent.y &&
                Math.abs(distance.z()) < extent.z;
    }

    public boolean intersects(AABB boundingBox) {
        var otherOBB = new OrientedBoundingBox(boundingBox).updateVertex();
        return Intersects(this, otherOBB);
    }

    public boolean intersects(OrientedBoundingBox otherOBB) {
        return Intersects(this, otherOBB);
    }

    /**
     * Calculates if there is intersection between given OBBs.
     * Separating Axes Theorem implementation.
     */
    public static boolean Intersects(OrientedBoundingBox a, OrientedBoundingBox b)  {
        if (Separated(a.vertices, b.vertices, a.scaledAxisX))
            return false;
        if (Separated(a.vertices, b.vertices, a.scaledAxisY))
            return false;
        if (Separated(a.vertices, b.vertices, a.scaledAxisZ))
            return false;

        if (Separated(a.vertices, b.vertices, b.scaledAxisX))
            return false;
        if (Separated(a.vertices, b.vertices, b.scaledAxisY))
            return false;
        if (Separated(a.vertices, b.vertices, b.scaledAxisZ))
            return false;

        if (Separated(a.vertices, b.vertices, a.scaledAxisX.cross(b.scaledAxisX)))
            return false;
        if (Separated(a.vertices, b.vertices, a.scaledAxisX.cross(b.scaledAxisY)))
            return false;
        if (Separated(a.vertices, b.vertices, a.scaledAxisX.cross(b.scaledAxisZ)))
            return false;

        if (Separated(a.vertices, b.vertices, a.scaledAxisY.cross(b.scaledAxisX)))
            return false;
        if (Separated(a.vertices, b.vertices, a.scaledAxisY.cross(b.scaledAxisY)))
            return false;
        if (Separated(a.vertices, b.vertices, a.scaledAxisY.cross(b.scaledAxisZ)))
            return false;

        if (Separated(a.vertices, b.vertices, a.scaledAxisZ.cross(b.scaledAxisX)))
            return false;
        if (Separated(a.vertices, b.vertices, a.scaledAxisZ.cross(b.scaledAxisY)))
            return false;
        if (Separated(a.vertices, b.vertices, a.scaledAxisZ.cross(b.scaledAxisZ)))
            return false;

        return true;
    }

    private static boolean Separated(Vec3[] vertsA, Vec3[] vertsB, Vec3 axis)  {
        // Handles the cross product = {0,0,0} case
        if (axis.equals(Vec3.ZERO))
            return false;

        var aMin = Double.POSITIVE_INFINITY;
        var aMax = Double.NEGATIVE_INFINITY;
        var bMin = Double.POSITIVE_INFINITY;
        var bMax = Double.NEGATIVE_INFINITY;

        // Define two intervals, a and b. Calculate their min and max values
        for (var i = 0; i < 8; i++)
        {
            var aDist = vertsA[i].dot(axis);
            aMin = (aDist < aMin) ? aDist : aMin;
            aMax = (aDist > aMax) ? aDist : aMax;
            var bDist = vertsB[i].dot(axis);
            bMin = (bDist < bMin) ? bDist : bMin;
            bMax = (bDist > bMax) ? bDist : bMax;
        }

        // One-dimensional intersection test between a and b
        var longSpan = Math.max(aMax, bMax) - Math.min(aMin, bMin);
        var sumSpan = aMax - aMin + bMax - bMin;
        return longSpan >= sumSpan; // > to treat touching as intersection
    }

    public boolean clip(Vec3 start, Vec3 end){
        Vec3 dir = end.subtract(start);
        double tmin = 0.0;
        double tmax = 100000.0;
        for(int i = 0; i < 3; i++){
            Direction.Axis axis = Direction.Axis.values()[i];
            if(Math.abs(dir.get(axis)) < 0.0000001){
                if(start.get(axis) < center.get(axis) - extent.get(axis) || start.get(axis) > center.get(axis) + extent.get(axis))
                    return false;
            }else{
                double ood = 1.0 / dir.get(axis);
                double t1 = (center.get(axis) - extent.get(axis) - start.get(axis)) * ood;
                double t2 = (center.get(axis) + extent.get(axis) - start.get(axis)) * ood;
                if(t1 > t2){
                    double temp = t1;
                    t1 = t2;
                    t2 = temp;
                }
                if(t1 > tmin)
                    tmin = t1;
                if(t2 < tmax)
                    tmax = t2;
                if(tmin > tmax)
                    return false;
            }
        }
        return true;
    }
    
    public void transform(Matrix4f matrix4f){
        Vector4f center = new Vector4f((float) this.center.x, (float) this.center.y, (float) this.center.z, 1);
        center.transform(matrix4f);
        this.center = new Vec3(center.x(), center.y(), center.z());
        Vector4f extent = new Vector4f((float) this.extent.x, (float) this.extent.y, (float) this.extent.z, 1);
        extent.transform(matrix4f);
        this.extent = new Vec3(extent.x(), extent.y(), extent.z());
        Vector4f axisX = new Vector4f((float) this.axisX.x, (float) this.axisX.y, (float) this.axisX.z, 1);
        axisX.transform(matrix4f);
        this.axisX = new Vec3(axisX.x(), axisX.y(), axisX.z());
        Vector4f axisY = new Vector4f((float) this.axisY.x, (float) this.axisY.y, (float) this.axisY.z, 1);
        axisY.transform(matrix4f);
        this.axisY = new Vec3(axisY.x(), axisY.y(), axisY.z());
        Vector4f axisZ = new Vector4f((float) this.axisZ.x, (float) this.axisZ.y, (float) this.axisZ.z, 1);
        axisZ.transform(matrix4f);
        this.axisZ = new Vec3(axisZ.x(), axisZ.y(), axisZ.z());
        updateVertex();
    }
}