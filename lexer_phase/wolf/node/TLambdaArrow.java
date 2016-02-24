/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class TLambdaArrow extends Token
{
    public TLambdaArrow()
    {
        super.setText("->");
    }

    public TLambdaArrow(int line, int pos)
    {
        super.setText("->");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TLambdaArrow(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTLambdaArrow(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TLambdaArrow text.");
    }
}