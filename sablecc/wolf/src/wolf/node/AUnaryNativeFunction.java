/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class AUnaryNativeFunction extends PFunction
{
    private PNativeUnaryOp _nativeUnaryOp_;
    private TLParen _lParen_;
    private PArg _arg_;
    private TRParen _rParen_;

    public AUnaryNativeFunction()
    {
        // Constructor
    }

    public AUnaryNativeFunction(
        @SuppressWarnings("hiding") PNativeUnaryOp _nativeUnaryOp_,
        @SuppressWarnings("hiding") TLParen _lParen_,
        @SuppressWarnings("hiding") PArg _arg_,
        @SuppressWarnings("hiding") TRParen _rParen_)
    {
        // Constructor
        setNativeUnaryOp(_nativeUnaryOp_);

        setLParen(_lParen_);

        setArg(_arg_);

        setRParen(_rParen_);

    }

    @Override
    public Object clone()
    {
        return new AUnaryNativeFunction(
            cloneNode(this._nativeUnaryOp_),
            cloneNode(this._lParen_),
            cloneNode(this._arg_),
            cloneNode(this._rParen_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAUnaryNativeFunction(this);
    }

    public PNativeUnaryOp getNativeUnaryOp()
    {
        return this._nativeUnaryOp_;
    }

    public void setNativeUnaryOp(PNativeUnaryOp node)
    {
        if(this._nativeUnaryOp_ != null)
        {
            this._nativeUnaryOp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._nativeUnaryOp_ = node;
    }

    public TLParen getLParen()
    {
        return this._lParen_;
    }

    public void setLParen(TLParen node)
    {
        if(this._lParen_ != null)
        {
            this._lParen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lParen_ = node;
    }

    public PArg getArg()
    {
        return this._arg_;
    }

    public void setArg(PArg node)
    {
        if(this._arg_ != null)
        {
            this._arg_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._arg_ = node;
    }

    public TRParen getRParen()
    {
        return this._rParen_;
    }

    public void setRParen(TRParen node)
    {
        if(this._rParen_ != null)
        {
            this._rParen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rParen_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._nativeUnaryOp_)
            + toString(this._lParen_)
            + toString(this._arg_)
            + toString(this._rParen_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._nativeUnaryOp_ == child)
        {
            this._nativeUnaryOp_ = null;
            return;
        }

        if(this._lParen_ == child)
        {
            this._lParen_ = null;
            return;
        }

        if(this._arg_ == child)
        {
            this._arg_ = null;
            return;
        }

        if(this._rParen_ == child)
        {
            this._rParen_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._nativeUnaryOp_ == oldChild)
        {
            setNativeUnaryOp((PNativeUnaryOp) newChild);
            return;
        }

        if(this._lParen_ == oldChild)
        {
            setLParen((TLParen) newChild);
            return;
        }

        if(this._arg_ == oldChild)
        {
            setArg((PArg) newChild);
            return;
        }

        if(this._rParen_ == oldChild)
        {
            setRParen((TRParen) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}