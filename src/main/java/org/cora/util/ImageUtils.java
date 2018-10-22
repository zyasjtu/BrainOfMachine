package org.cora.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Colin
 * @date 2018/10/22
 */

public class ImageUtils {

    public static final String DEFAULT_IMAGE_FORMAT = "JPG";
    private static final Float DEFAULT_STROKE_WIDTH = 2.0f;
    private static final String DEFAULT_FONT_NAME = "黑体";
    private static final Integer DEFAULT_FONT_SIZE = 20;

    private ImageUtils() {
    }

    public static Graphics2D getDefaultGraphics2D(BufferedImage bufferedImage) {
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(DEFAULT_STROKE_WIDTH));
        graphics.setFont(new Font(DEFAULT_FONT_NAME, Font.BOLD, DEFAULT_FONT_SIZE));

        return graphics;
    }

    public static void write(BufferedImage bufferedImage, String fullPath) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(fullPath)) {
            ImageIO.write(bufferedImage, ImageUtils.DEFAULT_IMAGE_FORMAT, stream);
            stream.flush();
        }
    }
}
