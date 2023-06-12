package com.bbodek.meboard.controller.order;

import com.bbodek.meboard.domain.orderWork.MEISDF;
import com.bbodek.meboard.dto.util.FileDto;
import com.bbodek.meboard.service.orderService.DfOrderItemService;
import com.bbodek.meboard.service.util.FileService;
import com.bbodek.meboard.util.commonMsg.MessageCode;
import com.bbodek.meboard.util.file.MD5Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DfOrderController {

    @Autowired
    private DfOrderItemService dfOrderItemService;

    @Autowired
    private FileService fileService;


    //DF GET
    @GetMapping("/get/dfitem")
    private ResponseEntity<Map> DfOrderGetItem(){
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<MEISDF> meisdfsList = dfOrderItemService.GetDfOrderItemData();
            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("meisdfList",meisdfsList);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    @PostMapping("/get/dfitemimg/{id}")
    private ResponseEntity<Map> DfOrderGetItemImg(@PathVariable long id ){
        HashMap resultMap = new HashMap<String, String>();
        try {
            List<FileDto> fileDtosList = fileService.getFileList(id);

            List<byte[]> base64ToString = new ArrayList<>();
            for (FileDto fil: fileDtosList
                 ) {
                Blob imgBlob =  fil.getDfImg();
                byte[] imageContent = imgBlob.getBytes(1, (int) imgBlob.length());
                base64ToString.add(imageContent);
            }

            resultMap.put("ResultValue", MessageCode.COM_GET_DATA_MSG);
            resultMap.put("fileDtoList",base64ToString);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    //DF SET
    @PostMapping("/set/dfitem")
    private ResponseEntity<Map> DfOrderSetItem(@RequestBody MEISDF meisdf) {
        HashMap resultMap = new HashMap<String, String>();
        try {
            if (!meisdf.getDfCode().isEmpty()) {
                dfOrderItemService.SetDfOrderItemData(meisdf);
                resultMap.put("ResultValue", MessageCode.COM_SAVE_MSG);
            } else {
                resultMap.put("ResultValue", MessageCode.COM_CHECK_DATA_MSG);
            }
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

    public static final String TEST_DIRECTORY = "files";

    @PostMapping("/set/upload/{id}")
    private ResponseEntity<Map> DfOrderSetImg(@PathVariable long id,@RequestPart(value="files") List<MultipartFile> files)  throws IOException {
        HashMap resultMap = new HashMap<String, String>();
        try {
            FileDto fileDto = new FileDto();
            String originalName = null;
            String filename = null;
            String filePath = null;
            Long fileId = null;
            byte[] bytes;

            // 파일 업로드(여러개) 처리 부분
            for(MultipartFile file : files) {
                originalName = file.getOriginalFilename();
                filename = new MD5Generator(originalName).toString();
                bytes = file.getBytes();
                Blob blob = new SerialBlob(bytes);
                /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
                String savePath = System.getProperty("user.dir") + TEST_DIRECTORY;
                /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
                if (!new File(savePath).exists()) {
                    try{
                        new File(savePath).mkdirs();
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }
                }
                filePath = savePath + "/" + filename;

                file.transferTo(new File(filePath));

                fileDto = new FileDto();
                fileDto.setOrigFilename(originalName);
                fileDto.setFilename(filename);
                fileDto.setFilePath(filePath);
                fileDto.setBoardid(id);
                fileDto.setDfImg(blob);
                fileService.saveFile(fileDto);
            }

            resultMap.put("ResultValue", MessageCode.COM_SAVE_MSG);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("ResultValue", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }



}
