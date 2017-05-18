//FRAGMENT SHADER

/**
 * Timer.frag
 * Renders the timer where the alpha channel defines how full the timer is
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/13/2017
 */

#ifdef GL_ES
	precision mediump float;
#endif

const float RADIUS = 0.6;
const float SOFTNESS = 0.45;
const vec3 GRAY = vec3(0.299, 0.587, 0.114);
const vec3 SEPIA = vec3(1.2, 1.0, 0.8);
const float SEPIA_AMOUNT = 0.8;
const float BLACK_FADE_AMOUNT = 0.7;

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
//screen resolution
uniform vec2 u_resolution;

void main()
{
	vec4 color = texture2D(u_texture, v_texCoords).rgba;

	//vignette
	vec2 position = (gl_FragCoord.xy / u_resolution.xy) - vec2(1.0 / 2.0);
	float vignette = smoothstep(RADIUS, RADIUS - SOFTNESS, length(position));
	color.rgb = color.rgb * vignette;

	//sepia
	vec3 sepia = vec3(dot(color.rgb, GRAY)) * SEPIA;

	//final
	vec3 final = mix(mix(color.rgb, sepia, SEPIA_AMOUNT), vec3(0), BLACK_FADE_AMOUNT);
	gl_FragColor = vec4(final, color.a);
}