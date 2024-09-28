/*
 * Enum for buffs
 * 
 * TODO:
 *      - Probably make more. Other things to do right now
 */

public enum Buffs {
    Sharp,          // Next instance of damage done by you does X more damage. Decrement by 1 afterwards.
    Advantage,      // Next action you perform has its delay reduced by 1. Removes itself after use.
    Block,          // Temporary HP for the fight. Doesn't expire
    Charge          // Represents the storing of power. Activates certain card effects. Decrements by 1 every turn
}
