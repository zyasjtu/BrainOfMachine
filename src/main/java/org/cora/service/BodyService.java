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
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(new File(fullPath)));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.BODY_DETECT_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            JSONArray bodyList = responseJo.getJSONArray(FaceApiConstants.HUMAN_BODIES);
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat img = Highgui.imread(fullPath);
            for (Integer i = 0; i < bodyList.size(); i++) {
                JSONObject body = bodyList.getJSONObject(i);
                Double top = body.getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE).getDouble(FaceApiConstants.TOP);
                Double left = body.getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE).getDouble(FaceApiConstants.LEFT);
                Double width = body.getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE).getDouble(FaceApiConstants.WIDTH);
                Double height = body.getJSONObject(FaceApiConstants.HUMAN_BODY_RECTANGLE).getDouble(FaceApiConstants.HEIGHT);
                Core.rectangle(img, new Point(left, top), new Point(left + width, top + height), new Scalar(0, 0, 255), 2);
            }
            Highgui.imwrite(fullPath, img);

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf('/') + 1));
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("detect body exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }

    public JSONObject analyze(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/bodyFeaturePointsDetect.jpg", request);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(new File(fullPath)));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.SKELETON_DETECT_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            JSONArray skeletons = responseJo.getJSONArray(FaceApiConstants.SKELETONS);
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat img = Highgui.imread(fullPath);
            for (Integer i = 0; i < skeletons.size(); i++) {
                JSONObject skeleton = skeletons.getJSONObject(i);
                Double top = skeleton.getJSONObject(FaceApiConstants.BODY_RECTANGLE).getDouble(FaceApiConstants.TOP);
                Double left = skeleton.getJSONObject(FaceApiConstants.BODY_RECTANGLE).getDouble(FaceApiConstants.LEFT);

                JSONObject landmark = skeleton.getJSONObject(FaceApiConstants.LANDMARK);
                for (Map.Entry<String, Object> entry : landmark.entrySet()) {
                    JSONObject point = (JSONObject) entry.getValue();
                    Integer x = point.getInteger(FaceApiConstants.X);
                    Integer y = point.getInteger(FaceApiConstants.Y);
                    Core.circle(img, new Point(x + left, y + top), 5, new Scalar(0, 0, 255), 2);
                }
            }
            Highgui.imwrite(fullPath, img);

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf('/') + 1));
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("analyze body exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }

    public JSONObject recognize(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/bodyRecognize.jpg", request);

            HashMap<String, String> map = FaceApiConstants.API_MAP;
            map.put(FaceApiConstants.RETURN_ATTRIBUTES, "gender,upper_body_cloth,lower_body_cloth");
            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(new File(fullPath)));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.BODY_DETECT_URL, map, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat img = Highgui.imread(fullPath);
            Highgui.imwrite(fullPath, img);

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("recognize body exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }
}
