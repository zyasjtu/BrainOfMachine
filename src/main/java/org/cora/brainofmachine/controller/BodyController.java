package org.cora.brainofmachine.controller;

import org.cora.brainofmachine.service.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Colin
 * @date 2018/10/11
 */

@RestController
@RequestMapping(value = "/html", method = RequestMethod.POST)
public class BodyController {

    @Autowired
    private BodyService bodyService;

    /**
     * 人体检测
     *
     * @param file     file
     * @param request  request
     * @param response response
     * @return String
     */
    @RequestMapping(value = "/detectBody.json")
    public String detect(@RequestParam("file") MultipartFile file,
                         HttpServletRequest request, HttpServletResponse response) {
        return bodyService.detect(file, request, response).toString();
    }

    /**
     * 人体关键点定位
     *
     * @param file     file
     * @param request  request
     * @param response response
     * @return String
     */
    @RequestMapping(value = "/analyzeBody.json")
    public String analyze(@RequestParam("file") MultipartFile file,
                          HttpServletRequest request, HttpServletResponse response) {
        return bodyService.analyze(file, request, response).toString();
    }

    /**
     * 人体属性识别
     *
     * @param file     file
     * @param request  request
     * @param response response
     * @return String
     */
    @RequestMapping(value = "recognizeBody.json")
    public String recognize(@RequestParam("file") MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response) {
        return bodyService.recognize(file, request, response).toString();
    }
}
