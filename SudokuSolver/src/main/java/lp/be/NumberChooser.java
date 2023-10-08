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
     * @param rowNumber single row from sudoku
     * @return boolean if row is complete
     */
    public boolean processRow(int rowNumber) {
        List<Field> row = getFieldList().get(rowNumber);
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
        return list.size() == row.size();
    }

    public boolean processColumn(int column) {
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
        return list.size() == getFieldList().size();
    }

    /**
     * number represents order of squares from left to right and from first line to last one.
     * for example for sudoku 9x9 its:
     * 0 1 2
     * 3 4 5
     * 6 7 8
     *
     * @param numberOfSquare specific square
     * @return if this process is complete
     */
    public boolean processSquare(int numberOfSquare) {
        final List<String> list = new ArrayList<>();
        int rowsAndColumnsInSquare = (int) Math.sqrt(getFieldList().size());

        int startNumberOfList = (numberOfSquare / rowsAndColumnsInSquare) * rowsAndColumnsInSquare;
        int startFromColumn = numberOfSquare - (numberOfSquare / rowsAndColumnsInSquare) * rowsAndColumnsInSquare;
        for (int i = startNumberOfList; i < startNumberOfList + 3; i++) {
            for (int j = startFromColumn; j < startFromColumn + 3; j++) {
                if (getFieldList().get(i).get(j).getResultNumber() != null) {
                    list.add(getFieldList().get(i).get(j).getResultNumber());
                }
            }
        }
        for (int i = startNumberOfList; i < startNumberOfList + 3; i++) {
            for (int j = startFromColumn; j < startFromColumn + 3; j++) {
                if (getFieldList().get(i).get(j).getResultNumber() == null) {
                    for (String number : list) {
                        getFieldList().get(i).get(j).getPossibleNumbers().remove(number);
                    }
                }
            }
        }

        return list.size() == getFieldList().size();
    }

    public void checkResult() {
        getFieldList().forEach(fields -> fields.forEach(field -> {
            if (field.getPossibleNumbers().size() == 1) {
                field.setResultNumber(field.getPossibleNumbers().get(0));
                field.getPossibleNumbers().remove(0);
            }
        }));
    }
}
