package com.lakroft.cardsreco;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceMasksLoader implements MasksLoader {
    static final List<String> names = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "A", "J", "K", "Q");
    static final List<String> suits = Arrays.asList("c", "d", "h", "s");

    private Map<String, BufferedImage> nameMasks;
    private Map<String, BufferedImage> suitMasks;

    @Override
    public Map<String, BufferedImage> loadNameMasks() throws IOException, URISyntaxException {
        if (nameMasks != null) {
            return nameMasks;
        }
        Map<String, BufferedImage> result = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            for (String name : names) {
                String fullName = i + name;
                InputStream imageStream = getStreamFromResource("nums2/" + fullName + ".png");
                result.put(fullName, ImageIO.read(imageStream));
            }
        }
        nameMasks = result;
        return result;
    }

    @Override
    public Map<String, BufferedImage> loadSuitMasks() throws IOException, URISyntaxException {
        if (suitMasks != null) {
            return suitMasks;
        }
        Map<String, BufferedImage> result = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            for (String suit : suits) {
                String suitName = i + suit;
                InputStream imageStream = getStreamFromResource("suits2/" + suitName + ".png");
                result.put(suitName, ImageIO.read(imageStream));
            }
        }
        suitMasks = result;
        return result;
    }

    private InputStream getStreamFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return resource;
        }
    }
}
