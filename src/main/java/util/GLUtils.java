package util;

import java.nio.*;

import static org.lwjgl.opengl.GL46C.*;

public class GLUtils {
    public static int buffer(FloatBuffer data, int target, int usage){
        int buf = glGenBuffers();
        glBindBuffer(target, buf);
        glBufferData(target, data, usage);
        glBindBuffer(target, 0);
        return buf;
    }

    public static int buffer(ByteBuffer data, int target, int usage){
        int buf = glGenBuffers();
        glBindBuffer(target, buf);
        glBufferData(target, data, usage);
        glBindBuffer(target, 0);
        return buf;
    }

    public static int buffer(IntBuffer data, int target, int usage){
        int buf = glGenBuffers();
        glBindBuffer(target, buf);
        glBufferData(target, data, usage);
        glBindBuffer(target, 0);
        return buf;
    }

    public static int buffer(DoubleBuffer data, int target, int usage){
        int buf = glGenBuffers();
        glBindBuffer(target, buf);
        glBufferData(target, data, usage);
        glBindBuffer(target, 0);
        return buf;
    }

    public static int buffer(ShortBuffer data, int target, int usage){
        int buf = glGenBuffers();
        glBindBuffer(target, buf);
        glBufferData(target, data, usage);
        glBindBuffer(target, 0);
        return buf;
    }


    public static int emptyBuffer(int size, int target, int usage){
        int buf = glGenBuffers();
        glBindBuffer(target, buf);
        glBufferData(target, size, usage);
        glBindBuffer(target, 0);
        return buf;
    }
}
