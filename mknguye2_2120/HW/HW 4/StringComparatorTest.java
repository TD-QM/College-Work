import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class StringComparatorTest {

    @Test
    void compareTest() {
        assertTrue(StringComparator.compare("Blub", "Blob") > 0);
        assertTrue(StringComparator.compare("Blob", "Blub") < 0);
        assertTrue(StringComparator.compare("Blub", "Blub") == 0);
        assertTrue(StringComparator.compare("These are some random words", "Blob") > 0);
        assertTrue(StringComparator.compare("", "a") < 0);
        assertTrue(StringComparator.compare("Blub", "") > 0);
        assertTrue(StringComparator.compare("", "") == 0);
        assertTrue(StringComparator.compare("a", "b") < 0);
    }

    @Test
    void findMinimumTest() {
        String[] sa1 = {"Blob",  "Blorg", "Blurf", "Zorpy", "Galoge", "Mulfry", "Plettarg", "Blub", "Ascog", "Asfog", "Rhalps", "Fypio"};
        String[] sa2 = {"A", "a", "aaaaAAAA", "Decay is an extant form of life."};
        String[] sa3 = {"This is a test", "This is also a test", "This is yet another test."};

        assertEquals("Ascog", StringComparator.findMinimum(sa1, sa1.length));
        assertEquals("A", StringComparator.findMinimum(sa2, sa2.length));
        assertEquals("This is a test", StringComparator.findMinimum(sa3, sa3.length));

    }

}