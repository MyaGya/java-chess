package chess.domain.board;

import static chess.domain.board.ChessBoardInfo.COLUMN_FIRST;
import static chess.domain.board.ChessBoardInfo.COLUMN_LAST;
import static chess.domain.board.ChessBoardInfo.ROW_BLACK_PAWN_LINE;
import static chess.domain.board.ChessBoardInfo.ROW_WHITE_PAWN_LINE;

import chess.domain.state.TeamColor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Position {

    private static final int POSITION_SIZE = 64;
    private static final String ERROR_POSITION_VALUE = "00";
    public static final Position ERROR = new Position(ERROR_POSITION_VALUE);
    private static final int ALPHA_INDEX = 0;
    private static final int NUMBER_INDEX = 1;
    private static final int ZERO = 0;
    private static final int ROW_AND_COLUMN_LENGTH = 2;
    private static final int MOVE_ONE_WEIGHT = 1;
    private static final String ERROR_POSITION = "잘못된 좌표입니다";

    private static Map<String, Position> positions = new LinkedHashMap<>(POSITION_SIZE);

    static {
        for (int row = ChessBoardInfo.ROW_FIRST.getBoardInfo(); row <= ChessBoardInfo.ROW_LAST
            .getBoardInfo(); row++) {
            for (char column = (char) COLUMN_FIRST.getBoardInfo();
                column <= (char) COLUMN_LAST.getBoardInfo();
                column++) {
                String boardPosition = ChessBoard.createPiecePositionName(row, column);
                positions.put(boardPosition, new Position(boardPosition));
            }
        }
    }

    private final char alpha;
    private final int number;

    private Position(String boardPosition) {
        validateLength(boardPosition);
        this.alpha = boardPosition.charAt(ALPHA_INDEX);
        this.number = Character.getNumericValue(boardPosition.charAt(NUMBER_INDEX));
    }

    public static Position valueOf(String value) {
        if (positions.containsKey(value)) {
            return positions.get(value);
        }
        throw new IllegalArgumentException(ERROR_POSITION);
    }

    public boolean isFront(Position value, TeamColor team) {
        if (team == TeamColor.BLACK) {
            return number - value.number > ZERO;
        }
        return number - value.number < ZERO;
    }

    public boolean isCross(Position value) {
        return horizontal(value) || vertical(value);
    }

    public boolean isDiagonal(Position value) {
        return Math.abs(alpha - value.alpha) == Math.abs(number - value.number);
    }

    public boolean isDistanceOne(Position value) {
        return
            (int) Math.sqrt(Math.pow(alpha - value.alpha, 2) + Math.pow(number - value.number, 2))
                == 1;
    }

    private boolean horizontal(Position value) {
        return alpha != value.alpha && number == value.number;
    }

    private boolean vertical(Position value) {
        return alpha == value.alpha && number != value.number;
    }

    private void validateLength(String boardPosition) {
        if (boardPosition.length() != ROW_AND_COLUMN_LENGTH) {
            throw new IllegalArgumentException();
        }
    }

    public Position moveUp() {
        String next = this.alpha + String.valueOf(this.number + MOVE_ONE_WEIGHT);
        if (positions.containsKey(next)) {
            return Position.valueOf(next);
        }
        return ERROR;
    }

    public Position moveDown() {
        String next = this.alpha + String.valueOf(this.number - MOVE_ONE_WEIGHT);
        if (positions.containsKey(next)) {
            return Position.valueOf(next);
        }
        return ERROR;
    }

    public Position moveLeft() {
        String next = (char) (this.alpha - MOVE_ONE_WEIGHT) + String.valueOf(this.number);
        if (positions.containsKey(next)) {
            return Position.valueOf(next);
        }
        return ERROR;
    }

    public Position moveRight() {
        String next = (char) (this.alpha + MOVE_ONE_WEIGHT) + String.valueOf(this.number);
        if (positions.containsKey(next)) {
            return Position.valueOf(next);
        }
        return ERROR;
    }

    public Position moveRightUp() {
        String next =
            (char) (this.alpha + MOVE_ONE_WEIGHT) + String.valueOf(this.number + MOVE_ONE_WEIGHT);
        if (positions.containsKey(next)) {
            return Position.valueOf(next);
        }
        return ERROR;
    }

    public Position moveRightDown() {
        String next =
            (char) (this.alpha + MOVE_ONE_WEIGHT) + String.valueOf(this.number - MOVE_ONE_WEIGHT);
        if (positions.containsKey(next)) {
            return Position.valueOf(next);
        }
        return ERROR;
    }

    public Position moveLeftUp() {
        String next =
            (char) (this.alpha - MOVE_ONE_WEIGHT) + String.valueOf(this.number + MOVE_ONE_WEIGHT);
        if (positions.containsKey(next)) {
            return Position.valueOf(next);
        }
        return ERROR;
    }

    public Position moveLeftDown() {
        String next =
            (char) (this.alpha - MOVE_ONE_WEIGHT) + String.valueOf(this.number - MOVE_ONE_WEIGHT);
        if (positions.containsKey(next)) {
            return Position.valueOf(next);
        }
        return ERROR;
    }


    public boolean isPawnNotMoving(TeamColor color) {
        if (color == TeamColor.BLACK) {
            return this.number == ROW_BLACK_PAWN_LINE.getBoardInfo();
        }
        return this.number == ROW_WHITE_PAWN_LINE.getBoardInfo();
    }

    public Character getColumn() {
        return alpha;
    }


    @Override
    public String toString() {
        return "Position{" +
            "boardPosition='" + alpha + ", " + number + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position position = (Position) o;
        return alpha == position.alpha &&
            number == position.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alpha, number);
    }
}