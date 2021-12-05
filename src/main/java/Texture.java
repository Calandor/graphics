import static org.lwjgl.opengl.GL46C.*;
import static org.lwjgl.stb.STBImage.*;

public abstract class Texture {
    int id;
    int binding;
    int format;
    protected static int TARGET;

    static {
        stbi_set_flip_vertically_on_load(true);
    }

    public Texture(String filename, int binding, int format){
        id = glGenTextures();
        this.binding = binding;
        this.format = format;
        loadFromFile(filename);
    }

    public void parameter(int param, int value){
        bind();
        glTexParameteri(TARGET, param, value);
    }

    public void parameter(int param, float value){
        bind();
        glTexParameterf(TARGET, param, value);
    }

    public void bind(int binding){
        setBinding(binding);
        bind();
    }

    public void bind(){
        glActiveTexture(binding);
        glBindTexture(TARGET, id);
    }

    public void setBinding(int binding){
        this.binding = binding;
    }

    abstract protected void loadFromFile(String filename);

    protected int getNumChannels(int format){
        switch (format){
            case GL_RED, GL_DEPTH_COMPONENT -> {return 1;}
            case GL_RG, GL_DEPTH_STENCIL -> {return 2;}
            case GL_RGB, GL_BGR -> {return 3;}
            case GL_RGBA, GL_BGRA -> {return 4;}
            default -> throw new IllegalStateException("Unexpected format: " + format);
        }
    }
    public void genMipmap(){
        bind();
        glGenerateMipmap(TARGET);
    }
}
