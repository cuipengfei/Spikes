package com.github.Contains_Duplicate;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void emptyArrayNoDups() throws Exception {
        Solution solution = new Solution();
        boolean dups = solution.containsDuplicate(new int[]{});
        assertThat(dups, is(false));
    }

    @Test
    public void noDupsExample() throws Exception {
        Solution solution = new Solution();
        boolean dups = solution.containsDuplicate(new int[]{1, 2, 3});
        assertThat(dups, is(false));
    }

    @Test
    public void dupsExample() throws Exception {
        Solution solution = new Solution();
        boolean dups = solution.containsDuplicate(new int[]{1, 2, 3, 1});
        assertThat(dups, is(true));
    }

    @Test
    public void bigInput() throws Exception {
        int[] ints = readBigArrayOfInts("bigInput217.properties", "bigArray");

        Solution solution = new Solution();
        boolean dups = solution.containsDuplicate(ints);
        assertThat(dups, is(false));
    }

    private int[] readBigArrayOfInts(String pathname, String propertyName) throws IOException {
        Properties p = new Properties();
        p.load(new FileReader(new File(getClass().getClassLoader().getResource(pathname).getFile())));

        String[] bigArray = p.getProperty(propertyName).split(",");
        int[] ints = new int[bigArray.length];
        for (int i = 0; i < bigArray.length; i++) {
            ints[i] = Integer.parseInt(bigArray[i]);
        }

        return ints;
    }
}