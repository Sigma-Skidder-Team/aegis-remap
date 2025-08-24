#version 150

in vec2 texCoord;
out vec4 fragColor;

uniform sampler2D Texture;
uniform vec2 Direction;
uniform float Radius = 15.0;

void main() {
    ivec2 texSize = textureSize(Texture, 0);
    vec2 vec2Size = vec2(texSize);
    vec2 texelSize = 1.0 / vec2Size;
    vec2 pixelDir = Direction * texelSize;

    float weight = 0.0;
    vec4 result = vec4(0.0);

    // 高斯权重参数
    float sigma = Radius / 3.0;
    float twoSigmaSq = 2.0 * sigma * sigma;
    float normalizeFactor = 1.0 / (sqrt(6.28318) * sigma);

    // 高斯模糊采样
    for (float i = -Radius; i <= Radius; i++) {
        float w = normalizeFactor * exp(-(i*i) / twoSigmaSq);
        result += texture(Texture, texCoord + i * pixelDir) * w;
        weight += w;
    }

    fragColor = result / weight;
}