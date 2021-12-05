package util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL46C.*;

public class VertexArrayBuilder {
    int id;
    int numVertices;

    public VertexArrayBuilder(int numVertices){
        id = glGenVertexArrays();
        this.numVertices = numVertices;
    }

    public VertexArrayBuilder addAttribute(FloatBuffer data, int location){
        int vbo = GLUtils.buffer(data, GL_ARRAY_BUFFER, GL_STATIC_DRAW);
        addAttribute(vbo, data.capacity()/numVertices, location, GL_FLOAT, (data.capacity()/numVertices) << 2);
        return this;
    }

    public VertexArrayBuilder addAttribute(IntBuffer data, int location){
        int vbo = GLUtils.buffer(data, GL_ARRAY_BUFFER, GL_STATIC_DRAW);
        addAttribute(vbo, data.capacity()/numVertices, location, GL_INT, (data.capacity()/numVertices) << 2);
        return this;
    }

    private void addAttribute(int vbo, int size, int location, int type, int stride){
        System.out.println(size);
        glBindVertexArray(id);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(location, size, type, false, stride, 0);
        glEnableVertexAttribArray(location);
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public VertexArrayBuilder addIndices(ShortBuffer data){
        int ebo = GLUtils.buffer(data, GL_ELEMENT_ARRAY_BUFFER, GL_STATIC_DRAW);
        glBindVertexArray(id);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBindVertexArray(0);
        return this;
    }

    public int get(){
        return id;
    }

}
