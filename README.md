# spring-boot-starter
写的一些springboot-stater-starter

springboot starter 作用就是简化bean 的配置，以前没有starter 的时候使用了maven依赖之后需要自己对其进行装配，相对来说比较麻烦，但是如果打包成为spring-boot-starter 之后，就能
导入maven 之后就可以直接使用。

这里使用的springboot 3.x版本 jdk 17

>在spring 2.x 的版本中 spring-boot-starter中需要配置 spring.factories 文件,而在3.0的时候废弃了该方法，我们可以使用mica-auto 来进行自动装配
