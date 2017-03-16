import structure.Page;
import policy.*;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        // Select a buffer size and a sequence of pages
        // TODO: Read from stdin
        int bufferSize = 5;
        List<Page> sequence = Policy.createSequence
                ('D', 'A', 'B', 'C', 'E', 'F', 'B', 'C', 'G', 'A', 'E', 'D', 'H', 'C', 'I', 'A', 'J');
        Policy policy;

        // Test the configuration for each policy
        // TODO: allow for specific policy testing rather than testing all at once (again, from stdin)

        // GCLOCK
        System.out.println("GCLOCK");
        policy= new GCLOCK(sequence, bufferSize);
        policy.execute();
        policy.getSteps().forEach(System.out::println);
        System.out.println();

        // CLOCK
        System.out.println("CLOCK");
        policy= new CLOCK(sequence, bufferSize);
        policy.execute();
        policy.getSteps().forEach(System.out::println);
        System.out.println();

        // MRU
        System.out.println("MRU");
        policy= new MRU(sequence, bufferSize);
        policy.execute();
        policy.getSteps().forEach(System.out::println);
        System.out.println();

        // LRU
        System.out.println("LRU");
        policy= new LRU(sequence, bufferSize);
        policy.execute();
        policy.getSteps().forEach(System.out::println);
        System.out.println();

        // FIFO
        System.out.println("FIFO");
        policy= new FIFO(sequence, bufferSize);
        policy.execute();
        policy.getSteps().forEach(System.out::println);
    }
}
