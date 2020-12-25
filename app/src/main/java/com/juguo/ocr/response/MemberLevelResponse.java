package com.juguo.ocr.response;

import com.juguo.ocr.base.BaseResponse;

import java.util.List;

public class MemberLevelResponse extends BaseResponse {

    private List<MemberLevelInfo> list;

    public List<MemberLevelInfo> getList() {
        return list;
    }

    public void setList(List<MemberLevelInfo> list) {
        this.list = list;
    }

    public class MemberLevelInfo {

        private int price; // 真实价格
        private int originalPrice; // 原价费用
        private String code;// 永久：9  年度：4  月度:2
        private String goodId;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(int originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
