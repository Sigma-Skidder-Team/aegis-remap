package nikoisntcat.client.utils.math;

import com.mojang.blaze3d.systems.RenderSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.client.gl.CompiledShader;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import nikoisntcat.client.modules.impl.misc.ClientSettingsModule;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shader {
    private static boolean field2195;
    private final Map<ShaderProgram, VertexFormat> vertexes;
    private static final Logger logger = LoggerFactory.getLogger(Shader.class);
    private final Map<String, ShaderProgram> fragments = new HashMap<>();

    private CompiledShader method1784(String path, CompiledShader.Type type) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Shader.class.getResourceAsStream(path))))) {
            stringBuilder.append(bufferedReader.lines().collect(Collectors.joining("\n")));
        } catch (Exception exception) {
            logger.error("Failed to read shader file: {}", path, exception);
            return null;
        }
        //System.out.println(stringBuilder.toString());
        try {
            return CompiledShader.compile(Identifier.of("", path), type, stringBuilder.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void clear() {
        RenderSystem.clearShader();
    }

    public void method1786(String name, Matrix4f value) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            GlUniform glUniform = new GlUniform(name, 10, 16);
            glUniform.setLocation(GlUniform.getUniformLocation(shaderProgram.getGlRef(), name));
            shaderProgram.addUniform(glUniform);
        }
        shaderProgram.getUniform(name).set(value);
    }

    private ShaderProgram create(String vertexPath, String fragmentPath, VertexFormat vertexFormat) {
        try {
            return ShaderProgram.create(Objects.requireNonNull(this.method1784(vertexPath, CompiledShader.Type.VERTEX)), Objects.requireNonNull(this.method1784(fragmentPath, CompiledShader.Type.FRAGMENT)), vertexFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void method1789() {
        if (!ClientSettingsModule.noShader.getValue()) {
            if (!field2195) {
                field2195 = true;
                this.put("rounded", "/assets/aegis/shaders/rounded_rect.vsh", "/assets/aegis/shaders/rounded_rect.fsh", VertexFormats.POSITION_TEXTURE_COLOR);
                this.put("blur", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/blur.fsh", VertexFormats.POSITION_TEXTURE);
                this.put("composite", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/composite.fsh", VertexFormats.POSITION_TEXTURE);
                this.put(
                        "blur_kawase_down", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/blur_kawase_down.fsh", VertexFormats.POSITION_TEXTURE
                );
                this.put("blur_kawase_up", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/blur_kawase_up.fsh", VertexFormats.POSITION_TEXTURE);
                this.put("pass", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/pass.fsh", VertexFormats.POSITION_TEXTURE);
                logger.info("ShaderManager initialized. Loaded {} shaders.", this.fragments.size());
            }
        }
    }

    private void put(String name, String vertexPath, String fragmentPath, VertexFormat vertexFormat) {
        try {
            ShaderProgram shaderProgram = this.create(vertexPath, fragmentPath, vertexFormat);
            this.fragments.put(name, shaderProgram);
            this.vertexes.put(shaderProgram, vertexFormat);
        } catch (Exception exception) {
            logger.error("Failed to load shader: {}", name);
        }
    }

    public void setUniformMatrix4f(String name, Matrix4f value) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            GlUniform glUniform = new GlUniform(name, 10, 16);
            glUniform.setLocation(GlUniform.getUniformLocation(shaderProgram.getGlRef(), name));
            shaderProgram.addUniform(glUniform);
        }
        Objects.requireNonNull(shaderProgram.getUniform(name)).set(value);
        Objects.requireNonNull(shaderProgram.getUniform(name)).upload();
    }

    public void bind() {
        Objects.requireNonNull(RenderSystem.getShader()).bind();
    }

    public VertexFormat method1793(ShaderProgram shaderProgram) {
        return this.vertexes.get(shaderProgram);
    }

    public void unbind() {
        Objects.requireNonNull(RenderSystem.getShader()).unbind();
    }

    public void setUniformsIntsUpload(String name, int... values) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            int n = values.length - 1;
            GlUniform glUniform = new GlUniform(name, n, values.length);
            glUniform.setLocation(GlUniform.getUniformLocation(shaderProgram.getGlRef(), name));
            shaderProgram.addUniform(glUniform);
        }
        GlUniform glUniform = shaderProgram.getUniform(name);
        switch (values.length) {
            case 1: {
                assert glUniform != null;
                glUniform.set(values[0]);
                break;
            }
            case 2: {
                assert glUniform != null;
                glUniform.set(values[0], values[1]);
                break;
            }
            case 3: {
                assert glUniform != null;
                glUniform.set(values[0], values[1], values[2]);
                break;
            }
            case 4: {
                assert glUniform != null;
                glUniform.set(values[0], values[1], values[2], values[3]);
            }
        }
        assert glUniform != null;
        glUniform.upload();
    }

    public void set(String name) {
        RenderSystem.setShader(this.get(name));
    }

    public void setUniformsInts(String name, int... values) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            int n = values.length - 1;
            GlUniform glUniform = new GlUniform(name, n, values.length);
            glUniform.setLocation(GlUniform.getUniformLocation(shaderProgram.getGlRef(), name));
            shaderProgram.addUniform(glUniform);
        }
        switch (values.length) {
            case 1: {
                Objects.requireNonNull(shaderProgram.getUniform(name)).set(values[0]);
                break;
            }
            case 2: {
                Objects.requireNonNull(shaderProgram.getUniform(name)).set(values[0], values[1]);
                break;
            }
            case 3: {
                Objects.requireNonNull(shaderProgram.getUniform(name)).set(values[0], values[1], values[2]);
                break;
            }
            case 4: {
                Objects.requireNonNull(shaderProgram.getUniform(name)).set(values[0], values[1], values[2], values[3]);
            }
        }
    }

    public void method1798() {
        field2195 = false;
        this.fragments.clear();
        this.vertexes.clear();
        this.method1789();
    }

    public ShaderProgram get(String name) {
        return this.fragments.getOrDefault(name, null);
    }

    public Shader() {
        this.vertexes = new HashMap<>();
    }

    public void setUniformFloats(String name, float... values) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            int n = values.length + 3;
            GlUniform glUniform = new GlUniform(name, n, values.length);
            glUniform.setLocation(GlUniform.getUniformLocation(shaderProgram.getGlRef(), name));
            shaderProgram.addUniform(glUniform);
        }
        switch (values.length) {
            case 1: {
                shaderProgram.getUniform(name).set(values[0]);
                break;
            }
            case 2: {
                shaderProgram.getUniform(name).set(values[0], values[1]);
                break;
            }
            case 3: {
                shaderProgram.getUniform(name).set(values[0], values[1], values[2]);
                break;
            }
            case 4: {
                shaderProgram.getUniform(name).set(values[0], values[1], values[2], values[3]);
            }
        }
    }

    public void method1801() {
        if (RenderSystem.getShader() == null) {
            return;
        }

        this.method1786("ModelViewMat", RenderSystem.getModelViewMatrix());
        this.method1786("ProjMat", RenderSystem.getProjectionMatrix());
    }
}
