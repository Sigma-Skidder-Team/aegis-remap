#version 150

uniform sampler2D inTexture;
uniform vec2 offset, halfpixel, iResolution;

in vec2 texCoord;  // 替换 gl_TexCoord[0].st
out vec4 fragColor; // 替换 gl_FragColor

void main() {
    vec2 uv = gl_FragCoord.xy / iResolution;

    vec4 sum = texture(inTexture, texCoord);
    sum.rgb *= sum.a;
    sum *= 4.0;

    vec4 smp1 = texture(inTexture, uv - halfpixel.xy * offset);
    smp1.rgb *= smp1.a;
    sum += smp1;

    vec4 smp2 = texture(inTexture, uv + halfpixel.xy * offset);
    smp2.rgb *= smp2.a;
    sum += smp2;

    vec4 smp3 = texture(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);
    smp3.rgb *= smp3.a;
    sum += smp3;

    vec4 smp4 = texture(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);
    smp4.rgb *= smp4.a;
    sum += smp4;

    vec4 result = sum / 8.0;
    fragColor = vec4(result.rgb / result.a, result.a);
}