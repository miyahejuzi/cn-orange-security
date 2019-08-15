package cn.orange.web.controller;

import cn.orange.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author : kz
 * @date : 2019/7/22
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping
    public FileInfo upload(MultipartFile file) {
        System.out.println("file = " + file);
        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        System.out.println("file.getName() = " + file.getName());
        System.out.println("file.getContentType() = " + file.getContentType());
        return new FileInfo();
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try (
                InputStream is = new BufferedInputStream(new FileInputStream(new File(id + ".txt")));
                OutputStream os = response.getOutputStream()
        ) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            IOUtils.copy(is, os);
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
