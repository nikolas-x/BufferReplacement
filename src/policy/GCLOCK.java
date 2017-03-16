package policy;

import structure.Page;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

/**
 * Generalised Clock policy
 *
 * A clock policy attempts to combined the benefits of a least-recently-used and a least-frequently-used policy to
 * maximise the utilisation of temporal locality (i.e. if a page is being used frequently, it will probably be used
 * frequently in the near future and is worth keeping). It is also known as a "second chance" policy.
 *
 * Each frame has a referenced value that is incremented by 1 on each page request. When page eviction is required,
 * the reference value is analysed (a.k.a. pointed to by the clock "hand"):
 * - If the value is greater than 0, it is decremented and we move on to the next page
 * - If the value is 0, we select this page for replacement
 * - If we reach the end of the buffer, we reset the position of the "hand" of the clock back to the start.
 */
public class GCLOCK extends Policy
{
    protected int cursorPos;

    public GCLOCK(List<Page> sequence, int bufferSize)
    {
        super(sequence, bufferSize);
        cursorPos = bufferSize - 1;
    }

    @Override
    protected void pageRequest(Page p)
    {
        // No matter what, a page request will result in an incremented reference value
        incrementReference(p);
        if (buffer.contains(p))
        {
            // Cache hit: no effect
            return;
        }

        ++io;

        if (buffer.size() < bufferSize)
        {
            // Cache miss, but the buffer was not full
            buffer.addFirst(p);
            return;
        }

        // We create an array for easier iteration and access
        Page[] bufferArray = buffer.toArray(new Page[0]);
        Page cursor;
        while (true)
        {
            cursor = bufferArray[cursorPos];
            if (cursor.getReference() == 0)
            {
                // Eviction candidate found
                // We update the array with the new page and recreate the buffer from this array
                bufferArray[cursorPos] = p;
                tick();
                buffer = new ArrayDeque<>(Arrays.asList(bufferArray));
                return;
            }

            // We decrement the reference and keep looking
            decrementReference(cursor);
            tick();
        }
    }

    /**
     * Helper method representing the changing in position of the cursor ("hand" of the clock)
     *
     * @return the new cursor position
     */
    protected int tick()
    {
        --cursorPos;
        if (cursorPos < 0)
        {
            cursorPos = bufferSize - 1;
        }
        return cursorPos;
    }

    /**
     * Interface method for incrementing the reference of the page
     *
     * @param p page to be updated
     * @return new reference value
     */
    protected int incrementReference(Page p)
    {
        return p.incrementReference();
    }

    /**
     * Interface method for decrementing the reference of the page
     *
     * @param p page to be updated
     * @return new reference value
     */
    protected int decrementReference(Page p)
    {
        return p.decrementReference();
    }
}
