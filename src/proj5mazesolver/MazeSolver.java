
package proj5mazesolver;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * MazeSolver Class:
 * A MazeSolver object prompts for a width and height of a character maze and
 * then for all characters in said maze, separated by whitespace.
 * Prints out every solution to the maze, with the path taken denoted by p's.
 * Solves character mazes 
 * with one exit and one starting position, denoted by 'e' and 'b'
 * respectively, and paths denoted by 0's. (Mazes that do not conform to 
 * these standards have no guarantee of being properly solved.)
 * 
 * EXAMPLE MAZE: 5 X 5
 *    1 1 1 1 1 
 *    b 0 1 0 1 
 *    1 0 1 0 e 
 *    0 0 0 0 1 
 *    1 1 1 1 1
 * @author Laura Josuweit
 */
public class MazeSolver
{
    private int cols;
    private int rows;
    private char[][] maze;
    //starting position co-ordinates
    private int bRow;
    private int bCol;

    /**
     * CONSTRUCTOR
     * Will ask user to enter two integers for the size of the maze,
     * then prompt them to enter each character of the maze separated by
     * whitespace. Fills the maze attribute with the maze characters provided,
     * and finds the starting position, setting bRow and bCol to the co-ords
     * of the 'b' in the maze.
     * If dimensions are not entered properly, defaults to 5x5 maze.
     */
    public MazeSolver() 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the height and width of your maze, "
                + "as integers, separated by whitespace:");
        try
        {
           this.rows = scan.nextInt();
           this.cols = scan.nextInt(); 
        }
        catch(InputMismatchException ex)
        {
            System.out.println("Error: maze width and height not properly "
                    + "entered. Inititalizing maze as 5x5.");
            scan.next();//clears out the bad input
            this.rows = 10;
            this.cols = 10;
        }
        
        this.maze = new char[rows][cols];
        //prompt for maze
        System.out.println("Enter a character for each section of the maze,"
                + " separated by a whitespace character:");
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {//if the user does not put white space between their character
                //entries, it will only take the first character as input.
                //In other words, when the user gives you a string instead 
                //of a character, we only use the first character in that 
                //string as our input.
                String c = scan.next();
                maze[i][j] = c.charAt(0);
            }
        }
        //find our starting position
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if(maze[i][j] == 'b')
                {
                    bRow = i;
                    bCol = j;
                }
            }
        }    
    }
    /**
     * Prints the maze.
     */
    public void printMaze()
    {
        System.out.println("");
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
                System.out.print(maze[i][j]+" ");
            System.out.println("");
        }
    }

    /**
     * Starts solving the maze;
     * Simply calls the recursive solveFromPosition function below with the
     * co-ords of the maze's beginning.
     * (Also replaces the 'b' where we started, since it might be taken over 
     * by path in the recursive solveFromPosition method.)
     *
     * This way, the user can ask for the maze to be solved without needing
     * to know where to start.
     */
    public void solve()
    {
        solveFromPosition(bRow, bCol);
        maze[bRow][bCol] = 'b';
    }
    /**
     * Recursive method with bactracking that finds and prints out each
     * path that leads to the end of the maze. Sets a P where it has walked.
     * Might overwrite the b marking the path beginning; b is replaced in
     * the solve() method that calls this one, just in case.
     * (Never overrides the 'e' of the maze's exit.)
     * @param row row co-ordinate of our current maze position.
     * @param col column co-ordinate of our current maze position.
     */
    private void solveFromPosition(int row, int col)
    {
        //check maze bounds to make sure we don't try to go in a direction
        //outside the bounds of the maze array
        boolean canGoUp = false;
        boolean canGoDown = false;
        boolean canGoLeft = false;
        boolean canGoRight = false;
        
        if(row != rows - 1)
            canGoDown = true;
        if(row != 0)
            canGoUp = true;
        if(col != cols - 1)
            canGoRight = true;
        if(col != 0)
            canGoLeft = true;
        
        //base case: check if we are adjacent to the exit.
        if((canGoUp && maze[row - 1][col] == 'e') ||
                (canGoDown && maze[row + 1][col] == 'e') ||
                (canGoLeft && maze[row][col - 1] == 'e') ||
                (canGoRight && maze[row][col + 1] == 'e'))
        {
            //we found our way out wooooo
            maze[row][col] = 'P';
            //replace 'b' of path start that got overridden by path.
            maze[bRow][bCol] = 'b';
            printMaze();
            maze[row][col] = '0';
        }
        else
        {
            if(canGoUp && maze[row - 1][col] == '0')
            {//p for path
                maze[row][col] = 'P';
                solveFromPosition(row - 1, col);
                maze[row][col] = '0';
            }
            if(canGoDown && maze[row + 1][col] == '0')
            {
                maze[row][col] = 'P';
                solveFromPosition(row + 1, col);
                maze[row][col] = '0';
            }
            if(canGoLeft && maze[row][col - 1] == '0')
            {
                maze[row][col] = 'P';
                solveFromPosition(row, col - 1);
                maze[row][col] = '0';
            }
            if(canGoRight && maze[row][col + 1] == '0')
            {
                maze[row][col] = 'P';
                solveFromPosition(row, col + 1);
                maze[row][col] = '0';
            }     
        } 
    }
}
