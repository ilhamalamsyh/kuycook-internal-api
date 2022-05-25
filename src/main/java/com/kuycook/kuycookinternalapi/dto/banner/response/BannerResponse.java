package com.kuycook.kuycookinternalapi.dto.banner.response;

import com.kuycook.kuycookinternalapi.models.Banner;

import java.util.ArrayList;
import java.util.List;

public class BannerResponse<T> {
    private int code;
    private boolean status;
    private List<String> messages = new ArrayList<>();
    private Banner data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Banner getData() {
        return data;
    }

    public void setData(Banner data) {
        this.data = data;
    }
}
