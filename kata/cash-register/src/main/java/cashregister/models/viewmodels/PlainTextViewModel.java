package cashregister.models.viewmodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cashregister.discounts.Discount.DECIMAL_FORMAT;

public class PlainTextViewModel {
    private static final String newLine = System.getProperty("line.separator");
    private static final String sectionSeparator = "----------------------";

    private List<String> linesSection = new ArrayList<>();
    private HashMap<String, List<String>> sections = new HashMap<>();
    private double originalTotal = 0;
    private double discountedTotal = 0;

    public void addToLinesSection(String line) {
        linesSection.add(line);
    }

    public void addToSection(String sectionName, String line) {
        if (!sections.containsKey(sectionName)) {
            sections.put(sectionName, new ArrayList<>());
        }
        sections.get(sectionName).add(line);
    }

    public void addToOriginalTotal(double delta) {
        originalTotal += delta;
    }

    public void addToDiscountedTotal(double delta) {
        discountedTotal += delta;
    }

    public String output() {
        StringBuilder stringBuilder = new StringBuilder("***<没钱赚商店>购物清单***");
        outputLinesSection(stringBuilder);
        outputCustomSections(stringBuilder);
        outputFinalSummary(stringBuilder);
        stringBuilder
                .append(newLine)
                .append("**********************");
        return stringBuilder.toString();
    }

    private void outputFinalSummary(StringBuilder stringBuilder) {
        stringBuilder
                .append(newLine)
                .append("总计: " + DECIMAL_FORMAT.format(discountedTotal) + "(元)")
                .append(newLine)
                .append("节省：" + DECIMAL_FORMAT.format(originalTotal - discountedTotal) + "(元)");
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
