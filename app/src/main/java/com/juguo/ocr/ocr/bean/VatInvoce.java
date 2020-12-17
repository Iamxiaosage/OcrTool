package com.juguo.ocr.ocr.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VatInvoce {
    public static final String key_Amount_In_Words ="总价";
    public static final String key_Note_Drawer ="开票人";
    public static final String key_Seller_Address ="销售方地址";
    public static final String key_Seller_Register_Num ="销售方注册编号";
    public static final String key_Seller_Bank ="销售方银行";
    public static final String key_Total_Tax="纳税总额";
    public static final String key_Invoice_Type_Org="税收类型组织";
    public static final String key_Agent="代理";
    public static final String key_Purchaser_Bank="购买方银行";
    public static final String key_Checker="复核";
    public static final String key_Purchaser_Name="购买方姓名";
    public static final String key_Invoice_Type="发票类型";
    public static final String key_Sheet_Num="表格编号";
    public static final String key_Payee="收款人";
    public static final String key_Seller_Name="卖方姓名";
    public static final String key_Invoice_Num="发票编号";

    public String toMyString(){
        String val_amountInWords = wordsResultBean.getAmountInWords();
        String val_noteDrawer = wordsResultBean.getNoteDrawer();
        String val_sellerAddress = wordsResultBean.getSellerAddress();
        String val_sellerRegisterNum = wordsResultBean.getSellerRegisterNum();
        String val_sellerBank = wordsResultBean.getSellerBank();
        String val_totalTax = wordsResultBean.getTotalTax();
        String val_invoiceTypeOrg = wordsResultBean.getInvoiceTypeOrg();
        String val_agent = wordsResultBean.getAgent();
        String val_purchaserBank = wordsResultBean.getPurchaserBank();
        String val_checker = wordsResultBean.getChecker();
        String val_purchaserName = wordsResultBean.getPurchaserName();
        String val_invoiceType = wordsResultBean.getInvoiceType();
        String val_sheetNum = wordsResultBean.getSheetNum();
        String val_payee = wordsResultBean.getPayee();
        String val_sellerName = wordsResultBean.getSellerName();
        String val_invoiceNum = wordsResultBean.getInvoiceNum();

        String str=key_Amount_In_Words+"："+val_amountInWords+"\n"+
        key_Note_Drawer+"："+val_noteDrawer+"\n"+
        key_Seller_Address+"："+val_sellerAddress+"\n"+
        key_Seller_Register_Num+"："+val_sellerRegisterNum+"\n"+
        key_Total_Tax+"："+val_totalTax+"\n"+
        key_Seller_Bank+"："+val_sellerBank+"\n"+
        key_Invoice_Type_Org+"："+val_invoiceTypeOrg+"\n"+
        key_Agent+"："+val_agent+"\n"+
        key_Purchaser_Bank+"："+val_purchaserBank+"\n"+
        key_Checker+"："+val_checker+"\n"+
        key_Purchaser_Name+"："+val_purchaserName+"\n"+
        key_Invoice_Type+"："+val_invoiceType+"\n"+
        key_Sheet_Num+"："+val_sheetNum+"\n"+
        key_Payee+"："+val_payee+"\n"+
        key_Seller_Name+"："+val_sellerName+"\n"+
        key_Invoice_Num+"："+val_invoiceNum+"\n";
//        String val_agent = wordsResultBean.getAgent();
        return str;
    }

    @Override
    public String toString() {



        return "VatInvoce{" +
                "_$Words_result42=" + wordsResultBean +
                ", log_id=" + log_id +
                ", words_result_num=" + words_result_num +
                '}';
    }

    @SerializedName("words_result")
    private WordsResultBean wordsResultBean; // FIXME check this code
    private long log_id;
    private int words_result_num;

    public WordsResultBean getWordsResultBean() {
        return wordsResultBean;
    }

    public void setWordsResultBean(WordsResultBean wordsResultBean) {
        this.wordsResultBean = wordsResultBean;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public static class WordsResultBean {
        @SerializedName("AmountInWords")
        private String AmountInWords206; // FIXME check this code
        private String NoteDrawer;
        private String SellerAddress;
        private String SellerRegisterNum;
        private String MachineCode;
        private String Remarks;
        private String SellerBank;
        private String TotalTax;
        private String CheckCode;
        private String InvoiceCode;
        private String InvoiceDate;
        private String PurchaserRegisterNum;
        private String InvoiceTypeOrg;
        private String Password;
        private String Agent;
        private String AmountInFiguers;
        private String PurchaserBank;
        private String Checker;
        private String City;
        private String TotalAmount;
        private String PurchaserName;
        private String Province;
        private String InvoiceType;
        private String SheetNum;
        private String PurchaserAddress;
        private String Payee;
        private String SellerName;
        private String InvoiceNum;
        private List<CommodityPriceBean> CommodityPrice;
        private List<CommodityNumBean> CommodityNum;
        private List<CommodityTaxRateBean> CommodityTaxRate;
        private List<CommodityAmountBean> CommodityAmount;
        private List<CommodityTypeBean> CommodityType;
        private List<CommodityTaxBean> CommodityTax;
        private List<CommodityUnitBean> CommodityUnit;
        private List<CommodityNameBean> CommodityName;

        @Override
        public String toString() {
            return "WordsResultBean{" +
                    "_$AmountInWords206='" + AmountInWords206 + '\'' +
                    ", NoteDrawer='" + NoteDrawer + '\'' +
                    ", SellerAddress='" + SellerAddress + '\'' +
                    ", SellerRegisterNum='" + SellerRegisterNum + '\'' +
                    ", MachineCode='" + MachineCode + '\'' +
                    ", Remarks='" + Remarks + '\'' +
                    ", SellerBank='" + SellerBank + '\'' +
                    ", TotalTax='" + TotalTax + '\'' +
                    ", CheckCode='" + CheckCode + '\'' +
                    ", InvoiceCode='" + InvoiceCode + '\'' +
                    ", InvoiceDate='" + InvoiceDate + '\'' +
                    ", PurchaserRegisterNum='" + PurchaserRegisterNum + '\'' +
                    ", InvoiceTypeOrg='" + InvoiceTypeOrg + '\'' +
                    ", Password='" + Password + '\'' +
                    ", Agent='" + Agent + '\'' +
                    ", AmountInFiguers='" + AmountInFiguers + '\'' +
                    ", PurchaserBank='" + PurchaserBank + '\'' +
                    ", Checker='" + Checker + '\'' +
                    ", City='" + City + '\'' +
                    ", TotalAmount='" + TotalAmount + '\'' +
                    ", PurchaserName='" + PurchaserName + '\'' +
                    ", Province='" + Province + '\'' +
                    ", InvoiceType='" + InvoiceType + '\'' +
                    ", SheetNum='" + SheetNum + '\'' +
                    ", PurchaserAddress='" + PurchaserAddress + '\'' +
                    ", Payee='" + Payee + '\'' +
                    ", SellerName='" + SellerName + '\'' +
                    ", InvoiceNum='" + InvoiceNum + '\'' +
                    ", CommodityPrice=" + CommodityPrice +
                    ", CommodityNum=" + CommodityNum +
                    ", CommodityTaxRate=" + CommodityTaxRate +
                    ", CommodityAmount=" + CommodityAmount +
                    ", CommodityType=" + CommodityType +
                    ", CommodityTax=" + CommodityTax +
                    ", CommodityUnit=" + CommodityUnit +
                    ", CommodityName=" + CommodityName +
                    '}';
        }

        public String getAmountInWords() {
            return AmountInWords206;
        }

        public void setAmountInWords206(String amountInWords206) {
            this.AmountInWords206 = amountInWords206;
        }

        public String getNoteDrawer() {
            return NoteDrawer;
        }

        public void setNoteDrawer(String NoteDrawer) {
            this.NoteDrawer = NoteDrawer;
        }

        public String getSellerAddress() {
            return SellerAddress;
        }

        public void setSellerAddress(String SellerAddress) {
            this.SellerAddress = SellerAddress;
        }

        public String getSellerRegisterNum() {
            return SellerRegisterNum;
        }

        public void setSellerRegisterNum(String SellerRegisterNum) {
            this.SellerRegisterNum = SellerRegisterNum;
        }

        public String getMachineCode() {
            return MachineCode;
        }

        public void setMachineCode(String MachineCode) {
            this.MachineCode = MachineCode;
        }

        public String getRemarks() {
            return Remarks;
        }

        public void setRemarks(String Remarks) {
            this.Remarks = Remarks;
        }

        public String getSellerBank() {
            return SellerBank;
        }

        public void setSellerBank(String SellerBank) {
            this.SellerBank = SellerBank;
        }

        public String getTotalTax() {
            return TotalTax;
        }

        public void setTotalTax(String TotalTax) {
            this.TotalTax = TotalTax;
        }

        public String getCheckCode() {
            return CheckCode;
        }

        public void setCheckCode(String CheckCode) {
            this.CheckCode = CheckCode;
        }

        public String getInvoiceCode() {
            return InvoiceCode;
        }

        public void setInvoiceCode(String InvoiceCode) {
            this.InvoiceCode = InvoiceCode;
        }

        public String getInvoiceDate() {
            return InvoiceDate;
        }

        public void setInvoiceDate(String InvoiceDate) {
            this.InvoiceDate = InvoiceDate;
        }

        public String getPurchaserRegisterNum() {
            return PurchaserRegisterNum;
        }

        public void setPurchaserRegisterNum(String PurchaserRegisterNum) {
            this.PurchaserRegisterNum = PurchaserRegisterNum;
        }

        public String getInvoiceTypeOrg() {
            return InvoiceTypeOrg;
        }

        public void setInvoiceTypeOrg(String InvoiceTypeOrg) {
            this.InvoiceTypeOrg = InvoiceTypeOrg;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getAgent() {
            return Agent;
        }

        public void setAgent(String Agent) {
            this.Agent = Agent;
        }

        public String getAmountInFiguers() {
            return AmountInFiguers;
        }

        public void setAmountInFiguers(String AmountInFiguers) {
            this.AmountInFiguers = AmountInFiguers;
        }

        public String getPurchaserBank() {
            return PurchaserBank;
        }

        public void setPurchaserBank(String PurchaserBank) {
            this.PurchaserBank = PurchaserBank;
        }

        public String getChecker() {
            return Checker;
        }

        public void setChecker(String Checker) {
            this.Checker = Checker;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String TotalAmount) {
            this.TotalAmount = TotalAmount;
        }

        public String getPurchaserName() {
            return PurchaserName;
        }

        public void setPurchaserName(String PurchaserName) {
            this.PurchaserName = PurchaserName;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String Province) {
            this.Province = Province;
        }

        public String getInvoiceType() {
            return InvoiceType;
        }

        public void setInvoiceType(String InvoiceType) {
            this.InvoiceType = InvoiceType;
        }

        public String getSheetNum() {
            return SheetNum;
        }

        public void setSheetNum(String SheetNum) {
            this.SheetNum = SheetNum;
        }

        public String getPurchaserAddress() {
            return PurchaserAddress;
        }

        public void setPurchaserAddress(String PurchaserAddress) {
            this.PurchaserAddress = PurchaserAddress;
        }

        public String getPayee() {
            return Payee;
        }

        public void setPayee(String Payee) {
            this.Payee = Payee;
        }

        public String getSellerName() {
            return SellerName;
        }

        public void setSellerName(String SellerName) {
            this.SellerName = SellerName;
        }

        public String getInvoiceNum() {
            return InvoiceNum;
        }

        public void setInvoiceNum(String InvoiceNum) {
            this.InvoiceNum = InvoiceNum;
        }

        public List<CommodityPriceBean> getCommodityPrice() {
            return CommodityPrice;
        }

        public void setCommodityPrice(List<CommodityPriceBean> CommodityPrice) {
            this.CommodityPrice = CommodityPrice;
        }

        public List<CommodityNumBean> getCommodityNum() {
            return CommodityNum;
        }

        public void setCommodityNum(List<CommodityNumBean> CommodityNum) {
            this.CommodityNum = CommodityNum;
        }

        public List<CommodityTaxRateBean> getCommodityTaxRate() {
            return CommodityTaxRate;
        }

        public void setCommodityTaxRate(List<CommodityTaxRateBean> CommodityTaxRate) {
            this.CommodityTaxRate = CommodityTaxRate;
        }

        public List<CommodityAmountBean> getCommodityAmount() {
            return CommodityAmount;
        }

        public void setCommodityAmount(List<CommodityAmountBean> CommodityAmount) {
            this.CommodityAmount = CommodityAmount;
        }

        public List<CommodityTypeBean> getCommodityType() {
            return CommodityType;
        }

        public void setCommodityType(List<CommodityTypeBean> CommodityType) {
            this.CommodityType = CommodityType;
        }

        public List<CommodityTaxBean> getCommodityTax() {
            return CommodityTax;
        }

        public void setCommodityTax(List<CommodityTaxBean> CommodityTax) {
            this.CommodityTax = CommodityTax;
        }

        public List<CommodityUnitBean> getCommodityUnit() {
            return CommodityUnit;
        }

        public void setCommodityUnit(List<CommodityUnitBean> CommodityUnit) {
            this.CommodityUnit = CommodityUnit;
        }

        public List<CommodityNameBean> getCommodityName() {
            return CommodityName;
        }

        public void setCommodityName(List<CommodityNameBean> CommodityName) {
            this.CommodityName = CommodityName;
        }

        public static class CommodityPriceBean {
            /**
             * row : 1
             * word : 240.0110674
             */

            private String row;
            private String word;

            public String getRow() {
                return row;
            }

            public void setRow(String row) {
                this.row = row;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class CommodityNumBean {
            /**
             * row : 1
             * word : 1
             */

            private String row;
            private String word;

            public String getRow() {
                return row;
            }

            public void setRow(String row) {
                this.row = row;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class CommodityTaxRateBean {
            /**
             * row : 1
             * word : A
             */

            private String row;
            private String word;

            public String getRow() {
                return row;
            }

            public void setRow(String row) {
                this.row = row;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class CommodityAmountBean {
            /**
             * row : 1
             * word : 295.02
             */

            private String row;
            private String word;

            public String getRow() {
                return row;
            }

            public void setRow(String row) {
                this.row = row;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class CommodityTypeBean {
            /**
             * row : 1
             * word : W3T1600001
             */

            private String row;
            private String word;

            public String getRow() {
                return row;
            }

            public void setRow(String row) {
                this.row = row;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class CommodityTaxBean {
            /**
             * row : 1
             * word : 16.98
             */

            private String row;
            private String word;

            public String getRow() {
                return row;
            }

            public void setRow(String row) {
                this.row = row;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class CommodityUnitBean {
            /**
             * row : 1
             * word : 李
             */

            private String row;
            private String word;

            public String getRow() {
                return row;
            }

            public void setRow(String row) {
                this.row = row;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class CommodityNameBean {
            /**
             * row : 1
             * word : *信息技术服务*技术服务费
             */

            private String row;
            private String word;

            public String getRow() {
                return row;
            }

            public void setRow(String row) {
                this.row = row;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }
    }
}
