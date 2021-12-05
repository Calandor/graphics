package util;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

public class NIOBuffers {
    public static FloatBuffer createBuffer(float[] value){
        return BufferUtils.createFloatBuffer(value.length).put(value).flip();
    }

    public static ShortBuffer createBuffer(short[] value){
        return BufferUtils.createShortBuffer(value.length).put(value).flip();
    }

    public static String toString(FloatBuffer buffer){
        float[] arr = new float[buffer.capacity()];
        buffer.get(arr);
        return Arrays.toString(arr);
    }
    public static String toString(ByteBuffer buffer){
        int[] arr = new int[buffer.capacity()];
        for(int i = 0; i < buffer.capacity(); i++){
            arr[i] = buffer.get(i) & 0xFF;
        }
        return Arrays.toString(arr);
    }
}
