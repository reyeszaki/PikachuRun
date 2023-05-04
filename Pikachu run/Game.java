//Jean Zachary Reyes - 300329378
import java.util.*;
public class Game
{
    private Grid grid;
    private int userRow;
    private int userCol;
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;
    private int health = 5;
    public static final int maxUnits = 3;
    
    public Game()
    {
        grid = new Grid(5, 10);
        userRow = 0;
        userCol = 0;
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, 0), "pikachu.gif");
    }

    public void play()
    {
        while (!isGameOver())
        {
            grid.pause(100);
            handleKeyPress();

            if (msElapsed % 300 == 0)
            {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }
    }

    public void handleKeyPress()
    {
        grid.setImage(new Location(userRow, 0), null);
        int key = grid.checkLastKeyPressed();
        if(key == 38){
            if(userRow != 0){
                userRow --;
                handleCollision(new Location(userRow, userCol));
            }
        }
        if(key == 40){
            if(userRow <= 3){
                userRow ++;
                handleCollision(new Location(userRow, userCol));
            }
        }
        grid.setImage(new Location(userRow, 0), "pikachu.gif");
    }

    public void populateRightEdge()
    {
        Random rnd = new Random();
        /*int units_amt = rnd.nextInt(3);*/
        int obs = (int)(Math.random()*3);
        int col = grid.getNumCols() - 1;
        switch (obs) {
            case 1:
                int row = (int)(Math.random()*5);
                int unit = rnd.nextInt(maxUnits);
                grid.setImage(new Location(row, col), "meowth.gif");
                break;
            case 2:
                row = (int)(Math.random()*5);
                int row2 = (int)(Math.random()*5);
                unit = rnd.nextInt(maxUnits);
                grid.setImage(new Location(row, col), "meowth.gif");
                grid.setImage(new Location(row2, col), "strawberry1.gif");
                break;
            case 3:
                row = (int)(Math.random()*5);
                row2 = (int)(Math.random()*5);
                int row3 = (int)(Math.random()*5);
                unit = rnd.nextInt();
                grid.setImage(new Location(row, col), "meowth.gif");
                grid.setImage(new Location(row2, col), "strawberry1.gif");
                grid.setImage(new Location(row3, col), "strawberry1.gif");
                break;
            default:
                if (true) {
                    break;
                }
        } 
    }
    

    public void scrollLeft()
    {
        Location user = new Location(userRow, 0);
        for(int r = 0; r < grid.getNumRows(); r++){
            for(int c = 0; c < grid.getNumCols(); c++){
                Location current = new Location(r, c);
                
                if (!current.equals(user)) {
                    int temp = c - 1;
                    Location set = new Location(r, temp);
                    if (temp < 0) {
                        grid.setImage(current, null);
                    } else if (!current.equals(new Location(userRow, 1))) {
                        grid.setImage(set, grid.getImage(current));
                    }
                    if (c == 10 - 1) {
                        grid.setImage(current, null);
                    }
                }
                if (current.equals(new Location(userRow, 1))){
                    handleCollision(current);
                }
            }
        }
    }

    public void handleCollision(Location loc)
    {
        String image1 = grid.getImage(loc);
        if(image1 == null){
        }else if(image1 == "strawberry1.gif"){
            grid.setImage((loc), null);
            timesGet++;
        }else if(image1 == "meowth.gif"){
            grid.setImage((loc), null);
            timesAvoid++;
            health--;
        }
    }

    public int getScore()
    {
        return timesGet;
    }

    public void updateTitle()
    {
        grid.setTitle("Pikachu's Strawberry Run      Strawberry: " + getScore() + " Health: " + health);
    }

    public boolean isGameOver()
    {
        if (health == 0){
            System.out.println("=============");
            System.out.println("| Game Over |");
            System.out.println("|===========|");
            System.out.println(" Score: " + getScore());
            System.out.println(" Time:  "+ msElapsed/1000);
            System.out.println("=============");
            return true;
        }
        return false;
    }

    public static void test()
    {
        Game game = new Game();
        game.play();
    }

    public static void main(String[] args)
    {
        test();
    }
}