package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.StringTokenizer;

public class FileUtil {


        private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

        public FileUtil() {
        }

        /**
         * 创建文件夹,传入参数 如: D:/Tomcat 6.0/webapps/jfycust/UploadImages/20110901/test
         * 将创建至最后一级的test文件夹. 分隔符必须为/
         * @param path
         */
        public static File createFolder(String path) throws Exception {
            File file = null;
            try {
                StringTokenizer st = new StringTokenizer(path, "/");
                String p = "";
                if (path.startsWith("/")) {
                    p = "/";
                }

                while (st.hasMoreElements()) {
                    p += ("".equals(p) ? "" : "/") + String.valueOf(st.nextElement());
                    file = new File(p);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                }
            } catch (Exception e) {
                logger.error("创建文件夹失败[" + path + "]:" + e.getMessage());
                throw e;
            }
            return file;
        }

}
