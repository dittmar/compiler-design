/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class ADefHeader extends PDefHeader
{
    private TDef _def_;
    private TIdentifier _identifier_;
    private PSig _sig_;
    private TAssign _assign_;
    private PFunc _func_;

    public ADefHeader()
    {
        // Constructor
    }

    public ADefHeader(
        @SuppressWarnings("hiding") TDef _def_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") PSig _sig_,
        @SuppressWarnings("hiding") TAssign _assign_,
        @SuppressWarnings("hiding") PFunc _func_)
    {
        // Constructor
        setDef(_def_);

        setIdentifier(_identifier_);

        setSig(_sig_);

        setAssign(_assign_);

        setFunc(_func_);

    }

    @Override
    public Object clone()
    {
        return new ADefHeader(
            cloneNode(this._def_),
            cloneNode(this._identifier_),
            cloneNode(this._sig_),
            cloneNode(this._assign_),
            cloneNode(this._func_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADefHeader(this);
    }

    public TDef getDef()
    {
        return this._def_;
    }

    public void setDef(TDef node)
    {
        if(this._def_ != null)
        {
            this._def_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._def_ = node;
    }

    public TIdentifier getIdentifier()
    {
        return this._identifier_;
    }

    public void setIdentifier(TIdentifier node)
    {
        if(this._identifier_ != null)
        {
            this._identifier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._identifier_ = node;
    }

    public PSig getSig()
    {
        return this._sig_;
    }

    public void setSig(PSig node)
    {
        if(this._sig_ != null)
        {
            this._sig_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._sig_ = node;
    }

    public TAssign getAssign()
    {
        return this._assign_;
    }

    public void setAssign(TAssign node)
    {
        if(this._assign_ != null)
        {
            this._assign_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._assign_ = node;
    }

    public PFunc getFunc()
    {
        return this._func_;
    }

    public void setFunc(PFunc node)
    {
        if(this._func_ != null)
        {
            this._func_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._func_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._def_)
            + toString(this._identifier_)
            + toString(this._sig_)
            + toString(this._assign_)
            + toString(this._func_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._def_ == child)
        {
            this._def_ = null;
            return;
        }

        if(this._identifier_ == child)
        {
            this._identifier_ = null;
            return;
        }

        if(this._sig_ == child)
        {
            this._sig_ = null;
            return;
        }

        if(this._assign_ == child)
        {
            this._assign_ = null;
            return;
        }

        if(this._func_ == child)
        {
            this._func_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._def_ == oldChild)
        {
            setDef((TDef) newChild);
            return;
        }

        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        if(this._sig_ == oldChild)
        {
            setSig((PSig) newChild);
            return;
        }

        if(this._assign_ == oldChild)
        {
            setAssign((TAssign) newChild);
            return;
        }

        if(this._func_ == oldChild)
        {
            setFunc((PFunc) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
