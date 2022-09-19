import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class DieTester {

    /**
     * A variable for a default die with 6 sides
     */
    private Die defaultDie;

    /**
     * A variable for a die with 5 sides
     */
    private Die fiveSidedDie;

    /**
     * A setup method that resets the objects stored in the variables defaultDie and fiveSidedDie
     *
     * @see Die#Die()
     * @see Die#Die(int)
     */
    @BeforeEach
    public void setup(){
        defaultDie = new Die();
        fiveSidedDie = new Die(5);
    }

    /**
     * A tester method for the rolling of a default 6 sided die
     *
     * @see Die#roll()
     */
    @Test
    public void testDefaultRoll(){
        int roll = defaultDie.roll();
        int min = 1;
        int max = 6;

        assertTrue(roll >= min && roll <= max);
    }

    /**
     * A tester method for the rolling of a 5 sided die
     *
     * @see Die#roll()
     */
    @Test
    public void test5SideRoll(){
        int roll = fiveSidedDie.roll();
        int min = 1;
        int max = 5;

        assertTrue(roll >= min && roll <= max);
    }

    /**
     * A tester method for getting the number of sides for a default 6 sided die
     *
     * @see Die#getNumSides()
     */
    @Test
    public void testDefaultNumSides(){
        assertEquals(6, defaultDie.getNumSides());
    }

    /**
     * A tester method for getting the number of sides for a 5 sided die
     *
     * @see Die#getNumSides()
     */
    @Test
    public void test5NumSides(){
        assertEquals(5, fiveSidedDie.getNumSides());
    }

    /**
     * A tester method for getting the default result of a 6 sided die
     *
     * @see Die#getResult()
     */
    @Test
    public void testGetDefaultResult(){
        assertEquals(1, defaultDie.getResult());
    }

    /**
     * A tester method for getting the default result of a 5 sided die
     *
     * @see Die#getResult()
     */
    @Test
    public void testGet5SideResult(){
        assertEquals(1, fiveSidedDie.getResult());
    }

    /**
     * A tester method for testing equality of two similar dice objects
     *
     * @see Die#equals(Object)
     */
    @Test
    public void testEqualsTrue(){
        Die otherDie = new Die();

        assertTrue(defaultDie.equals(otherDie));
    }

    /**
     * A tester method for testing inequality of two different dice objects
     *
     * @see Die#equals(Object)
     */
    @Test
    public void testEqualsFalse(){
        assertFalse(defaultDie.equals(fiveSidedDie));
    }

    /**
     * A tester method for testing the toString for a default 6 sided die
     *
     * @see Die#toString()
     */
    @Test
    public void testDefaultToString(){
        assertEquals("Num sides 6 result 1", defaultDie.toString());
    }

    /**
     * A tester method for testing the toString for a default 5 sided die
     *
     * @see Die#toString()
     */
    @Test
    public void test5SideToString(){
        assertEquals("Num sides 5 result 1", fiveSidedDie.toString());
    }




}