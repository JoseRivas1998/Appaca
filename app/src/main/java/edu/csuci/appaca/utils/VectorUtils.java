package edu.csuci.appaca.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class VectorUtils {

    public static Vector2 polarRadians(float magnitude, float radians) {
        float x = magnitude * MathUtils.cos(radians);
        float y = magnitude * MathUtils.sin(radians);
        return new Vector2(x, y);
    }

    public static Vector2 polarDegrees(float magnitude, float degrees) {
        float x = magnitude * MathUtils.cos(degrees * MathUtils.degRad);
        float y = magnitude * MathUtils.sin(degrees * MathUtils.degRad);
        return new Vector2(x, y);
    }

    public static float magSq(float x, float y) {
        return (x * x) + (y * y);
    }

    public static float magSq(Vector2 vec) {
        return magSq(vec.x, vec.y);
    }

    public static float mag(float x, float y) {
        return (float) Math.sqrt(magSq(x, y));
    }

    public static float mag(Vector2 vec) {
        return mag(vec.x, vec.y);
    }

    public static float distSq(float x1, float y1, float x2, float y2) {
        return magSq(x2 - x1, y2 - y1);
    }

    public static float distSq(Vector2 p1, Vector2 p2) {
        return distSq(p1.x, p1.y, p2.x, p2.y);
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(distSq(x1, y1, x2, y2));
    }

    public static float dist(Vector2 p1, Vector2 p2) {
        return distSq(p1.x, p1.y, p2.x, p2.y);
    }

    public static float angleRadians(float x, float y) {
        return MathUtils.atan2(y, x);
    }

    public static float angleRadians(Vector2 vec) {
        return MathUtils.atan2(vec.y, vec.x);
    }

    public static float angleDegrees(float x, float y) {
        return angleRadians(x, y) * MathUtils.radDeg;
    }

    public static float angleDegrees(Vector2 vec) {
        return angleRadians(vec) * MathUtils.radDeg;
    }
    public static float angleBetweenRadians(float x1, float y1, float x2, float y2) {
        return angleRadians(x2 - x1, y2 - y1);
    }

    public static float angleBetweenRadians(Vector2 p1, Vector2 p2) {
        return angleBetweenRadians(p1.x, p1.y, p2.x, p2.y);
    }

    public static float angleBetweenDegrees(float x1, float y1, float x2, float y2) {
        return angleBetweenRadians(x1, y1, x2, y2) * MathUtils.radDeg;
    }

    public static float angleBetweenDegrees(Vector2 p1, Vector2 p2) {
        return angleBetweenRadians(p1, p2) * MathUtils.radDeg;
    }

    public static Vector2 clampVector(Vector2 v, float left, float bottom, float right, float top) {
        float x = (float) MathFunctions.clamp(v.x, left, right);
        float y = (float) MathFunctions.clamp(v.y, bottom, top);
        return new Vector2(x, y);
    }

    public static Vector2 clampVector(Vector2 v, Vector2 bottomLeft, Vector2 topRight) {
        return clampVector(v, bottomLeft.x, bottomLeft.y, topRight.x, topRight.y);
    }

    public static Vector2 clampVector(Vector2 v, float left, float bottom, Vector2 topRight) {
        return clampVector(v, left, bottom, topRight.x, topRight.y);
    }

    public static Vector2 clampVector(Vector2 v, Vector2 bottomLeft, float right, float top) {
        return clampVector(v, bottomLeft.x, bottomLeft.y, right, top);
    }

}
