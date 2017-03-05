
package proj5mazesolver;

/**
 * Main Method for testing MazeSolver Class.
 * @author Laura Josuweit
 */
public class Proj5MazeSolver
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        MazeSolver mz = new MazeSolver();
        System.out.println("Maze Entered:");
        mz.printMaze();
        System.out.println("\n\n");
        mz.solve();
    }  
}
