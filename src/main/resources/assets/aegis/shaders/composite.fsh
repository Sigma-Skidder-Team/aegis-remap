#version 150
in vec2 texCoord;
out vec4 fragColor;

uniform sampler2D BaseTexture;
uniform sampler2D BloomTexture;
uniform float Intensity;
uniform vec4 BloomColor;
uniform int HasColor = 1;

void main() {
    vec4 baseColor = texture(BaseTexture, texCoord);
    vec4 bloomSample = texture(BloomTexture, texCoord);

    // 判断 Bloom 区域是否有内容
    if (bloomSample.a > 0.0) {
        // 检测BloomColor是亮色还是暗色
        vec4 colorToUse = BloomColor;
        if (HasColor == 0) {
            colorToUse = bloomSample;
        }
        float luminance = (colorToUse.r + colorToUse.g + colorToUse.b) / 3.0;

        if (luminance >= 0.5) {
            // 亮色处理: 增亮效果 (原来的行为)
            vec3 bloomColor = bloomSample.rgb * colorToUse.rgb;
            fragColor = vec4(baseColor.rgb + bloomColor, baseColor.a);
        } else {
            // 暗色处理: 降暗效果
            // 使用相对于中性灰的偏移量来决定暗化程度
            vec3 darkFactor = (vec3(0.5) - colorToUse.rgb) * 2.0 * Intensity;
            // 根据bloomSample和darkFactor的强度来降低基础颜色
            vec3 darkBloom = baseColor.rgb * (1.0 - bloomSample.rgb * darkFactor);
            fragColor = vec4(darkBloom, baseColor.a);
        }
    } else {
        fragColor = baseColor;
    }
}