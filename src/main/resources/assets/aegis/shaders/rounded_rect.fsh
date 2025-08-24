#version 150

in vec4 vertexColor;
in vec2 texCoord;

uniform vec2 Size;
uniform float Radius;
uniform vec4 ColorOverride;
uniform float BorderWidth;
uniform vec4 BorderColor;

out vec4 fragColor;

float roundedRectSDF(vec2 uv, float radius) {
    // 将纹理坐标从[0,1]空间转换为[-0.5,0.5]空间
    vec2 center = vec2(0.5, 0.5);
    vec2 point = uv - center;

    // 边缘到中心的距离
    vec2 halfSize = vec2(0.5, 0.5);
    vec2 cornerDist = abs(point) - halfSize + radius;
    float dist = length(max(cornerDist, 0.0)) + min(max(cornerDist.x, cornerDist.y), 0.0) - radius;

    // 将距离缩放回实际像素空间
    return dist * min(Size.x, Size.y);
}

void main() {
    float distance = roundedRectSDF(texCoord, Radius / min(Size.x, Size.y));

    // 边缘抗锯齿
    float alpha = 1.0 - smoothstep(-0.5, 0.5, distance);

    // 处理边框
    float borderMask = 1.0 - smoothstep(BorderWidth - 0.5, BorderWidth + 0.5, -distance);
    borderMask *= alpha; // 确保边框也是圆角的

    vec4 fillColor = ColorOverride.a > 0.0 ? ColorOverride : vertexColor;
    vec4 finalColor = mix(fillColor, BorderColor, borderMask * BorderColor.a);

    fragColor = vec4(finalColor.rgb, finalColor.a * alpha);
}


