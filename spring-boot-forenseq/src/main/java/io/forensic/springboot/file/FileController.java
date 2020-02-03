package io.forensic.springboot.file;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.forensic.springboot.Other.OtherService;
import io.forensic.springboot.person.PersonService;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private OtherService otherService;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        fileStorageService.storeFile(file);
        return "success";
    }
    
    @PostMapping("/uploadFileTxt")
    public String uploadFileTxt(@RequestParam("file") MultipartFile file) {
        fileStorageService.storeFileTxt(file);
        return "success";
    }
    
    @PostMapping("/search/excelfile")
    public List<Object[]> searchByExcel(@RequestParam("file") MultipartFile file){
    	fileStorageService.storeFile(file);
    	System.out.println("Middle");
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return otherService.searchByExcelData("uploads/temp.xlsx");
    }
}
