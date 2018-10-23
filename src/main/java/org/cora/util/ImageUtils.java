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

    public static BufferedImage merge(BufferedImage img1, BufferedImage img2, Boolean isHorizontal) {
        int w1 = img1.getWidth();
        int w2 = img2.getWidth();
        int h1 = img1.getHeight();
        int h2 = img2.getHeight();

        // 从图片中读取RGB
        int[] imageArrayOne = new int[w1 * h1];
        int[] imageArrayTwo = new int[w2 * h2];
        // 逐行扫描图像中各个像素的RGB到数组中
        imageArrayOne = img1.getRGB(0, 0, w1, h1, imageArrayOne, 0, w1);
        imageArrayTwo = img2.getRGB(0, 0, w2, h2, imageArrayTwo, 0, w2);

        // 生成新图片
        BufferedImage destImage = null;
        if (isHorizontal) {
            // 水平方向合并
            destImage = new BufferedImage(w1 + w2, h1, BufferedImage.TYPE_INT_RGB);
            // 设置上半部分或左半部分的RGB
            destImage.setRGB(0, 0, w1, h1, imageArrayOne, 0, w1);
            destImage.setRGB(w1, 0, w2, h2, imageArrayTwo, 0, w2);
        } else {
            // 垂直方向合并
            destImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
            // 设置上半部分或左半部分的RGB
            destImage.setRGB(0, 0, w1, h1, imageArrayOne, 0, w1);
            // 设置下半部分的RGB
            destImage.setRGB(0, h1, w2, h2, imageArrayTwo, 0, w2);
        }

        return destImage;
    }
}
