#version 150
in vec2 texCoord;
out vec4 fragColor;
uniform sampler2D BloomTexture;
void main() {
    fragColor = texture(BloomTexture, texCoord);
}