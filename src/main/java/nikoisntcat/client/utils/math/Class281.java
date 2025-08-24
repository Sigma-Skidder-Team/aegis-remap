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
import nikoisntcat.client.modules.impl.ClientSettingsModule;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Class281 {
    private static boolean field2195;
    private final Map<ShaderProgram, VertexFormat> field2196;
    private static final Logger field2197 = LoggerFactory.getLogger(Class281.class);
    private final Map<String, ShaderProgram> field2198 = new HashMap();
    static Object field2199;

    private CompiledShader method1784(String path, CompiledShader.Type type) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Class281.class.getResourceAsStream(path))))){
            stringBuilder.append(bufferedReader.lines().collect(Collectors.joining("\n")));
        }
        catch (Exception exception) {
            field2197.error("Failed to read shader file: {}", (Object)path, (Object)exception);
            return null;
        }
        System.out.println(stringBuilder.toString());
        try {
            return CompiledShader.compile((Identifier)Identifier.of((String)(""), (String)path), (CompiledShader.Type)type, (String)stringBuilder.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void method1785() {
        RenderSystem.clearShader();
    }

    public void method1786(String name, Matrix4f value) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            GlUniform glUniform = new GlUniform(name, 10, 16);
            glUniform.setLocation(GlUniform.getUniformLocation((int)shaderProgram.getGlRef(), (CharSequence)name));
            shaderProgram.addUniform(glUniform);
        }
        shaderProgram.getUniform(name).set(value);
    }

    private ShaderProgram method1787(String vertexPath, String fragmentPath, VertexFormat vertexFormat) {
        try {
            return ShaderProgram.create((CompiledShader)this.method1784(vertexPath, CompiledShader.Type.VERTEX), (CompiledShader)this.method1784(fragmentPath, CompiledShader.Type.FRAGMENT), (VertexFormat)vertexFormat);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void method1788(String name, float ... values) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            int n = values.length + 3;
            GlUniform glUniform = new GlUniform(name, n, values.length);
            glUniform.setLocation(GlUniform.getUniformLocation((int)shaderProgram.getGlRef(), (CharSequence)name));
            shaderProgram.addUniform(glUniform);
        }
        GlUniform glUniform = shaderProgram.getUniform(name);
        switch (values.length) {
            case 1: {
                glUniform.set(values[0]);
                break;
            }
            case 2: {
                glUniform.set(values[0], values[1]);
                break;
            }
            case 3: {
                glUniform.set(values[0], values[1], values[2]);
                break;
            }
            case 4: {
                glUniform.set(values[0], values[1], values[2], values[3]);
            }
        }
        glUniform.upload();
    }

    public void method1789() {
        if (ClientSettingsModule.field1846.method1703()) {
            return;
        }
        if (field2195) {
            return;
        }
        field2195 = true;
        this.method1790("rounded", "/assets/aegis/shaders/rounded_rect.vsh", "/assets/aegis/shaders/rounded_rect.fsh", VertexFormats.POSITION_TEXTURE_COLOR);
        this.method1790("blur", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/blur.fsh", VertexFormats.POSITION_TEXTURE);
        this.method1790("composite", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/composite.fsh", VertexFormats.POSITION_TEXTURE);
        this.method1790("blur_kawase_down", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/blur_kawase_down.fsh", VertexFormats.POSITION_TEXTURE);
        this.method1790("blur_kawase_up", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/blur_kawase_up.fsh", VertexFormats.POSITION_TEXTURE);
        this.method1790("pass", "/assets/aegis/shaders/vertex.vsh", "/assets/aegis/shaders/pass.fsh", VertexFormats.POSITION_TEXTURE);
        field2197.info("ShaderManager initialized. Loaded {} shaders.", (Object)this.field2198.size());
    }

    private void method1790(String name, String vertexPath, String fragmentPath, VertexFormat vertexFormat) {
        try {
            ShaderProgram shaderProgram = this.method1787(vertexPath, fragmentPath, vertexFormat);
            this.field2198.put(name, shaderProgram);
            this.field2196.put(shaderProgram, vertexFormat);
        }
        catch (Exception exception) {
            field2197.error("Failed to load shader: {}", (Object)name);
        }
    }

    public void method1791(String name, Matrix4f value) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            GlUniform glUniform = new GlUniform(name, 10, 16);
            glUniform.setLocation(GlUniform.getUniformLocation((int)shaderProgram.getGlRef(), (CharSequence)name));
            shaderProgram.addUniform(glUniform);
        }
        shaderProgram.getUniform(name).set(value);
        shaderProgram.getUniform(name).upload();
    }

    public void method1792() {
        RenderSystem.getShader().bind();
    }

    public VertexFormat method1793(ShaderProgram shaderProgram) {
        return (VertexFormat)this.field2196.get(shaderProgram);
    }

    public void method1794() {
        RenderSystem.getShader().unbind();
    }

    public void method1795(String name, int ... values) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            int n = values.length - 1;
            GlUniform glUniform = new GlUniform(name, n, values.length);
            glUniform.setLocation(GlUniform.getUniformLocation((int)shaderProgram.getGlRef(), (CharSequence)name));
            shaderProgram.addUniform(glUniform);
        }
        GlUniform glUniform = shaderProgram.getUniform(name);
        switch (values.length) {
            case 1: {
                glUniform.set(values[0]);
                break;
            }
            case 2: {
                glUniform.set(values[0], values[1]);
                break;
            }
            case 3: {
                glUniform.set(values[0], values[1], values[2]);
                break;
            }
            case 4: {
                glUniform.set(values[0], values[1], values[2], values[3]);
            }
        }
        glUniform.upload();
    }

    public void method1796(String name) {
        RenderSystem.setShader((ShaderProgram)this.method1799(name));
    }

    public void method1797(String name, int ... values) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            int n = values.length - 1;
            GlUniform glUniform = new GlUniform(name, n, values.length);
            glUniform.setLocation(GlUniform.getUniformLocation((int)shaderProgram.getGlRef(), (CharSequence)name));
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

    public void method1798() {
        field2195 = false;
        this.field2198.clear();
        this.field2196.clear();
        this.method1789();
    }

    public ShaderProgram method1799(String name) {
        return this.field2198.getOrDefault(name, null);
    }

    public Class281() {
        this.field2196 = new HashMap();
    }

    public void method1800(String name, float ... values) {
        if (RenderSystem.getShader() == null) {
            return;
        }
        ShaderProgram shaderProgram = RenderSystem.getShader();
        if (shaderProgram.getUniform(name) == null) {
            int n = values.length + 3;
            GlUniform glUniform = new GlUniform(name, n, values.length);
            glUniform.setLocation(GlUniform.getUniformLocation((int)shaderProgram.getGlRef(), (CharSequence)name));
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
