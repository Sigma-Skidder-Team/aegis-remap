#version 150

in vec3 Position;          // 顶点位置属性
in vec2 TexCoord;    // 纹理坐标属性
uniform mat4 ModelViewMat;  // 变换矩阵
uniform mat4 ProjMat;

out vec2 texCoord;             // 输出纹理坐标

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texCoord = TexCoord.xy;  // 传递纹理坐标
}