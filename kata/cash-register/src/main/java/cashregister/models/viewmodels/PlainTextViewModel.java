package cashregister.models.viewmodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlainTextViewModel {
    private static final String newLine = System.getProperty("line.separator");
    private static final String sectionSeparator = "----------------------";

    private List<String> linesSection = new ArrayList<>();
    private HashMap<String, List<String>> sections = new HashMap<>();

    public void addToLinesSection(String line) {
        linesSection.add(line);
    }

    public void createSection(String sectionName) {
        sections.put(sectionName, new ArrayList<>());
    }

    public void addToSection(String sectionName, String line) {
        sections.get(sectionName).add(line);
    }

    public String output() {
        StringBuilder stringBuilder = new StringBuilder("***<没钱赚商店>购物清单***");
        outputLinesSection(stringBuilder);
        outputCustomSections(stringBuilder);
        stringBuilder
                .append(newLine)
                .append("**********************");
        return stringBuilder.toString();
    }

    private void outputCustomSections(StringBuilder stringBuilder) {
        for (String sectionName : sections.keySet()) {
            List<String> section = sections.get(sectionName);
            if (section.size() > 0) {
                stringBuilder.append(newLine);
                stringBuilder.append(sectionName);
                for (String line : section) {
                    stringBuilder.append(newLine);
                    stringBuilder.append(line);
                }
                stringBuilder.append(newLine).append(sectionSeparator);
            }
        }
    }

    private void outputLinesSection(StringBuilder stringBuilder) {
        for (String line : linesSection) {
            stringBuilder
                    .append(newLine)
                    .append(line);
        }
        stringBuilder.append(newLine).append(sectionSeparator);
    }
}
