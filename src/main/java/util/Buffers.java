package util;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class Buffers {
    public static FloatBuffer createBuffer(float[] value){
        return BufferUtils.createFloatBuffer(value.length).put(value).flip();
    }

    public static String toString(FloatBuffer buffer){
        float[] arr = new float[buffer.capacity()];
        buffer.get(arr);
        return Arrays.toString(arr);
    }
}
