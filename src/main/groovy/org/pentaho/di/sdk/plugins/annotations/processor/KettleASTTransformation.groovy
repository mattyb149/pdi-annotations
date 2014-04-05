package org.pentaho.di.sdk.plugins.annotations.processor

import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.builder.AstBuilder



@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class KettleASTTransformation implements ASTTransformation {

  public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
    List methods = sourceUnit.getAST()?.getMethods()
    // find all methods annotated with @WithLogging
    methods.findAll { MethodNode method ->
      method.getAnnotations(new ClassNode(WithLogging))
    }.each { MethodNode method ->
      Statement startMessage = createPrintlnAst("Starting $method.name")
      Statement endMessage = createPrintlnAst("Ending $method.name")

      List existingStatements = method.getCode().getStatements()
      existingStatements.add(0, startMessage)
      existingStatements.add(endMessage)
    }
  }

  private Statement createPrintlnAst(String message) {
    return new ExpressionStatement(
    new MethodCallExpression(
    new VariableExpression("this"),
    new ConstantExpression("println"),
    new ArgumentListExpression(
    new ConstantExpression(message)
    )
    )
    )
  }
}
