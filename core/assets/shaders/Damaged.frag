//FRAGMENT SHADER

/**
 * Damaged.frag
 * Renders the player with a red tint
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/17/2017
 */

#ifdef GL_ES
	precision mediump float;
#endif

const vec3 RED_COLOR = vec3(1, 0, 0);
const float RED_AMOUNT = 0.5;

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main()
{
	vec4 color = texture2D(u_texture, v_texCoords).rgba;
	vec3 final = mix(RED_COLOR, color.rgb, RED_AMOUNT * color.a);

	gl_FragColor = vec4(final, color.a);
}