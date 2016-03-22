/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class AFloatArg extends PArg
{
    private TFloatNumber _floatNumber_;

    public AFloatArg()
    {
        // Constructor
    }

    public AFloatArg(
        @SuppressWarnings("hiding") TFloatNumber _floatNumber_)
    {
        // Constructor
        setFloatNumber(_floatNumber_);

    }

    @Override
    public Object clone()
    {
        return new AFloatArg(
            cloneNode(this._floatNumber_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFloatArg(this);
    }

    public TFloatNumber getFloatNumber()
    {
        return this._floatNumber_;
    }

    public void setFloatNumber(TFloatNumber node)
    {
        if(this._floatNumber_ != null)
        {
            this._floatNumber_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._floatNumber_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._floatNumber_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._floatNumber_ == child)
        {
            this._floatNumber_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._floatNumber_ == oldChild)
        {
            setFloatNumber((TFloatNumber) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
