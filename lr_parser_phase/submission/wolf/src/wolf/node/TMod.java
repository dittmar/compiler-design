/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class TMod extends Token
{
    public TMod()
    {
        super.setText("%");
    }

    public TMod(int line, int pos)
    {
        super.setText("%");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TMod(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTMod(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TMod text.");
    }
}
