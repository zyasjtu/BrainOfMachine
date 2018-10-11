package org.cora.controller;

import org.cora.service.TextService;
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
 * @date 2018/10/10
 */

@RestController
@RequestMapping(value = "/html", method = RequestMethod.POST)
public class TextController {

    @Autowired
    private TextService textService;

    @RequestMapping(value = "/ocrText.json")
    public String ocrText(@RequestParam("file") MultipartFile file,
                          HttpServletRequest request, HttpServletResponse response) {
        return textService.ocrText(file, request, response).toString();
    }

    @RequestMapping(value = "/ocrBankCard.json")
    public String ocrBankCard(@RequestParam("file") MultipartFile file,
                              HttpServletRequest request, HttpServletResponse response) {
        return textService.ocrBankCard(file, request, response).toString();
    }

    @RequestMapping(value = "/ocrIdCard.json")
    public String ocrIdCard(@RequestParam("file") MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response) {
        return textService.ocrIdCard(file, request, response).toString();
    }
}
