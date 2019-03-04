/* This file was generated by SableCC (http://www.sablecc.org/). */

package Compiler.SableCC.postfix.node;

import Compiler.SableCC.postfix.analysis.*;

@SuppressWarnings("nls")
public final class TFunction extends Token
{
    public TFunction()
    {
        super.setText("function");
    }

    public TFunction(int line, int pos)
    {
        super.setText("function");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TFunction(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTFunction(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TFunction text.");
    }
}
