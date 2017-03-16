package policy;

import structure.Page;
import java.util.*;

/**
 * Abstract Policy Class
 *
 * This class defines the necessary components of a policy.
 * - The method of execution is the same for all policies so this method does not need to be overridden
 * - Each policy will include a sequence of structure requests, a buffer, the buffer size, a count of the amount of disk I/O
 *   required, and a record of each step
 * - A simulation of a structure request, including a structure replacement scheme. This depends on the policy so this must be
 *   defined by a policy-specific child class. This encapsulates the entire definition of each policy
 */
public abstract class Policy
{
    private List<Page> sequence;
    protected Deque<Page> buffer;
    protected int bufferSize;
    protected int io;
    protected List<String> steps;

    public Policy(List<Page> sequence, int bufferSize)
    {
        this.sequence = sequence;
        buffer = new ArrayDeque<Page>();
        this.bufferSize = bufferSize;
        io = 0;
        steps = new ArrayList<>();
    }

    public void execute()
    {
        // Ensure the reference value is 0 for all pages at the start of the algorithm
        for (Page p : sequence)
        {
            p.resetReference();
        }

        // For each structure in the sequence, perform the replacement policy and record the state
        int stepNumber = 1;
        for (Page p : sequence)
        {
            pageRequest(p);

            // TODO: Create a "Step" class rather than relying on strings (avoid stringly-typed programming)
            steps.add("t"
                    + (stepNumber++)
                    + ": "
                    + p
                    + ", "
                    + buffer.toString()
                    + ", "
                    + io);
        }
    }

    /**
     * Simulate a structure request
     *
     * A replacement policy will need to:
     * - keep track of how many times a structure was requested which was not in the buffer (this includes the early stage
     *   of filling a buffer from 0 to bufferSize)
     * - decide which structure to remove from the buffer if the buffer is full
     *
     * @param p
     */
    protected abstract void pageRequest(Page p);

    /**
     * Factory method for generating a sequence of pages from a sequence of characters
     *
     * @param identifiers array of single-character names for each structure;
     *                    duplicates are considered as references to the same structure
     * @return a list of pages representing a stream of structure requests
     */
    public static List<Page> createSequence(char... identifiers)
    {
        List<Page> sequence = new ArrayList<>();
        Map<Character, Page> seen = new HashMap<>();
        for (char c : identifiers)
        {
            // If we haven't seen a structure with this identifier before, we create a new instance with the identifier
            // Otherwise, we simply add a reference to the existing structure
            if (!seen.containsKey(c))
            {
                seen.put(c, new Page(c));
            }
            sequence.add(seen.get(c));
        }
        return sequence;
    }

    /**
     * Returns the number of I/O operations required for the stream of structure requests with the given policy
     * It is only suitable to call this after a call to execute()
     *
     * @return number of I/O requests
     */
    public int getIO()
    {
        return io;
    }

    /**
     * Returns a copy of the list of steps so that the original is not accidentally modified
     * It is only suitable to call this after a call to execute()
     *
     * @return the list of steps taken
     */
    public List<String> getSteps()
    {
        return new ArrayList<>(steps);
    }
}
