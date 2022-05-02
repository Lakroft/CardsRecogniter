package com.lakroft.cardsreco;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DirMasksLoader implements MasksLoader {
    @Override
    public Map<String, BufferedImage> loadNameMasks() throws IOException {
        Map<String, BufferedImage> result = new HashMap<>();
        Set<String> cardNames = FilesListTool.listOfPngFilesMeaning("nums2");
        for (String name : cardNames) {
            File imageFile = new File("nums2" + File.separator + name + ".png");
            result.put(name, ImageIO.read(imageFile));
        }
        return result;
    }

    @Override
    public Map<String, BufferedImage> loadSuitMasks() throws IOException {
        Map<String, BufferedImage> result = new HashMap<>();
        Set<String> cardSuits = FilesListTool.listOfPngFilesMeaning("suits2");
        for (String suit : cardSuits) {
            File imageFile = new File("suits2" + File.separator + suit + ".png");
            result.put(suit, ImageIO.read(imageFile));
        }
        return result;
    }
}
