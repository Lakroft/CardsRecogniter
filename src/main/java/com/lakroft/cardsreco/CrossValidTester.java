package com.lakroft.cardsreco;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrossValidTester {
    public static void main(String[] args) throws Exception {
        test();
    }

    private static void test() throws Exception {
        Set<String> files = listFiles();

        long loss = 0;
        ResourceMasksLoader resourceMasksLoader = new ResourceMasksLoader();

        for (String filePath : files) {
            File imageFile = new File("imgs_marked" + File.separator + filePath + ".png");
            CardChecker checker = new CardChecker(ImageIO.read(imageFile), resourceMasksLoader);

            String cards = checker.getCards();
            if (!cards.equals(filePath)) {
                loss++;
            }
        }
        System.out.println(loss/(double) files.size());
    }

    private static Set<String> listFiles() {
        return Stream.of(new File("imgs_marked").listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(s -> s.endsWith(".png"))
                .map(s -> s.substring(0, s.lastIndexOf('.')))
                .collect(Collectors.toSet());
    }

    // Could be issues with cards
    //position-name
    //3-3
    //3-4
    //3-J
    //4-2
    //4-3
    //4-4
    //4-6
    //4-7
    //4-9
    //4-10
    //4-A
    //4-J
}
