//package project.solverRefactored;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import project.modelRefactored.Board;
//import project.modelRefactored.BoardItem;
//import project.modelRefactored.Coordinate;
//import project.modelRefactored.Direction;
//
//import java.util.List;
//import java.util.ArrayList;
//
//public class Solver {
//    Board board;
//
//    //Use logger
//    public static Logger logger = LogManager.getLogger(Solver.class);
//
//    public Solver() {
//
//    }
//
//    public List<Move> generateMoves(Board board, Coordinate coordinate) {
//        logger.trace("generate moves");
//        List<Move> legalMoves = new ArrayList<>();
//        BoardItem item = board.getItem(coordinate);
//
//        if (item instanceof Rabbit) {
//
//            //check up jump
//            if (coordinate.row - 1 is )
//            for (int row = coordinate.row - 1; row >= 0; row--) {
//
//            }
//
//
//        }
//        return legalMoves;
//    }
//
//}
