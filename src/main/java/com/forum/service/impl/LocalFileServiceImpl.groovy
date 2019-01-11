package com.forum.service.impl

import com.forum.service.FileService
import com.forum.utils.CommonUtil
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class LocalFileServiceImpl implements FileService {
    @Override
    String save(String path, MultipartFile file) {
        String name = ""
        String pathname = ""
        String fileName = file.getOriginalFilename()
        File uploadFile = new File(path)
        if (!uploadFile.exists()) {
            uploadFile.mkdirs()
        }
        String end = CommonUtil.getExtension(file.getOriginalFilename())
        name = CommonUtil.generateUUID()
        String diskFileName = name + "." + end; //目标文件的文件名
        pathname = path + diskFileName;
        file.transferTo(new File(pathname))
        return diskFileName
    }
}
