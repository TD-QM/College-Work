public class Strike extends Card{
    
    private int damage;

    public Strike(){
        super("Strike", 2, "Deal 4 damage", STA);
    }

    public void doEffect(Player p, Enemy[] e, int target){
        e.takeDamage();
    }
}
