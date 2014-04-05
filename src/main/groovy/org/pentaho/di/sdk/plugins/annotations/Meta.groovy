package org.pentaho.di.sdk.plugins.annotations

import org.codehaus.groovy.transform.GroovyASTTransformationClass
import java.lang.annotation.*

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["org.pentaho.di.sdk.plugins.annotations.processor.KettleASTTransformation"])
public @interface Meta {
}
