package com.lakroft.cardsreco;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class CardChecker {
    // Шаг между картами - 74 пикселя
    // Размер карты - 54х80
    // Высота верхней части карты - 30, нижней - 50.
    // Начальные координаты 148х589
    private static final int startX = 148;
    private static final int startY = 589;
    private static final int topSideOfCard = 30;
    private static final int bottomSideOfCard = 50;
    private static final int cartW = 54;
    private static final int cardH = topSideOfCard + bottomSideOfCard;
    private static final int cardStep = 72;
    private static final int colorThreshold = 80;

    private final Map<String, BufferedImage> nameMasks;
    private final Map<String, BufferedImage> suitMasks;

    BufferedImage image;

    public CardChecker(BufferedImage image, MasksLoader masksLoader) throws Exception {
        this.image = image;
        nameMasks = masksLoader.loadNameMasks();
        suitMasks = masksLoader.loadSuitMasks();
    }

    public String getCards() throws IOException {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            BufferedImage card = getMonochromeCardByNum(i);
            if (isBlank(card)) {
                break;
            }
            result.append(recName(getCardName(card), String.valueOf(i)));
            result.append(recSuit(getCardSuit(card), String.valueOf(i)));
        }
        return result.toString();
    }

    public String recName(BufferedImage cardTop, String pos) throws IOException {
        float bestMatch = 0;
        String matchName = "";
        for (String name : nameMasks.keySet().stream().filter(k -> k.startsWith(pos)).collect(Collectors.toSet())) {
            float match = compareImages(cardTop, nameMasks.get(name));
            if (match > bestMatch) {
                bestMatch = match;
                matchName = name.substring(1);
            }
        }
//        File saveFile = new File(matchName + ".png");
//        ImageIO.write(cardTop, "png", saveFile);
        return matchName;
    }

    public String recSuit(BufferedImage cardBottom, String pos) throws IOException {
        float bestMatch = 0;
        String matchSuit = "";
        for (String suit : suitMasks.keySet().stream().filter(k -> k.startsWith(pos)).collect(Collectors.toSet())) {
            float match = compareImages(cardBottom, suitMasks.get(suit));
            if (match > bestMatch) {
                bestMatch = match;
                matchSuit = suit.substring(1);
            }
        }
//        File saveFile = new File(matchSuit + ".png");
//        ImageIO.write(cardBottom, "png", saveFile);
        return matchSuit;
    }

    public static BufferedImage getCardName(BufferedImage card) {
        return card.getSubimage(0, 0, cartW, topSideOfCard);
    }

    public static BufferedImage getCardSuit(BufferedImage card) {
        return card.getSubimage(0, topSideOfCard, cartW, bottomSideOfCard);
    }

    public BufferedImage getCardByNum(int num) {
        int step = cardStep * num;
        return image.getSubimage(startX + step,
                startY, cartW, cardH);
    }

    public BufferedImage getMonochromeCardByNum(int num) {
        return toMonochromeAlter(getCardByNum(num));
    }

    public static float compareImages(BufferedImage left, BufferedImage right) {
        int total = 0;
        int match = 0;
        for (int x = 0; x < left.getWidth(); x++) {
            for (int y = 0; y < left.getHeight(); y++) {
                if (getBlueLine(left.getRGB(x, y)) == 0) {
                    total++;
                    if (getBlueLine(right.getRGB(x, y)) == 0) {
                        match++;
                    }
                }
            }
        }
        return (float) match/total;
    }

    public static int getBlueLine(int rgb) {
        return (rgb & 0xff);
    }

    public static boolean isBlank(BufferedImage image) {
        int notBlack = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (getBlueLine(image.getRGB(x, y)) > 0) {
                    notBlack++;
                }
            }
        }
        return (notBlack == 0);
    }

    public static BufferedImage toMonochrome(BufferedImage image) {
        BufferedImage blackAndWhiteImg = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphics = blackAndWhiteImg.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        return blackAndWhiteImg;
    }

    public static BufferedImage toMonochromeAlter(BufferedImage image) {
        BufferedImage blackAndWhiteImg = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (getBlueLine(image.getRGB(x, y)) > colorThreshold) {
                    blackAndWhiteImg.setRGB(x, y, 0xffffff);
                } else {
                    blackAndWhiteImg.setRGB(x, y, 0);
                }
            }
        }

        return blackAndWhiteImg;
    }
}
