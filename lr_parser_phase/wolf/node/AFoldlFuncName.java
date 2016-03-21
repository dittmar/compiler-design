/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class AFoldlFuncName extends PFuncName
{
    private TFoldl _foldl_;

    public AFoldlFuncName()
    {
        // Constructor
    }

    public AFoldlFuncName(
        @SuppressWarnings("hiding") TFoldl _foldl_)
    {
        // Constructor
        setFoldl(_foldl_);

    }

    @Override
    public Object clone()
    {
        return new AFoldlFuncName(
            cloneNode(this._foldl_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFoldlFuncName(this);
    }

    public TFoldl getFoldl()
    {
        return this._foldl_;
    }

    public void setFoldl(TFoldl node)
    {
        if(this._foldl_ != null)
        {
            this._foldl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._foldl_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._foldl_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._foldl_ == child)
        {
            this._foldl_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._foldl_ == oldChild)
        {
            setFoldl((TFoldl) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
