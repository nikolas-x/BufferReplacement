package policy;

import structure.Page;
import java.util.List;

/**
 * Least-Recently-Used policy
 *
 * According to this policy, upon receiving a structure request when the buffer is full, the least-recently-used page is
 * selected for replacement.
 *
 * Intuitively, a page that has not been used for a while is unlikely to be used in the near future (temporal locality).
 *
 * We consider the back of the queue to be the least-recently-used while the front is the most-recently-used.
 */
public class LRU extends Policy
{
    public LRU(List<Page> sequence, int bufferSize)
    {
        super(sequence, bufferSize);
    }

    @Override
    protected void pageRequest(Page p)
    {
        if (buffer.contains(p))
        {
            // Move the page, which was already in the buffer, to the head of the queue
            buffer.remove(p);
            buffer.addFirst(p);
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

        // TODO: Code repetition, consider refactoring
        // Replace the least-recently-used (tail of the queue) page with the new page
        // The new page is now the most-recently-used
        buffer.removeLast();
        buffer.addFirst(p);
    }
}
