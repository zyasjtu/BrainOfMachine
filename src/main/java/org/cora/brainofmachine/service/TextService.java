package org.cora.brainofmachine.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.cora.brainofmachine.constant.FaceApiConstants;
import org.cora.brainofmachine.constant.ResponseJson;
import org.cora.brainofmachine.util.FaceApiUtils;
import org.cora.brainofmachine.util.FileUtils;
import org.cora.brainofmachine.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Colin
 * @date 2018/10/10
 */

@Service
public class TextService {

    private static final Logger LOGGER = Logger.getLogger(TextService.class);

    public JSONObject ocrText(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/textOcr.jpg", request);
            File f = new File(fullPath);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.TEXT_OCR_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);

            ImageUtils.write(bufferedImage, fullPath);

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("ocr text exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }

    public JSONObject ocrBankCard(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/bankCardOcr.jpg", request);
            File f = new File(fullPath);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.BANK_CARD_OCR_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);
            JSONArray bankCards = responseJo.getJSONArray(FaceApiConstants.BANK_CARDS);
            for (Integer i = 0; i < bankCards.size(); i++) {
                String number = bankCards.getJSONObject(i).getString(FaceApiConstants.NUMBER);
                String bank = bankCards.getJSONObject(i).getString(FaceApiConstants.BANK);
                JSONArray organization = bankCards.getJSONObject(i).getJSONArray(FaceApiConstants.ORGANIZATION);
                Integer x1 = bankCards.getJSONObject(i).getJSONObject(FaceApiConstants.BOUND).getJSONObject(FaceApiConstants.LEFT_TOP).getInteger(FaceApiConstants.X);
                Integer y1 = bankCards.getJSONObject(i).getJSONObject(FaceApiConstants.BOUND).getJSONObject(FaceApiConstants.LEFT_TOP).getInteger(FaceApiConstants.Y);
                graphics.drawString(bank, x1 + 5, y1 + 20);
                Integer x2 = bankCards.getJSONObject(i).getJSONObject(FaceApiConstants.BOUND).getJSONObject(FaceApiConstants.LEFT_BOTTOM).getInteger(FaceApiConstants.X);
                Integer y2 = bankCards.getJSONObject(i).getJSONObject(FaceApiConstants.BOUND).getJSONObject(FaceApiConstants.LEFT_BOTTOM).getInteger(FaceApiConstants.Y);
                graphics.drawString(StringUtils.join(organization, ","), x2 + 5, y2 - 5);
                graphics.drawString(number, (x1 + x2) / 2 + 5, (y1 + y2) / 2 + 5);
            }

            ImageUtils.write(bufferedImage, fullPath);
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("ocr bank card exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }

    public JSONObject ocrIdCard(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/idCardOcr.jpg", request);
            File f = new File(fullPath);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.ID_CARD_OCR_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);
            JSONArray idCards = responseJo.getJSONArray(FaceApiConstants.CARDS);
            for (Integer i = 0; i < idCards.size(); i++) {
                String birthday = idCards.getJSONObject(i).getString(FaceApiConstants.BIRTHDAY);
                String address = idCards.getJSONObject(i).getString(FaceApiConstants.ADDRESS);
                String gender = idCards.getJSONObject(i).getString(FaceApiConstants.GENDER);
                String race = idCards.getJSONObject(i).getString(FaceApiConstants.RACE);
                String name = idCards.getJSONObject(i).getString(FaceApiConstants.NAME);
                String number = idCards.getJSONObject(i).getString(FaceApiConstants.ID_CARD_NUMBER);
                graphics.drawString(StringUtils.join(Arrays.asList(name, gender, race, birthday), " "), 5, 20);
                graphics.drawString(number, 5, bufferedImage.getHeight() - 5);
                graphics.drawString(address, 5, bufferedImage.getHeight() / 2 + 5);
            }

            ImageUtils.write(bufferedImage, fullPath);
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("ocr id card exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }
}
