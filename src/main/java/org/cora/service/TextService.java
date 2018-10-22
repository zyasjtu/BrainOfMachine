package org.cora.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.cora.constant.FaceApiConstants;
import org.cora.constant.ResponseJson;
import org.cora.util.FaceApiUtils;
import org.cora.util.FileUtils;
import org.cora.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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

            ImageUtils.write(bufferedImage, fullPath);

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

            ImageUtils.write(bufferedImage, fullPath);

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("ocr id card exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }
}
