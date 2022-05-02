package com.lakroft.cardsreco;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface MasksLoader {
    public Map<String, BufferedImage> loadNameMasks() throws Exception;
    public Map<String, BufferedImage> loadSuitMasks() throws Exception;
}
