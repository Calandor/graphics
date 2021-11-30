import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.lwjgl.opengl.GL46C.*;

public class ShaderProgram {
    final int id;

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
            throw new RuntimeException("Program linking failed for:" + Arrays.toString(paths));
        }
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

}