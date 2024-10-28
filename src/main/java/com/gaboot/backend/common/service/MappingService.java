package com.gaboot.backend.common.service;

import com.gaboot.backend.common.constant.Storage;
import com.gaboot.backend.common.dto.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MappingService<T> {

    public void mapResponseSuccess(ResponseDto<T> resp, T data, String msg){
        mapResponseMsg(resp, msg);
        resp.setDatum(data);
    }

    public void mapResponseSuccess(ResponseDto<T> resp, List<T> data, String msg){
        mapResponseMsg(resp, msg);
        resp.setData(data);
    }

    public void mapResponseSuccess(ResponseDto<T> resp, List<T> data, String msg, int lastPage, int totalData) {
        mapResponseMsg(resp, msg);
        resp.setData(data);
        resp.setLastPage(lastPage);
        resp.setTotalData(totalData);
    }

    void mapResponseMsg(ResponseDto<T> resp, String msg) {
        if(msg.isEmpty()) resp.setMessage(msg);
        resp.setMessage("OK");
        resp.setSuccess(true);
    }

    public void mapResponseMsgFail(ResponseDto<T> resp) {
        resp.setMessage("Error");
        resp.setSuccess(false);
    }


}
