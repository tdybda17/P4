/* Generated By:JJTree: Do not edit this line. ASTPROG.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package Compiler.Parser.GeneratedFiles;

public
class ASTPROG extends SimpleNode {
  public ASTPROG(int id) {
    super(id);
  }

  public ASTPROG(TestParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(TestParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d7ee6ec00a862bfd03e5bd128da6e302 (do not edit this line) */