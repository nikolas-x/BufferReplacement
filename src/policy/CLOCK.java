package policy;

import structure.Page;
import java.util.List;

/**
 * Clock policy
 *
 * This is a simpler version of GCLOCK (Generalised Clock) where instead of a reference value we have a reference bit
 * which can be 0 or 1. Therefore it is in-fact a special case of GCLOCK (hence the lack of the "generalised" part)
 * where the reference value cannot exceed 1 and it is implemented as such.
 *
 * This is considered sub-optimal compared to GCLOCK, but is generally easier to implement.
 */
public class CLOCK extends GCLOCK
{
    public CLOCK(List<Page> sequence, int bufferSize)
    {
        super(sequence, bufferSize);
    }

    @Override
    protected int incrementReference(Page p)
    {
        // Only increment if the reference value is 0, thereby restricting the possible values to [0, 1]
        if (p.getReference() == 0)
        {
            p.incrementReference();
        }
        return 1;
    }
}
