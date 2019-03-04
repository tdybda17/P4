/* This file was generated by SableCC (http://www.sablecc.org/). */

package Compiler.SableCC.postfix.analysis;

import Compiler.SableCC.postfix.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAFactorExpr(AFactorExpr node);
    void caseAPlusExpr(APlusExpr node);
    void caseAMinusExpr(AMinusExpr node);
    void caseATermFactor(ATermFactor node);
    void caseAMultFactor(AMultFactor node);
    void caseADivFactor(ADivFactor node);
    void caseAModFactor(AModFactor node);
    void caseANumberTerm(ANumberTerm node);
    void caseAExprTerm(AExprTerm node);

    void caseTNumber(TNumber node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTMult(TMult node);
    void caseTDiv(TDiv node);
    void caseTMod(TMod node);
    void caseTLPar(TLPar node);
    void caseTRPar(TRPar node);
    void caseTBlank(TBlank node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}
