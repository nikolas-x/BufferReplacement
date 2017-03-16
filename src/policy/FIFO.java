package policy;

import structure.Page;
import java.util.List;

/**
 * First-In-First-Out policy
 *
 * According to this policy, upon receiving a structure request when the buffer is full, the page that has been in the
 * buffer for the longest is removed.
 *
 * This is a simple and intuitive policy, however it does not utilise the concept of temporal locality and is considered
 * to be sub-optimal in most cases. However, this does not preclude it from out-performing LRU/GLOCK given certain
 * circumstances.
 */
public class FIFO extends Policy
{
    public FIFO(List<Page> sequence, int bufferSize)
    {
        super(sequence, bufferSize);
    }

    @Override
    protected void pageRequest(Page p)
    {
        if (buffer.contains(p))
        {
            // No effect in the event of a cache hit
            // This emphasises the lack of utilisation of critical information about the pages in this policy
            return;
        }

        ++io;

        if (buffer.size() < bufferSize)
        {
            // The buffer has spare frames, therefore no replacement is required.
            // However, we are still loading the page from secondary memory.
            buffer.addFirst(p);
            return;
        }

        // Replace the oldest(tail of the queue) page with the new page
        // The new page is now the most-recently-used
        buffer.removeLast();
        buffer.addFirst(p);
    }
}
