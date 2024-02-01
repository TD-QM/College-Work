
public final class StdDraw  
{
    //Named Constants for formatting instructions that control terminal
    private final static String ANSI_CLEAR = "\033[H\033[2J"; //Clear Terminal
    
    //Class variables
    private static String[][] gameMap;  //Current game map
    private static int width;           //game map's width
    private static int height;          //game map's height
    
    //initialize the game map
    public static void initGameMap(int x, int y)
    {
        StdDraw.width = x;
        StdDraw.height = y;
        StdDraw.gameMap = new String[x][y];
    }
    
    //load a tile within the game map
    public static void loadTileImage(int x, int y, String tileImage)
    {
        StdDraw.gameMap[x][y] = tileImage;
    }
    
    //draw the game map
    public static void draw()
    {
        String textImage = "";
        //Draws the contents of the array into console
        for (int y=0; y<StdDraw.height; y++)
        {
            for (int x=0; x<StdDraw.width; x++)
            {
                textImage += StdDraw.gameMap[x][y];
            }//end inner for-loop
            textImage += "\n";
        }//end outer for-loop
        System.out.print(textImage);
    }
    
    //Clears drawing area
    public static void clear()
    {
        //Escape seqeunce command to reset cursor to top-left position
        System.out.print(ANSI_CLEAR);
    }
    
    //#TODO delete this, depricated
    public static void pause(int milleseconds)
    {
        try
        {
            Thread.sleep(milleseconds);
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void drawText(String text)
    {
        System.out.print(text);
    }

   
}
