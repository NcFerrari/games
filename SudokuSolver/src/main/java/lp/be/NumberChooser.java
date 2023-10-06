package lp.be;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NumberChooser {

    private final List<List<Field>> fieldList = new ArrayList<>();

    public NumberChooser(Sudoku sudoku) {
        if (!sudoku.isSudokuLoaded()) {
            return;
        }
        for (int[] rows : sudoku.getData()) {
            List<Field> list = new ArrayList<>();
            for (int value : rows) {
                list.add(new Field(value, sudoku.getData().length));
            }
            fieldList.add(list);
        }
    }

    /**
     * 1. get all final numbers from line
     * 2. remove all final numbers from possible numbers of not completed fields
     *
     * @param row single row from sudoku
     */
    public void processRow(List<Field> row) {
        final List<String> list = new ArrayList<>();
        row.forEach(field -> {
            if (field.getResultNumber() != null) {
                list.add(field.getResultNumber());
            }
        });
        row.forEach(field -> {
            if (field.getResultNumber() == null) {
                list.forEach(number -> field.getPossibleNumbers().remove(number));
            }
        });
    }

    public void processColumn(int column) {
        final List<String> list = new ArrayList<>();
        getFieldList().forEach(fields -> {
            if (fields.get(column).getResultNumber() != null) {
                list.add(fields.get(column).getResultNumber());
            }
        });
        getFieldList().forEach(fields -> {
            if (fields.get(column).getResultNumber() == null) {
                list.forEach(number -> fields.get(column).getPossibleNumbers().remove(number));
            }
        });
    }

    /**
     * number represents order of squares from left to right and from first line to last one.
     * for example for sudoku 9x9 its:
     * 1 2 3
     * 4 5 6
     * 7 8 9
     *
     * @param numberOfSquare specific square
     */
    public void processSquare(int numberOfSquare) {
        final List<String> list = new ArrayList<>();
        int rowsAndColumnsInSquare = (int) Math.sqrt(getFieldList().size());
        for (int i = 0; i < rowsAndColumnsInSquare; i++) {
            for (int j = 0; j < rowsAndColumnsInSquare; j++) {
                Field field = getFieldList().get(i + ((numberOfSquare - 1) * rowsAndColumnsInSquare)).get(j);
                if (field.getResultNumber() != null) {
                    list.add(field.getResultNumber());
                }
            }
        }

        for (int i = 0; i < rowsAndColumnsInSquare; i++) {
            for (int j = 0; j < rowsAndColumnsInSquare; j++) {
                Field field = getFieldList().get(i + ((numberOfSquare - 1) * rowsAndColumnsInSquare)).get(j);
                if (field.getResultNumber() == null) {
                    list.forEach(number -> field.getPossibleNumbers().remove(number));
                }
            }
        }
    }

    public void checkResult() {
        getFieldList().forEach(fields -> fields.forEach(field -> {
            if (field.getPossibleNumbers().size() == 1) {
                field.setResultNumber(field.getPossibleNumbers().get(0));
            }
        }));
    }
}
