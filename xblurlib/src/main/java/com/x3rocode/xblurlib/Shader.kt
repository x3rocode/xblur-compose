package com.x3rocode.xblurlib

import org.intellij.lang.annotations.Language

@Language("AGSL")
val GET_BEHIND_CANVAS="""
uniform shader background;
layout(color) uniform half4 returnColor;

 vec4 main(float2 fragCoord) {
 
    return mix( background.eval(fragCoord), returnColor,  1);
 }
"""

@Language("AGSL")
val SET_COLOR_FILTER="""
uniform shader background;
layout(color) uniform half4 filterColor;
uniform float alpha;
 vec4 main(float2 fragCoord) {
    return mix( background.eval(fragCoord), filterColor,  alpha);
 }
"""