package policy;

import structure.Page;
import java.util.List;

/**
 * Most-Recently-Used policy
 *
 * According to this policy, upon receiving a structure request when the buffer is full, the most-recently-used page is
 * selected for replacement.
 *
 * This seems counter-intuitive at a glance, but in some specific examples this may actually result in fewer cache
 * misses than Least-Recently-Used. However, this relies on coincidence more than intuition.
 *
 * We consider the back of the queue to be the least-recently-used while the front is the most-recently-used.
 */
public class MRU extends Policy
{
    public MRU(List<Page> sequence, int bufferSize)
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
        // Replace the most-recently-used (head of the queue) page with the new page
        // The new page is now the most-recently-used
        buffer.removeFirst();
        buffer.addFirst(p);
    }
}
