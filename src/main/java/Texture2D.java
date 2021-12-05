import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46C.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture2D extends Texture{
    int[] shape;

    static {
        TARGET = GL_TEXTURE_2D;
    }

    public Texture2D(String filename, int binding, int format) {
        super(filename, binding, format);
    }

    @Override
    protected void loadFromFile(String filename) {
        int channels = getNumChannels(format);
        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer x = stack.callocInt(1);
            IntBuffer y = stack.callocInt(1);
            ByteBuffer data = stbi_load(filename, x, y, stack.callocInt(1), channels);
            int width = x.get(0);
            int height = y.get(0);
            bind();
            glTexImage2D(TARGET, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, data);
            shape = new int[]{width, height, channels};
            stbi_image_free(data);
        }
    }
}
