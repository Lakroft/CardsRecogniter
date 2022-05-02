package com.lakroft.cardsreco;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesListTool {
    public static Set<String> listOfPngFilesMeaning(String dir) {
        return listOfPngFilesFull(dir).stream()
                .map(s -> s.substring(0, s.lastIndexOf('.')))
                .collect(Collectors.toSet());
    }

    public static Set<String> listOfPngFilesFull(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(s -> s.endsWith(".png"))
                .collect(Collectors.toSet());
    }
}
