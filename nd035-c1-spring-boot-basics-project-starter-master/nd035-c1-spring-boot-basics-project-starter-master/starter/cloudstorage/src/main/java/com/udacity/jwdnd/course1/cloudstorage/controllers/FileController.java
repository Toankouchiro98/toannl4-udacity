package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/files")
public class FileController {

    private FileService fileService;

    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication , Model model) throws IOException  {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        String fileName = multipartFile.getOriginalFilename();

        if("".equals(fileName)){
            model.addAttribute("fail", "File must not be empty.");
            model.addAttribute("success", false);
        }
        else if(!fileService.isHaveFile(userId, fileName)){
            model.addAttribute("fail", "File already exists.");
            model.addAttribute("success", false);
        } else {
            String contentType = multipartFile.getContentType();
            Long fileSize = multipartFile.getSize();
            byte[] fileData = multipartFile.getBytes();

            var file = new File(null, fileName, contentType, fileSize, userId, fileData);
            fileService.uploadFile(file);
            model.addAttribute("success", true);
        }
        return "result";
    }

    @GetMapping("/views/{fileId}")
    public void ViewFile( HttpServletResponse response, Authentication authentication, @PathVariable("fileId") Integer fileId) throws IOException {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        File file = fileService.getFile( fileId, userId);
        if(file != null){
            response.setContentType(file.getContentType());
            response.setHeader("Content-Disposition", "fileName=\"" + file.getFileName() + "\"");
            response.setContentLengthLong(file.getFileSize());

            OutputStream output = response.getOutputStream();
            try {
                output.write(file.getFileData(), 0, file.getFileData().length);
            } catch (Exception e){

            } finally {
                output.close();
            }

        }
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model){
        fileService.deteleFile(fileId);
        model.addAttribute("delete",true);
        return "result";
    }
}
