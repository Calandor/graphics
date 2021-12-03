import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

import static org.lwjgl.opengl.GL46C.*;

public class ShaderProgram {

    private class Uniform{
        String name;
        int location;
        Uniform(String name){
            this.name = name;
            this.location = glGetUniformLocation(ShaderProgram.this.id, name);
        }
    }

    final int id;
    HashMap<String, Uniform> uniforms;

    public ShaderProgram(String... paths) throws IOException {
        id = glCreateProgram();
        for (String path : paths) {
            int shader = getShader(path);
            glAttachShader(id, shader);
        }
        glLinkProgram(id);
        int[] success = new int[1];
        glGetProgramiv(id, GL_LINK_STATUS, success);
        if(success[0] != GL_TRUE){
            throw new RuntimeException("Program linking failed for" + Arrays.toString(paths) + ": " + glGetProgramInfoLog(id));
        }
        uniforms = new HashMap<>();
    }

    private static int getShader(String path) throws IOException {
        Path shaderPath = Path.of(path);
        String source = Files.readString(shaderPath);
        String extension = path.substring(path.lastIndexOf('.') + 1);
        int type;
        switch (extension) {
            case "vert" -> type = GL_VERTEX_SHADER;
            case "frag" -> type = GL_FRAGMENT_SHADER;
            case "geom" -> type = GL_GEOMETRY_SHADER;
            case "tesc" -> type = GL_TESS_CONTROL_SHADER;
            case "tese" -> type = GL_TESS_EVALUATION_SHADER;
            case "comp" -> type = GL_COMPUTE_SHADER;
            default -> throw new IllegalStateException("Unexpected value: " + extension);
        }
        try {
            return compileShader(source, type);
        } catch (RuntimeException e){
            String msg = "Shader compilation failed for" + path + e.getMessage().substring(e.getMessage().indexOf(':'));
            throw new RuntimeException(msg);
        }
    }

    public static int compileShader(CharSequence source, int type){
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        int[] success = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, success);
        if (success[0] != GL_TRUE){
            throw new RuntimeException("Shader compilation failed: %s" + glGetShaderInfoLog(shader));
        }
        return shader;
    }

    public void use(){
        glUseProgram(id);
    }

    private void addUniformIfAbsent(String name){
        if(!uniforms.containsKey(name)){
            uniforms.put(name, new Uniform(name));
        }
    }

    public void setUniform1(String name, FloatBuffer value){
        addUniformIfAbsent(name);
        glProgramUniform1fv(id, uniforms.get(name).location, value);
    }

    public void setUniform2(String name, FloatBuffer value){
        addUniformIfAbsent(name);
        glProgramUniform2fv(id, uniforms.get(name).location, value);
    }

    public void setUniform3(String name, FloatBuffer value){
        addUniformIfAbsent(name);
        glProgramUniform3fv(id, uniforms.get(name).location, value);
    }

    public void setUniform4(String name, FloatBuffer value){
        addUniformIfAbsent(name);
        glProgramUniform4fv(id, uniforms.get(name).location, value);
    }

    public void setUniform1(String name, IntBuffer value){
        addUniformIfAbsent(name);
        glProgramUniform1iv(id, uniforms.get(name).location, value);
    }

    public void setUniform2(String name, IntBuffer value){
        addUniformIfAbsent(name);
        glProgramUniform2iv(id, uniforms.get(name).location, value);
    }

    public void setUniform3(String name, IntBuffer value){
        addUniformIfAbsent(name);
        glProgramUniform3iv(id, uniforms.get(name).location, value);
    }

    public void setUniform4(String name, IntBuffer value){
        addUniformIfAbsent(name);
        glProgramUniform4iv(id, uniforms.get(name).location, value);
    }

    public void setUniformMatrix4(String name, FloatBuffer value, boolean transpose){
        addUniformIfAbsent(name);
        glProgramUniformMatrix4fv(id, uniforms.get(name).location, transpose, value);
    }
}