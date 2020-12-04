package com.baidu.ocr.demo.bean;

import java.util.List;

public class Words {

    /**
     * words_result : [{"words":"vold onResult(string result. st"},{"words":"old onResult(string result- str)"},{"words":"e vold recGeneral(Context ctx, string"},{"words":"arms param. new GeneralParams ();"},{"words":"tDetectDirection(true);"},{"words":"tvertexesLocation(true):"},{"words":"tRecognizeGranularity( GeneralParams GRANU"},{"words":"etImageFile(new File(filePath));"},{"words":"nstance(ctx). recognizeGeneral(param, new"},{"words":"ride"},{"words":"de void onResult(GeneralResult result)("},{"words":"stringBullder sb- new StringBuilder():"},{"words":"for(Wordsimple wordsimple result gete"},{"words":"Wordword .(Word) wordsimple:"},{"words":"String words. word. getwords ();"},{"words":"List<word. Char? characterResults"},{"words":"g,e(g\"ocr\u201d,mg:\u201c文字:\"+wond"},{"words":"b append (word metHords o));"}]
     * log_id : 1334456250083049472
     * words_result_num : 18
     * direction : 0
     */

    private long log_id;
    private int words_result_num;
    private int direction;
    private List<WordsResultBean> words_result;

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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * words : vold onResult(string result. st
         */

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
