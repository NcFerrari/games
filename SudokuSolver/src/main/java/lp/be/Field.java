package lp.be;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Field {

    private final List<String> possibleNumbers = new ArrayList<>();
    private String resultNumber;

    public Field(int value, int sudokuSize) {
        if (value == 0) {
            for (int i = 1; i <= sudokuSize; i++) {
                possibleNumbers.add(String.valueOf(i));
            }
        } else {
            resultNumber = String.valueOf(value);
        }
    }
}
