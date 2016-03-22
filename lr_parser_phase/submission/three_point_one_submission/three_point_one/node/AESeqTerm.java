/* This file was generated by SableCC (http://www.sablecc.org/). */

package three_point_one.node;

import three_point_one.analysis.*;

@SuppressWarnings("nls")
public final class AESeqTerm extends PTerm
{
    private PESeq _eSeq_;

    public AESeqTerm()
    {
        // Constructor
    }

    public AESeqTerm(
        @SuppressWarnings("hiding") PESeq _eSeq_)
    {
        // Constructor
        setESeq(_eSeq_);

    }

    @Override
    public Object clone()
    {
        return new AESeqTerm(
            cloneNode(this._eSeq_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAESeqTerm(this);
    }

    public PESeq getESeq()
    {
        return this._eSeq_;
    }

    public void setESeq(PESeq node)
    {
        if(this._eSeq_ != null)
        {
            this._eSeq_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._eSeq_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._eSeq_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._eSeq_ == child)
        {
            this._eSeq_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._eSeq_ == oldChild)
        {
            setESeq((PESeq) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
