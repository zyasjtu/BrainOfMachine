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
 * @date 2018/10/8
 */

@Service
public class FaceService {

    private static final Logger LOGGER = Logger.getLogger(FaceService.class);

    /**
     * detect face
     *
     * @param file     file
     * @param request  request
     * @param response response
     * @return json
     */
    public JSONObject detect(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/faceDetect.jpg", request);
            File f = new File(fullPath);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.FACE_DETECT_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);
            JSONArray faces = responseJo.getJSONArray(FaceApiConstants.FACES);
            for (int i = 0; i < faces.size(); i++) {
                JSONObject face = faces.getJSONObject(i);
                Double top = face.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.TOP);
                Double left = face.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.LEFT);
                Double width = face.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.WIDTH);
                Double height = face.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.HEIGHT);
                graphics.drawRect(left.intValue(), top.intValue(), width.intValue(), height.intValue());
            }

            ImageUtils.write(bufferedImage, fullPath);
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("detect face exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }

    /**
     * analyze face
     *
     * @param file     file
     * @param request  request
     * @param response response
     * @return json
     */
    public JSONObject analyze(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/faceOutline.jpg", request);
            File f = new File(fullPath);

            HashMap<String, String> map = new HashMap<>(FaceApiConstants.API_MAP);
            map.put(FaceApiConstants.RETURN_LANDMARK, "2");
            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.FACE_DETECT_URL, map, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);
            JSONArray faces = responseJo.getJSONArray(FaceApiConstants.FACES);
            for (int i = 0; i < faces.size(); i++) {
                JSONObject landmark = faces.getJSONObject(i).getJSONObject(FaceApiConstants.LANDMARK);
                for (Map.Entry<String, Object> entry : landmark.entrySet()) {
                    JSONObject pt = (JSONObject) entry.getValue();
                    graphics.drawRect(pt.getInteger(FaceApiConstants.X), pt.getInteger(FaceApiConstants.Y), 2, 2);
                }
            }

            ImageUtils.write(bufferedImage, fullPath);
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("analyze face exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }

    /**
     * compare
     *
     * @param files    files
     * @param request  request
     * @param response response
     * @return json
     */
    public JSONObject compare(MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath1 = FileUtils.saveFile(files[0], "img/faceVerify1.jpg", request);
            String fullPath2 = FileUtils.saveFile(files[1], "img/faceVerify2.jpg", request);
            File f1 = new File(fullPath1);
            File f2 = new File(fullPath2);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE_1, FaceApiUtils.getBytesFromFile(f1));
            fileMap.put(FaceApiConstants.IMAGE_FILE_2, FaceApiUtils.getBytesFromFile(f2));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.FACE_COMPARE_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            String text = responseJo.getDouble(FaceApiConstants.CONFIDENCE).intValue() + "%";
            JSONObject face1 = responseJo.getJSONArray(FaceApiConstants.FACES_1).getJSONObject(0);
            Double top1 = face1.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.TOP);
            Double left1 = face1.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.LEFT);
            Double width1 = face1.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.WIDTH);
            Double height1 = face1.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.HEIGHT);
            JSONObject face2 = responseJo.getJSONArray(FaceApiConstants.FACES_2).getJSONObject(0);
            Double top2 = face2.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.TOP);
            Double left2 = face2.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.LEFT);
            Double width2 = face2.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.WIDTH);
            Double height2 = face2.getJSONObject(FaceApiConstants.FACE_RECTANGLE).getDouble(FaceApiConstants.HEIGHT);

            BufferedImage bufferedImage1 = ImageIO.read(f1);
            BufferedImage bufferedImage2 = ImageIO.read(f2);
            Graphics2D graphics1 = ImageUtils.getDefaultGraphics2D(bufferedImage1);
            Graphics2D graphics2 = ImageUtils.getDefaultGraphics2D(bufferedImage2);

            graphics1.drawRect(left1.intValue(), top1.intValue(), width1.intValue(), height1.intValue());
            graphics2.drawRect(left2.intValue(), top2.intValue(), width2.intValue(), height2.intValue());
            graphics1.drawString(text, left1.intValue() + 3, top1.intValue() + height1.intValue() - 5);
            graphics2.drawString(text, left2.intValue() + 3, top2.intValue() + height2.intValue() - 5);
            BufferedImage bufferedImage = ImageUtils.merge(bufferedImage1, bufferedImage2, Boolean.TRUE);

            ImageUtils.write(bufferedImage, fullPath1);
            response.sendRedirect("../upload/" + fullPath1.substring(fullPath1.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("compare face exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }

    }
}
