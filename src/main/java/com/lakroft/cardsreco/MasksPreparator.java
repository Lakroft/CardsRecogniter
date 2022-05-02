package com.lakroft.cardsreco;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MasksPreparator {
    public static void main(String[] args) throws Exception {
        prepairMasks2();
    }

    private static void prepairMasks2() throws Exception {
        List<String> files = listFiles();
        files = files.subList(0, files.size()/2);
        Map<String, BufferedImage> nameMasks = new HashMap<>();
        Map<String, BufferedImage> suitMasks = new HashMap<>();
        for (String file : files) {
            File imageFile = new File("imgs_marked" + File.separator + file);
            CardChecker checker = new CardChecker(ImageIO.read(imageFile), new DirMasksLoader());

            String meaningPart = file.substring(0, file.lastIndexOf('.'));
            int i = 0;
            int pos = 0;
            while (pos < meaningPart.length()) {
                String cardNum = String.valueOf(meaningPart.charAt(pos++));
                if (cardNum.equals("1")) {
                    cardNum = "10";
                    pos++;
                }
                String cardSuit = String.valueOf(meaningPart.charAt(pos++));
                BufferedImage nameImg = CardChecker.getCardName(checker.getMonochromeCardByNum(i));
                BufferedImage suitImg = CardChecker.getCardSuit(checker.getMonochromeCardByNum(i));
                if (!nameMasks.containsKey(i+cardNum)) {
                    nameMasks.put(i+cardNum, nameImg);
                } else {
                    mergeRightToLeft(nameMasks.get(i+cardNum), nameImg);
                }
                if (!suitMasks.containsKey(i+cardSuit)) {
                    suitMasks.put(i+cardSuit, suitImg);
                } else {
                    mergeRightToLeft(suitMasks.get(i+cardSuit), suitImg);
                }
                i++;
            }
        }
        saveImgs("nums2" + File.separator, nameMasks);
        saveImgs("suits2" + File.separator, suitMasks);
    }

    public static void saveImgs(String dir, Map<String, BufferedImage> images) throws IOException {
        for (String key : images.keySet()) {
            File saveFile = new File(dir + key + ".png");
            ImageIO.write(images.get(key), "png", saveFile);
        }
    }

    public static float mergeRightToLeft(BufferedImage left, BufferedImage right) {
        int total = 0;
        int match = 0;
        for (int x = 0; x < left.getWidth(); x++) {
            for (int y = 0; y < left.getHeight(); y++) {
                if (CardChecker.getBlueLine(right.getRGB(x, y)) == 0) {
                    left.setRGB(x, y, 0);
                }
            }
        }
        return (float) match/total;
    }

    private static List<String> listFiles() {
        return Stream.of(new File("imgs_marked").listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(s -> s.endsWith(".png"))
                .collect(Collectors.toList());
    }
}
