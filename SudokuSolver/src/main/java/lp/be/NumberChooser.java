package lp.be;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class NumberChooser {

    private final List<List<Field>> fieldList = new ArrayList<>();
    private final Map<String, Integer> frequencyMap = new HashMap<>();
    private final List<String> temporaryDataList = new ArrayList<>();

    public NumberChooser(Sudoku sudoku) {
        if (!sudoku.isSudokuLoaded()) {
            return;
        }
        for (int i = 1; i <= sudoku.getData().length; i++) {
            frequencyMap.put(String.valueOf(i), 0);
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
     * <li> 1. get all final numbers from line</li>
     * <li> 2. remove all final numbers from possible numbers of not completed fields</li>
     * <li> 3. check all possible numbers for each number and get distinct value</li>
     *
     * @param rowNumber single row from sudoku
     * @return boolean if row is complete
     */
    public boolean processRow(int rowNumber) {
        temporaryDataList.clear();
        getFieldList().get(rowNumber)
                .stream()
                .filter(field -> field.getResultNumber() != null || field.getPossibleNumbers().size() == 1)
                .forEach(this::getFinalNumbersIfItIsPossible);

        getFieldList().get(rowNumber)
                .stream()
                .filter(field -> field.getPossibleNumbers().size() > 1)
                .forEach(this::processNumbers);

        getFrequencyMap().forEach((s, integer) -> {
            if (integer == 1) {
                Optional<Field> optional = getFieldList().get(rowNumber)
                        .stream()
                        .filter(field -> field.getPossibleNumbers().contains(s))
                        .findFirst();
                optional.ifPresent(field -> setResultNumberToField(field, s));
            }
        });

        resetFrequencyMap();
        return temporaryDataList.size() == getFieldList().size();
    }

    /**
     * <li> 1. get all final numbers from columns (so get all lists and get specific index of this list)</li>
     * <li> 2. remove all final numbers from possible numbers of not completed fields</li>
     * <li> 3. check all possible numbers for each number and get distinct value</li>
     *
     * @param column represented by one index in list of fields
     * @return boolean if row is complete
     */
    public boolean processColumn(int column) {
        temporaryDataList.clear();
        getFieldList().forEach(fields -> getFinalNumbersIfItIsPossible(fields.get(column)));

        getFieldList()
                .stream()
                .filter(fields -> fields.get(column).getPossibleNumbers().size() > 1)
                .forEach(fields -> processNumbers(fields.get(column)));

        getFrequencyMap().forEach((s, integer) -> {
            if (integer == 1) {
                Optional<List<Field>> optional = getFieldList()
                        .stream()
                        .filter(fields -> fields.get(column).getPossibleNumbers().contains(s))
                        .findFirst();
                optional.ifPresent(field -> setResultNumberToField(field.get(column), s));
            }
        });
        resetFrequencyMap();
        return temporaryDataList.size() == getFieldList().size();
    }

    private void setResultNumberToField(Field field, String number) {
        field.setResultNumber(number);
        field.getPossibleNumbers().clear();
    }

    private void getFinalNumbersIfItIsPossible(Field field) {
        if (field.getPossibleNumbers().size() == 1) {
            setResultNumberToField(field, field.getPossibleNumbers().get(0));
        }
        if (field.getResultNumber() != null) {
            temporaryDataList.add(field.getResultNumber());
        }
    }

    /**
     * Remove all used numbers from each possible number list and sign up all remaining numbers into frequency map
     *
     * @param field
     */
    private void processNumbers(Field field) {
        field.getPossibleNumbers().removeAll(temporaryDataList);
        field.getPossibleNumbers().forEach(possibleNumber -> getFrequencyMap().replace(possibleNumber,
                getFrequencyMap().get(possibleNumber) + 1));
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
        int ratioValue = (int) Math.sqrt(getFieldList().size());

        int startNumberOfList = (numberOfSquare / ratioValue) * ratioValue;
        int startFromColumn = (numberOfSquare - ((numberOfSquare / ratioValue) * ratioValue)) * ratioValue;
        for (int i = startNumberOfList; i < startNumberOfList + 3; i++) {
            for (int j = startFromColumn; j < startFromColumn + 3; j++) {
                Field field = getFieldList().get(i).get(j);
                if (field.getPossibleNumbers().size() == 1) {
                    field.setResultNumber(field.getPossibleNumbers().get(0));
                }
                if (field.getResultNumber() != null) {
                    list.add(field.getResultNumber());
                }
            }
        }

        for (int i = startNumberOfList; i < startNumberOfList + 3; i++) {
            for (int j = startFromColumn; j < startFromColumn + 3; j++) {
                Field field = getFieldList().get(i).get(j);
                if (field.getPossibleNumbers().size() > 1) {
                    field.getPossibleNumbers().removeAll(list);
                    field.getPossibleNumbers().forEach(possibleNumber -> getFrequencyMap().replace(possibleNumber,
                            getFrequencyMap().get(possibleNumber) + 1));
                }
            }
        }

        getFrequencyMap().forEach((s, integer) -> {
            if (integer == 1) {
                for (int i = startNumberOfList; i < startNumberOfList + 3; i++) {
                    for (int j = startFromColumn; j < startFromColumn + 3; j++) {
                        Field field = getFieldList().get(i).get(j);
                        if (field.getPossibleNumbers().contains(s)) {
                            field.getPossibleNumbers().clear();
                            field.setResultNumber(s);
                        }
                    }
                }
            }
        });
        resetFrequencyMap();
        return list.size() == getFieldList().size();
    }

    private void resetFrequencyMap() {
        frequencyMap.keySet().forEach(keyNumber -> frequencyMap.replace(keyNumber, 0));
    }
}
