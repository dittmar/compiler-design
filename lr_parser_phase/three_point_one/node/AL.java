/* This file was generated by SableCC (http://www.sablecc.org/). */

package three_point_one.node;

import three_point_one.analysis.*;

@SuppressWarnings("nls")
public final class AL extends PL
{

    public AL()
    {
        // Constructor
    }

    @Override
    public Object clone()
    {
        return new AL();
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAL(this);
    }

    @Override
    public String toString()
    {
        return "";
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        throw new RuntimeException("Not a child.");
    }
}
