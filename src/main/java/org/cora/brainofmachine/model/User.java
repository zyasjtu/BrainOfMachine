package org.cora.brainofmachine.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Colin
 * @date 2018/8/15
 */

@Data
public class User {

    private Long id;

    private String mobile;

    private String password;

    private Date createTime;

    private Date updateTime;

    private String pwd;
}