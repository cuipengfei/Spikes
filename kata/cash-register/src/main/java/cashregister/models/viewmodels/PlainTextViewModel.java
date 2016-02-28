package cashregister.models.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class PlainTextViewModel {
    private static String newLine = System.getProperty("line.separator");
    private List<String> linesSection = new ArrayList<>();

    public void addToLines(String line) {
        linesSection.add(line);
    }

    public String output() {
        StringBuilder stringBuilder = new StringBuilder("***<没钱赚商店>购物清单***");
        for (String line : linesSection) {
            stringBuilder
                    .append(newLine)
                    .append(line);
        }
        stringBuilder
                .append(newLine)
                .append("**********************");
        return stringBuilder.toString();
    }
}
