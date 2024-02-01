/**
    @author Christopher Summa
    @version 1.0

    parse custom tags with the following command:

    javadoc -tag custom.require:cm:"Precondition:" -tag custom.ensure:cm:"Postcondition:" -Xdoclint:none  *.java

    Specify an output directory for the generated files by adding the `-d ./doc` option of the above command.

    Link to the java API by adding the `-link https://docs.oracle.com/javase/8/docs/api/` option as well.
*/
public class Pokemon{

    private int myHealth;  /** @invariant myHealth >=0 */
    private int myAttackPower; /** @invariant myAttackPower >= 0 */
    private String myName; /** @invariant myName != "" */
    
    /**
     *  
     * 
     *  @param name The name of the Pokemon
     *  @param initialHealth The initial health of the Pokemon
     *  @param initialAttackPower The initial attack power of the Pokemon
     *  @since Sunday, Sep 9, 2018
     * 
     *  @custom.require name != ""
     *  @custom.require initialHealth > 0
     *  @custom.require initialAttackPower > 0
     *  @custom.ensure  getName() != "" && getName() == name
     *  @custom.ensure  getHealth() > 0 && getHealth() == initialHealth
     *  @custom.ensure  getAttackPower() > 0 && getAttackPower() == initialAttackPower
     */
    public Pokemon(String name, int initialHealth, int initialAttackPower) {
        myName = name;
        myAttackPower = initialAttackPower;
        myHealth = initialHealth;
    }

    /**
     * Returns the current health of the Pokemon
     * 
     * @custom.ensure getHealth() == this.myHealth
     * @custom.ensure getHealth() >= 0
     * 
     * @return the current health of the Pokemon
     */
    public int getHealth() {
        return myHealth;
    }

    /**
     * Returns the current attack power of the Pokemon
     *  
     * @return the current attack power of the Pokemon
     * 
     * @custom.ensure getAttackPower() == this.myattackPower
     * @custom.ensure getAttackPower() >= 0
     */
    public int getAttackPower() {
        return myAttackPower;
    }

    /**
     * Returns the current name of the Pokemon
     * 
     * @return the current attack power of the Pokemon
     * 
     * @custom.ensure getName().equals(this.name)
     * @custom.ensure !getName().equals("")
     */
    public String getName() {
        return myName;
    }

    /**
     * Checks to see if the Pokemon is dead or not
     * 
     * @return true if health is zero, false otherwise
     * 
     * @custom.require getHealth() >= 0
     */
    public boolean isDead() {
        if (myHealth == 0)
            return true;
        return false;
    }

    /**
     * Reduces the health of another Pokemon object
     * 
     * @param other the Pokemon object recieving damage
     * 
     * @custom.require getAttackPower() >= 0
     * @custom.ensure this.other.getHealth() == old.other.getHealth() - getAttackPower()
     * @custom.ensure this.other.getHealth() >= 0
     */
    public void attack(Pokemon other) {
        other.takeDamage(this.myAttackPower, this);
    }

    /**
     * Reduces the health of this Pokemon object
     * 
     * @param amountOfDamage    the amount of incoming damage
     * @param attacker          the Pokemon object that is dealing damage
     * 
     * @custom.require attacker.getAttackPower >= 0
     * @custom.require amountOfDamage >= 0
     * @custom.ensure amtOfDamageActuallyDealt == getHealth() || amtOfDamageActuallyDealt == amountOfDamage
     * @custom.ensure this.getHealth() == old.getHealth() - amtOfDamageActuallyDealt
     */
    public void takeDamage(int amountOfDamage, Pokemon attacker) {
        int amtOfDamageActuallyDealt;
        if (amountOfDamage > myHealth) {
            amtOfDamageActuallyDealt = myHealth;
            myHealth = 0;
        }
        else {
            myHealth -= amountOfDamage;
            amtOfDamageActuallyDealt = amountOfDamage;
        }
    }

    /**
     * Returns a String representation of the current object
     * 
     * @return a String representation of the current object
     * 
     * @custom.require !getName().equals("")
     * @custom.require getHealth() >= 0
     * @custom.require getAttackPower() >= 0
     * @custom.ensure !getName().equals("")
     * @custom.ensure getHealth() >= 0
     * @custom.ensure getAttackPower() >= 0
     * @custom.ensure returnval.equals("Pokemon " + getName() + "\n\thealth: " + getHealth() + "/n/tattack: " + getAttackPower())
     */
    public String toString() {
        String returnval = "";
        returnval += "Pokemon " + myName + ":" +
                 "\n\thealth: " + myHealth +
                 "\n\tattack: " + myAttackPower;
        return returnval;
    }

} // end class Pokemon
