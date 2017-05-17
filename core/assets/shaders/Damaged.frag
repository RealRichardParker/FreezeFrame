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

const float alphaMultiplier = 0.5;

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main()
{
	vec4 color = texture2D(u_texture, v_texCoords).rgba;
	vec3 final = mix(vec3(1, 0, 0), color.rgb, 0.5 * color.a);

	gl_FragColor = vec4(final, color.a);
}