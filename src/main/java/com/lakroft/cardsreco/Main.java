package com.lakroft.cardsreco;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Please input directory path");
            return;
        }

        String dir = args[0];

        Set<String> files = FilesListTool.listOfPngFilesFull(dir);
        ResourceMasksLoader resourceMasksLoader = new ResourceMasksLoader();

        for (String file : files) {
            String fullPath = (!dir.isEmpty()) ? dir + File.separator + file : file;
            File imageFile = new File(fullPath);
            CardChecker checker = new CardChecker(ImageIO.read(imageFile), resourceMasksLoader);

            System.out.println(file + " - " + checker.getCards());
        }
    }
}
