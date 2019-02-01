package com.example.demo;


import com.example.demo.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.demo.FileUtil;
import com.example.demo.ImgErToFileUtil;
import sun.misc.BASE64Decoder;

@RestController
@RequestMapping("/uploadpic")
public class UploadController {


    public ResponseEntity<String> uploadpicAjax(
            @RequestParam(value = "file") MultipartFile file,
            HttpServletRequest request) {
        String productPicSubPath = "itemImage";
        String file1Name =  file.getOriginalFilename();
        System.out.println("-------------------------file1Name:" + file1Name);

        Map map = new HashMap(16);
        // 获得文件
        if (!file.isEmpty()) {
            String extend = "."
                    + file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf('.') + 1);
            String fileOrigName = !"".equals(file1Name) ? file1Name + extend : file.getOriginalFilename();
            String fileSystemName = String.valueOf(System.currentTimeMillis())
                    + extend;
            String filePath = "/proj/image"
                    + "/upload/" +productPicSubPath+"/"+ fileSystemName;// 文件保存路径
            String dataOldBasePath = "/upload/" +productPicSubPath+"/"+ fileSystemName;//数据库保存路径


            //创建应用文件夹
            File appFolder = new File( "/proj/image/upload/" +productPicSubPath);
            if(!appFolder.exists()){
                appFolder.mkdirs();
            }

            //创建大图片路径
            String newFloderPath = "/proj/image/upload/"+"bigImg/";
            try {
                FileUtil.createFolder(newFloderPath);
//                logger.info("创建文件夹" + newFloderPath + "成功!");
            } catch (Exception e) {
//                logger.info("创建文件夹失败:" + e.toString());
            }
            //图片裁剪后图片的新路径
            String newPath = newFloderPath +fileSystemName;
            String dataBasePath = "/upload/bigImg/"+fileSystemName;
            try {
                // 转存文件
                file.transferTo(new File(filePath));
//                String newBigPic = ImgUtil.compressDengbiliPic(filePath, newPath, 600, 800, true);
//                boolean flag = newBigPic.contains("bigImg");
//                if (flag) {
//                    map.put("picPath", dataBasePath);
//                } else {
//                    map.put("picPath", dataOldBasePath);
//                }

                // 正常返回
                map.put("picPath", filePath);
                map.put("success", true);
                map.put("retmsg", "");
            } catch (Exception e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
                map.put("success", false);
                map.put("retmsg", "文件上传失败！");
                map.put("picPath", "");
            }
        } else {
            map.put("success", false);
            map.put("retmsg", "文件上传失败！");
            map.put("picPath", "");
        }
        return ResponseUtil.getResponseEntity(map);
    }


    /**
     *     * @param imgStr 二进制流转换的字符串
     *      * @param imgPath 图片的保存路径
     *      * @param imgName 图片的名称
     * @param imgBase64
     * @return
     */
    private String imgStr;
    @PostMapping(value = "/uploadpic.do")
//    public int uploadImg(String imgBase64){
//        String imgPath = "D:/images";
//        String imgName = "aaa";
//        return ImgErToFileUtil.saveToImgByStr(imgBase64,imgPath,imgName);
//    }


    public Map<String,String> uploadImg(String imgBase64) {
     Map<String,String> map =new HashMap<>();
        //对字符串解码进行Base64解码并生成图片
        if(imgStr == null){//判断图片数据是否为空
            map.put("state","false");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        //Base解码
        String recordString = imgBase64;
        try{
            String fileImg = recordString.substring(recordString.indexOf(",")+1);
            byte[] b = decoder.decodeBuffer(fileImg);
            for(int i=0;i<b.length;++i){
                if(b[i] < 0){
                    b[i] += 256;
                }
            }
            String files = UUID.randomUUID().toString().replace("-", "");
            Date date = new Date();
            //           String path = "/proj/image";
			String path = "D:/images";
            String filePath = new SimpleDateFormat("yyyyMMdd/").format(date);
            File f = new File(path+filePath);
            if(!f.exists()){
                f.mkdirs();
            }
            String imgFilePath = path + filePath + files +".jpg";
            String filePathImg = filePath + files+".jpg";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            map.put("imgFilePath",imgFilePath);
            map.put("filePathImg",filePathImg);
//            context.setDataValue("filePath",filePathImg);
//            String redString = (String)  context.getDataValue("filePath");
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
