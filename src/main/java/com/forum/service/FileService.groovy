package com.forum.service

import org.springframework.web.multipart.MultipartFile

interface FileService {
    String save(String path, MultipartFile file)

}