package com.fastdev.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.fastdev.ftp.config.FtpProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

/**
 * @author zhouxi
 * @className FtpClientTemplate
 * @date 2022/8/6 10:47
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtpClientTemplate {

    private FtpProperties ftpProperties;

    public static FtpClientTemplate getClient(FtpProperties ftpProperties) {
        return new FtpClientTemplate(ftpProperties);
    }


    // ---------------------------------------------------------------------
    // private method
    // ---------------------------------------------------------------------

    /**
     * 返回一个FTPClient实例
     *
     * @throws Exception
     */
    private FTPClient getFTPClient() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding(ftpProperties.getEncoding());
        // 连接到ftp服务器
        Assert.isTrue(connect(ftpClient), "ftp server connect fail.");
        // 设置为passive模式
        if (ftpProperties.isPassiveMode()) {
            ftpClient.enterLocalPassiveMode();
        }
        // 设置文件传输类型
        setFileType(ftpClient);
        try {
            ftpClient.makeDirectory(ftpProperties.getRootPath());
            ftpClient.changeWorkingDirectory(ftpProperties.getRootPath());
            ftpClient.setSoTimeout(ftpProperties.getClientTimeout());
        } catch (SocketException e) {
            throw new Exception("Set timeout error.", e);
        }
        return ftpClient;
    }

    /**
     * 设置文件传输类型
     *
     * @throws Exception
     * @throws IOException
     */
    private void setFileType(FTPClient ftpClient) throws Exception {
        try {
            if (ftpProperties.isBinaryTransfer()) {
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            } else {
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
            }
        } catch (IOException e) {
            throw new Exception("Could not to set file type.", e);
        }
    }

    /**
     * 连接到ftp服务器
     *
     * @param ftpClient
     * @return 连接成功返回true，否则返回false
     * @throws Exception
     */
    public boolean connect(FTPClient ftpClient) throws Exception {
        try {
            ftpClient.connect(ftpProperties.getHost(), ftpProperties.getPort());

            // 连接后检测返回码来校验连接是否成功
            int reply = ftpClient.getReplyCode();

            if (FTPReply.isPositiveCompletion(reply)) {
                // 登陆到ftp服务器
                if (ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword())) {
                    setFileType(ftpClient);
                    return true;
                } else {
                    ftpClient.disconnect();
                    throw new Exception("Login username or password error.");
                }
            } else {
                ftpClient.disconnect();
                throw new Exception("FTP server refused connection.");
            }
        } catch (IOException e) {
            if (ftpClient.isConnected()) {
                try {
                    // 断开连接
                    ftpClient.disconnect();
                } catch (IOException e1) {
                    throw new Exception("Could not disconnect from server.", e);
                }
            }
            throw new Exception("Could not connect to server.", e);
        }
    }

    /**
     * 断开ftp连接
     *
     * @throws Exception
     */
    private void disconnect(FTPClient ftpClient) throws Exception {
        try {
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new Exception("Could not disconnect from server.", e);
        }
    }

    // ---------------------------------------------------------------------
    // public method
    // ---------------------------------------------------------------------

    /**
     * 上传一个本地文件到远程指定文件
     *
     * @param serverFile 服务器端文件名(包括完整路径)
     * @param localFile  本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws Exception
     */
    public boolean put(String serverFile, String localFile) throws Exception {
        return put(serverFile, localFile, false);
    }

    /**
     * 上传一个本地文件到远程指定文件
     *
     * @param serverFile 服务器端文件名(包括完整路径)
     * @param localFile  本地文件名(包括完整路径)
     * @param delFile    成功后是否删除文件
     * @return 成功时，返回true，失败返回false
     * @throws Exception
     */
    public boolean put(String serverFile, String localFile, boolean delFile) throws Exception {
        FTPClient ftpClient = null;
        InputStream input = null;
        try {
            ftpClient = getFTPClient();
            // 处理传输
            serverFile = getFtpServerPath(serverFile);
            createDirectory(serverFile, ftpClient);
            input = new FileInputStream(localFile);
            boolean result = ftpClient.storeFile(serverFile, input);
            log.debug("put " + localFile);
            input.close();
            if (delFile) {
                FileUtil.del(localFile);
                log.debug("delete " + localFile);
            }
            return result;
        } catch (FileNotFoundException e) {
            throw new Exception("local file not found.", e);
        } catch (IOException e) {
            throw new Exception("Could not put file to server.", e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
                throw new Exception("Couldn't close FileInputStream.", e);
            }
            if (ftpClient != null) {
                // 断开连接
                disconnect(ftpClient);
            }
        }
    }

    /**
     * 上传一个本地文件到远程指定文件
     *
     * @param destPath 服务器端文件名(包括完整路径)
     * @param inStream 输入流
     * @return 成功时，返回true，失败返回false
     * @throws Exception
     */
    public boolean put(String destPath, InputStream inStream) throws Exception {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            // 处理传输
            destPath = getFtpServerPath(destPath);
            createDirectory(destPath, ftpClient);
            return ftpClient.storeFile(destPath, inStream);
        } catch (FileNotFoundException e) {
            throw new Exception("local file not found.", e);
        } catch (IOException e) {
            throw new Exception("Could not put file to server.", e);
        } finally {
            if (ftpClient != null) {
                // 断开连接
                disconnect(ftpClient);
            }
        }
    }

    /**
     * 下载一个远程文件到本地的指定文件
     *
     * @param serverFile 服务器端文件名(包括完整路径)
     * @param localFile  本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws Exception
     */
    public boolean get(String serverFile, String localFile) throws Exception {
        return get(serverFile, localFile, false);
    }

    /**
     * 下载一个远程文件到本地的指定文件
     *
     * @param serverFile 服务器端文件名(包括完整路径)
     * @param localFile  本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws Exception
     */
    public boolean get(String serverFile, String localFile, boolean delFile)
            throws Exception {
        try (OutputStream output = new FileOutputStream(localFile)) {
            return get(serverFile, output, delFile);
        } catch (FileNotFoundException e) {
            throw new Exception("local file not found.", e);
        }
    }

    /**
     * 下载一个远程文件到指定的流 处理完后记得关闭流
     *
     * @param serverFile
     * @param output
     * @return
     * @throws Exception
     */
    public boolean get(String serverFile, OutputStream output) throws Exception {
        return get(serverFile, output, false);
    }

    /**
     * 下载一个远程文件到指定的流 处理完后记得关闭流
     *
     * @param serverFile
     * @param output
     * @param delFile
     * @return
     * @throws Exception
     */
    public boolean get(String serverFile, OutputStream output, boolean delFile) throws Exception {
        serverFile = getFtpServerPath(serverFile);
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            // 处理传输
            ftpClient.retrieveFile(serverFile, output);
            if (delFile) {
                // 删除远程文件
                ftpClient.deleteFile(serverFile);
            }
            return true;
        } catch (IOException e) {
            throw new Exception("Couldn't get file from server.", e);
        } finally {
            if (ftpClient != null) {
                // 断开连接
                disconnect(ftpClient);
            }
        }
    }

    /**
     * 从ftp服务器上删除一个文件
     *
     * @param delFile
     * @return
     * @throws Exception
     */
    public boolean delete(String delFile) throws Exception {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            ftpClient.deleteFile(delFile);
            return true;
        } catch (IOException e) {
            throw new Exception("Couldn't delete file from server.", e);
        } finally {
            if (ftpClient != null) {
                // 断开连接
                disconnect(ftpClient);
            }
        }
    }

    /**
     * 批量删除
     *
     * @param delFiles
     * @return
     * @throws Exception
     */
    public boolean delete(String[] delFiles) throws Exception {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            for (String s : delFiles) {
                ftpClient.deleteFile(s);
            }
            return true;
        } catch (IOException e) {
            throw new Exception("Couldn't delete file from server.", e);
        } finally {
            if (ftpClient != null) {
                // 断开连接
                disconnect(ftpClient);
            }
        }
    }

    /**
     * 列出远程默认目录下所有的文件
     *
     * @return 远程默认目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
     * @throws Exception
     */
    public String[] listNames() throws Exception {
        return listNames(null);
    }

    /**
     * 列出远程目录下所有的文件
     *
     * @param remotePath 远程目录名
     * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
     * @throws Exception
     */
    public String[] listNames(String remotePath) throws Exception {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            return ftpClient.listNames(remotePath);
        } catch (IOException e) {
            throw new Exception("列出远程目录下所有的文件时出现异常", e);
        } finally {
            if (ftpClient != null) {
                // 断开连接
                disconnect(ftpClient);
            }
        }
    }

    /**
     * 创建远程服务器目录
     *
     * @param remote 远程服务器文件绝对路径
     * @param ftp
     * @return 目录创建是否成功
     * @throws IOException
     */
    public boolean createDirectory(String remote, FTPClient ftp) throws IOException {
        boolean success = true;
        String directory = remote.substring(0, remote.lastIndexOf(StrUtil.SLASH) + 1);
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase(StrUtil.SLASH) && !ftp.changeWorkingDirectory(directory)) {
            int start = 0;
            if (directory.startsWith(StrUtil.SLASH)) {
                start = 1;
            }
            int end = directory.indexOf(StrUtil.SLASH, start);
            while (true) {
                String subDirectory = remote.substring(start, end);
                if (!ftp.changeWorkingDirectory(subDirectory)) {
                    if (ftp.makeDirectory(subDirectory)) {
                        ftp.changeWorkingDirectory(subDirectory);
                    } else {
                        log.info("Create folder failed.");
                        success = false;
                        break;
                    }
                }
                start = end + 1;
                end = directory.indexOf(StrUtil.SLASH, start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    /**
     * 获取服务器完整地址
     *
     * @param destPath
     * @return
     */
    public String getFtpServerPath(String destPath) {
        if (destPath.startsWith(ftpProperties.getRootPath())) {
            return destPath;
        }
        return ftpProperties.getRootPath().concat(destPath);
    }
}
