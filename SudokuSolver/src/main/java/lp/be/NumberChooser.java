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
     * @param row
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
            if (field.getPossibleNumbers().size() == 1) {
                field.setResultNumber(field.getPossibleNumbers().get(0));
            }
        });
    }
}
