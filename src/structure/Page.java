package structure;

/**
 * Representation of a data page
 *
 * Includes a single-character identifier and a reference value
 */
public class Page
{
    private char identifier;
    private int reference; // for use in GCLOCK/CLOCK replacement
    // TODO: refactor into CLOCK/GCLOCK since it is only used in those policies

    public Page(char identifier)
    {
        this.identifier = identifier;
        reference = 0;
    }

    public char getIdentifier()
    {
        return identifier;
    }

    public int getReference()
    {
        return reference;
    }

    /**
     * Simple accessor for incrementing the reference value
     *
     * @return the new reference value
     */
    public int incrementReference()
    {
        ++reference;
        return reference;
    }

    /**
     * Simple accessor for decrementing the reference value
     *
     * @return the new reference value
     */
    public int decrementReference()
    {
        if (reference > 0)
        {
            --reference;
        }
        return reference;
    }

    /**
     * Resets the reference value to 0
     */
    public void resetReference()
    {
        reference = 0;
    }

    public String toString()
    {
        return "(" + identifier + "," + reference + ")";
    }
}
