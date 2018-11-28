package org.cora.brainofmachine.controller;

import org.cora.brainofmachine.service.FaceService;
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
 * @date 2018/10/8
 */

@RestController
@RequestMapping(value = "/html", method = RequestMethod.POST)
public class FaceController {

    @Autowired
    private FaceService faceService;

    @RequestMapping(value = "/detectFace.json")
    public String detect(@RequestParam("file") MultipartFile file,
                         HttpServletRequest request, HttpServletResponse response) {
        return faceService.detect(file, request, response).toString();
    }

    @RequestMapping(value = "/analyzeFace.json")
    public String analyze(@RequestParam("file") MultipartFile file,
                          HttpServletRequest request, HttpServletResponse response) {
        return faceService.analyze(file, request, response).toString();
    }

    @RequestMapping(value = "/compareFace.json")
    public String compare(@RequestParam("files") MultipartFile[] files,
                          HttpServletRequest request, HttpServletResponse response) {
        return faceService.compare(files, request, response).toString();
    }
}
