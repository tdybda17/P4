/* This file was generated by SableCC (http://www.sablecc.org/). */

package Compiler.SableCC.postfix.node;

import Compiler.SableCC.postfix.analysis.*;

@SuppressWarnings("nls")
public final class TEdge extends Token
{
    public TEdge()
    {
        super.setText("Edge");
    }

    public TEdge(int line, int pos)
    {
        super.setText("Edge");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TEdge(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTEdge(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TEdge text.");
    }
}
