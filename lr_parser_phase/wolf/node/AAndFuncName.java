/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class AAndFuncName extends PFuncName
{
    private TAnd _and_;

    public AAndFuncName()
    {
        // Constructor
    }

    public AAndFuncName(
        @SuppressWarnings("hiding") TAnd _and_)
    {
        // Constructor
        setAnd(_and_);

    }

    @Override
    public Object clone()
    {
        return new AAndFuncName(
            cloneNode(this._and_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAndFuncName(this);
    }

    public TAnd getAnd()
    {
        return this._and_;
    }

    public void setAnd(TAnd node)
    {
        if(this._and_ != null)
        {
            this._and_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._and_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._and_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._and_ == child)
        {
            this._and_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._and_ == oldChild)
        {
            setAnd((TAnd) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
