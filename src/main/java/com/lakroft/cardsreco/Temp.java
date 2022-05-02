package com.lakroft.cardsreco;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Temp {
    static final List<String> names = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "A", "J", "K", "Q");

    public static void main(String[] args) throws IOException, URISyntaxException {
        checkResLoader();
    }

    public static void checkResLoader() throws IOException, URISyntaxException {
        ResourceMasksLoader loader = new ResourceMasksLoader();
        BufferedImage image = loader.loadNameMasks().get("0K");

        File saveFile = new File("temp.png");
        ImageIO.write(image, "png", saveFile);
    }

    public static void checkMasksPresent() {
        List<String> files = Stream.of(new File("nums2").listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(s -> s.endsWith(".png"))
                .map(s -> s.substring(0, s.lastIndexOf('.')))
                .collect(Collectors.toList());

        for (int i = 0; i < 5; i++) {
            for (String name : names) {
                String fileName = i + name;
                if(!files.contains(fileName)) {
                    System.out.println(fileName);
                }
            }
        }
    }
}
