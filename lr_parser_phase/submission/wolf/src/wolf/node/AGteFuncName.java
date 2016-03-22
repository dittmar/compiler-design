/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class AGteFuncName extends PFuncName
{
    private TGte _gte_;

    public AGteFuncName()
    {
        // Constructor
    }

    public AGteFuncName(
        @SuppressWarnings("hiding") TGte _gte_)
    {
        // Constructor
        setGte(_gte_);

    }

    @Override
    public Object clone()
    {
        return new AGteFuncName(
            cloneNode(this._gte_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAGteFuncName(this);
    }

    public TGte getGte()
    {
        return this._gte_;
    }

    public void setGte(TGte node)
    {
        if(this._gte_ != null)
        {
            this._gte_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._gte_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._gte_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._gte_ == child)
        {
            this._gte_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._gte_ == oldChild)
        {
            setGte((TGte) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
