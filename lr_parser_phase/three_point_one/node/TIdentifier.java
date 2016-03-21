/* This file was generated by SableCC (http://www.sablecc.org/). */

package three_point_one.node;

import three_point_one.analysis.*;

@SuppressWarnings("nls")
public final class TIdentifier extends Token
{
    public TIdentifier(String text)
    {
        setText(text);
    }

    public TIdentifier(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TIdentifier(getText(), getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTIdentifier(this);
    }
}
