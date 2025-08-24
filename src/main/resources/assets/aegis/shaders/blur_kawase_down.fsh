#version 150
in vec2 texCoord;
out vec4 fragColor;

uniform sampler2D TextureA;
uniform vec2 srcSize;
uniform vec2 dstSize;
uniform vec2 halfpixel;
uniform float offset;

const int directions = 8;
const float pi = 3.1415926535;

void main() {
    vec2 scale = vec2(srcSize / dstSize);
    vec4 color = vec4(0.0);
    for (int i = 0; i < directions; ++i) {
        float angle = pi * 2.0 * float(i) / float(directions);
        vec2 dir = vec2(cos(angle), sin(angle));
        color += texture(TextureA, texCoord * scale + dir * offset * halfpixel);
    }
    fragColor = vec4(color.rgb / float(directions), color.a);
}