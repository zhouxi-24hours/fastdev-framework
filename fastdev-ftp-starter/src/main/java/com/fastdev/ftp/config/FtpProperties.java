package com.fastdev.ftp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhouxi
 * @date 2022/8/6 10:11
 */
@Data
@ConfigurationProperties(prefix = "zx.framework.ftp")
public class FtpProperties {

    /**
     * FTP地址
     **/
    private String host = "127.0.0.1";

    /**
     * FTP端口
     **/
    private int port = 21;

    /**
     * FTP用户名
     **/
    private String username = "xxx";

    /**
     * FTP密码
     **/
    private String password = "xxx";

    /**
     * 编码
     **/
    private String encoding = "UTF-8";

    /**
     * passiveMode
     */
    private boolean passiveMode = true;

    /**
     * 超时时间
     */
    private int clientTimeout = 5000;

    /**
     * binaryTransfer
     */
    private boolean binaryTransfer = true;

    /**
     * 目录
     **/
    private String rootPath = "/ftp";
}
