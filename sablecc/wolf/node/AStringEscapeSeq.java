/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.node;

import wolf.analysis.*;

@SuppressWarnings("nls")
public final class AStringEscapeSeq extends PStringEscapeSeq
{
    private TStringEscape _stringEscape_;
    private PEscapeChar _escapeChar_;

    public AStringEscapeSeq()
    {
        // Constructor
    }

    public AStringEscapeSeq(
        @SuppressWarnings("hiding") TStringEscape _stringEscape_,
        @SuppressWarnings("hiding") PEscapeChar _escapeChar_)
    {
        // Constructor
        setStringEscape(_stringEscape_);

        setEscapeChar(_escapeChar_);

    }

    @Override
    public Object clone()
    {
        return new AStringEscapeSeq(
            cloneNode(this._stringEscape_),
            cloneNode(this._escapeChar_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStringEscapeSeq(this);
    }

    public TStringEscape getStringEscape()
    {
        return this._stringEscape_;
    }

    public void setStringEscape(TStringEscape node)
    {
        if(this._stringEscape_ != null)
        {
            this._stringEscape_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stringEscape_ = node;
    }

    public PEscapeChar getEscapeChar()
    {
        return this._escapeChar_;
    }

    public void setEscapeChar(PEscapeChar node)
    {
        if(this._escapeChar_ != null)
        {
            this._escapeChar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._escapeChar_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._stringEscape_)
            + toString(this._escapeChar_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._stringEscape_ == child)
        {
            this._stringEscape_ = null;
            return;
        }

        if(this._escapeChar_ == child)
        {
            this._escapeChar_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._stringEscape_ == oldChild)
        {
            setStringEscape((TStringEscape) newChild);
            return;
        }

        if(this._escapeChar_ == oldChild)
        {
            setEscapeChar((PEscapeChar) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
