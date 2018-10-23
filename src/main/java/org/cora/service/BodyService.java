package org.cora.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Map;

/**
 * @author Colin
 * @date 2018/10/11
 */

@Service
public class BodyService {

    private static final Logger LOGGER = Logger.getLogger(BodyService.class);

    public JSONObject detect(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/bodyDetect.jpg", request);
            File f = new File(fullPath);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.BODY_DETECT_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);
            JSONArray bodyList = responseJo.getJSONArray(FaceApiConstants.HUMAN_BODIES);
            for (Integer i = 0; i < bodyList.size(); i++) {
                JSONObject body = bodyList.getJSONObject(i);
                Double top = body.getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE).getDouble(FaceApiConstants.TOP);
                Double left = body.getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE).getDouble(FaceApiConstants.LEFT);
                Double width = body.getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE).getDouble(FaceApiConstants.WIDTH);
                Double height = body.getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE).getDouble(FaceApiConstants.HEIGHT);
                graphics.drawRect(left.intValue(), top.intValue(), width.intValue(), height.intValue());
            }

            ImageUtils.write(bufferedImage, fullPath);
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("detect body exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }

    public JSONObject analyze(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/bodyFeaturePointsDetect.jpg", request);
            File f = new File(fullPath);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.SKELETON_DETECT_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);
            JSONArray skeletons = responseJo.getJSONArray(FaceApiConstants.SKELETONS);
            for (Integer i = 0; i < skeletons.size(); i++) {
                JSONObject skeleton = skeletons.getJSONObject(i);
                Double top = skeleton.getJSONObject(FaceApiConstants.BODY_RECTANGLE).getDouble(FaceApiConstants.TOP);
                Double left = skeleton.getJSONObject(FaceApiConstants.BODY_RECTANGLE).getDouble(FaceApiConstants.LEFT);

                JSONObject landmark = skeleton.getJSONObject(FaceApiConstants.LANDMARK);
                for (Map.Entry<String, Object> entry : landmark.entrySet()) {
                    JSONObject point = (JSONObject) entry.getValue();
                    Integer x = point.getInteger(FaceApiConstants.X);
                    Integer y = point.getInteger(FaceApiConstants.Y);
                    graphics.drawOval(x + left.intValue(), y + top.intValue(), 5, 5);
                }
            }

            ImageUtils.write(bufferedImage, fullPath);
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("analyze body exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }

    public JSONObject recognize(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/bodyRecognize.jpg", request);
            File f = new File(fullPath);

            HashMap<String, String> map = FaceApiConstants.API_MAP;
            map.put(FaceApiConstants.RETURN_ATTRIBUTES, "gender,upper_body_cloth,lower_body_cloth");
            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.BODY_DETECT_URL, map, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);
            JSONArray bodyList = responseJo.getJSONArray(FaceApiConstants.HUMAN_BODIES);
            for (Integer i = 0; i < bodyList.size(); i++) {
                JSONObject rectangle = bodyList.getJSONObject(i).getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE);
                Integer top = rectangle.getInteger(FaceApiConstants.TOP);
                Integer left = rectangle.getInteger(FaceApiConstants.LEFT);
                Integer width = rectangle.getInteger(FaceApiConstants.WIDTH);
                Integer height = rectangle.getInteger(FaceApiConstants.HEIGHT);

                JSONObject attributes = bodyList.getJSONObject(i).getJSONObject(FaceApiConstants.ATTRIBUTES);
                String gender = attributes.getJSONObject(FaceApiConstants.GENDER).getDouble(FaceApiConstants.MALE).
                        compareTo(attributes.getJSONObject(FaceApiConstants.GENDER).getDouble(FaceApiConstants.FEMALE))
                        > 0 ? "男" : "女";
                String upperColor = attributes.getJSONObject(FaceApiConstants.UPPER_BODY_CLOTH).
                        getString(FaceApiConstants.UPPER_BODY_CLOTH_COLOR).equals(FaceApiConstants.WHITE) ? "浅色" : "深色";
                String lowerColor = attributes.getJSONObject(FaceApiConstants.LOWER_BODY_CLOTH).
                        getString(FaceApiConstants.LOWER_BODY_CLOTH_COLOR).equals(FaceApiConstants.WHITE) ? "浅色" : "深色";

                graphics.drawRect(left, top, width, height);
                graphics.drawString("上衣" + upperColor, left + 5, top + 20);
                graphics.drawString("下装" + lowerColor, left + 5, top + height - 5);
                graphics.drawString(gender, left + 5, top + height / 2 + 5);
            }

            ImageUtils.write(bufferedImage, fullPath);
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("recognize body exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }
}
