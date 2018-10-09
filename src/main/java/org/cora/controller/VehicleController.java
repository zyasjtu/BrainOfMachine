package org.cora.controller;

import org.cora.service.VehicleService;
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
 * @date 2018/10/9
 */

@RestController
@RequestMapping(value = "/html", method = RequestMethod.POST)
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(value = "/recognizePlate.json")
    public String recognizePlate(@RequestParam("file") MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response) {
        return vehicleService.recognizePlate(file, request, response).toString();
    }
}
