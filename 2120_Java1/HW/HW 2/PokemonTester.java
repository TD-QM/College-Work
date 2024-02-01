import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PokemonTester {

    static Pokemon charmander;
    static Pokemon squirtle;
    static Pokemon bulbasaur;
    static Pokemon pikachu;

    /**
     * A setup method that runs before every single tester that resets the status of the variables
     *
     * @custom.require this.charmander == new Pokemon("Flametail", 39, 6)
     * @custom.require this.squirtle == new Pokemon("Hydrolock", 44, 5)
     * @custom.require this.bulbasaur == new Pokemon("Dryas", 52, 4)
     * @custom.require this.pikachu == new Pokemon("Volt", 28, 7)
     *
     * @see Pokemon#Pokemon(String, int, int)
     */
    @BeforeEach
    void setup(){
        charmander = new Pokemon("Flametail", 39, 6);
        squirtle = new Pokemon("Hydrolock", 44, 5);
        bulbasaur = new Pokemon("Dryas", 52, 4);
        pikachu = new Pokemon("Volt", 28, 7);
    }

    /**
     * Tests the getHealth() method for class Pokemon to ensure it returns the correct information
     *
     * @custom.require charmander.getHealth() == 39
     * @custom.require squirtle.getHealth() == 44
     * @custom.require bulbasaur.getHealth() == 52
     * @custom.require pikachu.getHealth() == 28
     *
     * @see Pokemon#getHealth()
     */
    @Test
    void getHealthTester() {
        assertEquals(39, charmander.getHealth());
        assertEquals(44, squirtle.getHealth());
        assertEquals(52, bulbasaur.getHealth());
        assertEquals(28, pikachu.getHealth());
    }

    /**
     * Tests the getAttackPower() method for class Pokemon to ensure it returns the correct information
     *
     * @custom.require charmander.getAttackPower() == 6
     * @custom.require squirtle.getAttackPower() == 5
     * @custom.require bulbasaur.getAttackPower() == 4
     * @custom.require pikachu.getAttackPower() == 7
     *
     * @see Pokemon#getAttackPower()
     */
    @Test
    void getAttackPowerTester() {
        assertEquals(6, charmander.getAttackPower());
        assertEquals(5, squirtle.getAttackPower());
        assertEquals(4, bulbasaur.getAttackPower());
        assertEquals(7, pikachu.getAttackPower());
    }

    /**
     * Tests the getName() method for class Pokemon to ensure it returns the correct information
     *
     * @custom.require charmander.getName().equals("Flametail")
     * @custom.require squirtle.getName().equals("Hydrolock")
     * @custom.require bulbasaur.getName().equals("Dryas")
     * @custom.require pikachu.getName().equals("Volt")
     *
     * @see Pokemon#getName()
     */
    @Test
    void getNameTester() {
        assertEquals("Flametail", charmander.getName());
        assertEquals("Hydrolock", squirtle.getName());
        assertEquals("Dryas", bulbasaur.getName());
        assertEquals("Volt", pikachu.getName());
    }

    /**
     * Tests the isDead() method for class Pokemon both before and after taking damage to ensure it returns the correct information
     *
     * @custom.require charmander.getHealth() == 39
     * @custom.require squirtle.getHealth() == 44
     * @custom.require bulbasaur.getHealth() == 52
     * @custom.require pikachu.getHealth() == 28
     * @custom.ensure charmander.getHealth() == 0
     * @custom.ensure squirtle.getHealth() == 0
     * @custom.ensure bulbasaur.getHealth() == 32
     * @custom.ensure pikachu.getHealth() == 1
     *
     * @see Pokemon#isDead()
     * @see Pokemon#takeDamage(int, Pokemon)
     */
    @Test
    void isDeadTester() {
        assertEquals(false, charmander.isDead());
        assertEquals(false, squirtle.isDead());
        assertEquals(false, bulbasaur.isDead());
        assertEquals(false, pikachu.isDead());

        charmander.takeDamage(9999, squirtle);
        squirtle.takeDamage(44, charmander);
        bulbasaur.takeDamage(20, pikachu);
        pikachu.takeDamage(27, bulbasaur);

        assertEquals(true, charmander.isDead());
        assertEquals(true, squirtle.isDead());
        assertEquals(false, bulbasaur.isDead());
        assertEquals(false, pikachu.isDead());
    }

    /**
     * Tests the attack() method for class Pokemon using the getHealth() method to ensure it returns the correct information
     *
     * @custom.require charmander.getHealth() == 39
     * @custom.require squirtle.getHealth() == 44
     * @custom.require bulbasaur.getHealth() == 52
     * @custom.require pikachu.getHealth() == 28
     * @custom.ensure charmander.getHealth() == 34
     * @custom.ensure squirtle.getHealth() == 38
     * @custom.ensure bulbasaur.getHealth() == 45
     * @custom.ensure pikachu.getHealth() == 24
     *
     * @see Pokemon#attack(Pokemon)
     * @see Pokemon#getHealth()
     * @see Pokemon#getAttackPower()
     */
    @Test
    void attackTester() {
        assertEquals(6, charmander.getAttackPower());
        assertEquals(5, squirtle.getAttackPower());
        assertEquals(4, bulbasaur.getAttackPower());
        assertEquals(7, pikachu.getAttackPower());

        assertEquals(39, charmander.getHealth());
        assertEquals(44, squirtle.getHealth());
        assertEquals(52, bulbasaur.getHealth());
        assertEquals(28, pikachu.getHealth());

        charmander.attack(squirtle);
        squirtle.attack(charmander);
        bulbasaur.attack(pikachu);
        pikachu.attack(bulbasaur);

        assertEquals(34, charmander.getHealth());
        assertEquals(38, squirtle.getHealth());
        assertEquals(45, bulbasaur.getHealth());
        assertEquals(24, pikachu.getHealth());
    }

    /**
     * Tests the takeDamage() method for class Pokemon using the getHealth() method. Checks the health after multiple
     *     instances of damage to ensure it returns the correct information
     *
     * @custom.require charmander.getHealth() == 39
     * @custom.require squirtle.getHealth() == 44
     * @custom.require bulbasaur.getHealth() == 52
     * @custom.require pikachu.getHealth() == 28
     * @custom.ensure charmander.getHealth() == 0
     * @custom.ensure squirtle.getHealth() == 18
     * @custom.ensure bulbasaur.getHealth() == 0
     * @custom.ensure pikachu.getHealth() == 31
     * 
     * @see Pokemon#getHealth()
     * @see Pokemon#takeDamage(int, Pokemon) 
     */
    @Test
    void takeDamageTester() {
        assertEquals(39, charmander.getHealth());
        assertEquals(44, squirtle.getHealth());
        assertEquals(52, bulbasaur.getHealth());
        assertEquals(28, pikachu.getHealth());

        charmander.takeDamage(30, squirtle);
        squirtle.takeDamage(14, charmander);
        bulbasaur.takeDamage(66, pikachu);
        pikachu.takeDamage(-4, bulbasaur);

        assertEquals(9, charmander.getHealth());
        assertEquals(30, squirtle.getHealth());
        assertEquals(0, bulbasaur.getHealth());
        assertEquals(32, pikachu.getHealth());

        charmander.takeDamage(10, squirtle);
        squirtle.takeDamage(12, charmander);
        bulbasaur.takeDamage(34, pikachu);
        pikachu.takeDamage(1, bulbasaur);

        assertEquals(0, charmander.getHealth());
        assertEquals(18, squirtle.getHealth());
        assertEquals(0, bulbasaur.getHealth());
        assertEquals(31, pikachu.getHealth());
    }

    /**
     * Tests the toString() for class Pokemon to ensure it returns the correct information
     *
     * @custom.require charmander.getName().equals("Flametail")
     * @custom.require squirtle.getName().equals("Hydrolock")
     * @custom.require bulbasaur.getName().equals("Dryas")
     * @custom.require pikachu.getName().equals("Volt")
     * @custom.require charmander.getHealth() == 39
     * @custom.require squirtle.getHealth() == 44
     * @custom.require bulbasaur.getHealth() == 52
     * @custom.require pikachu.getHealth() == 28
     * @custom.require charmander.getAttackPower() == 6
     * @custom.require squirtle.getAttackPower() == 5
     * @custom.require bulbasaur.getAttackPower() == 4
     * @custom.require pikachu.getAttackPower() == 7
     * @custom.require charmander.toString().equals("Pokemon Flametail:\n\thealth: 39\n\tattack: 6")
     * @custom.require squirtle.toString().equals("Pokemon Hydrolock:\n\thealth: 44\n\tattack: 5")
     * @custom.require bulbasaur.toString().equals("Pokemon Dryas:\n\thealth: 52\n\tattack: 4")
     * @custom.require pikachu.toString().equals("Pokemon Volt:\n\thealth: 28\n\tattack: 7")
     *
     * @see Pokemon#toString()
     */
    @Test
    void toStringTester() {
        assertEquals("Pokemon Flametail:\n\t" +
                        "health: 39\n\t" +
                        "attack: 6",
                        charmander.toString());
        assertEquals("Pokemon Hydrolock:\n\t" +
                        "health: 44\n\t" +
                        "attack: 5",
                        squirtle.toString());
        assertEquals("Pokemon Dryas:\n\t" +
                        "health: 52\n\t" +
                        "attack: 4",
                        bulbasaur.toString());
        assertEquals("Pokemon Volt:\n\t" +
                        "health: 28\n\t" +
                        "attack: 7",
                        pikachu.toString());
    }
}