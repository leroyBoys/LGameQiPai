/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.util;

import com.lgame.util.file.FileTool;
import com.lgame.util.file.PropertiesTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

/*
 * 敏感词工具类
 */
public class SensitiveUtil {
    //日志

    private static final Logger logger = LoggerFactory.getLogger(SensitiveUtil.class);
    //默认字符
    private static final String DEFAULTWORD = "";
    //不合法标志
    private static final boolean ILLEGAL = false;
    //敏感字典名称
    private static final String SENSITIVEFILENAME = "keyword1.dic";
    //敏感词集合
    private static Map<Character, List<String>> SensitiveMap = null;
    private static boolean isOpen = false;

    //加载敏感词
    static {
        try {
            if (isOpen) {
                loadSensitiveWord();
            }
        } catch (Exception e) {
            logger.error("加载敏感词失败");
        }
    }

    /**
     * 替换text中的屏蔽词 将屏蔽词替换为
     *
     *
     * @param text 需要验证的字符串
     * @return 如果捕获到异常将返回空字符串
     */
    public static String replace(String text) {
        if (!isOpen) {
            return text;
        }
        //text转成字符数组
        char[] textArray = text.toCharArray();
        try {
            //text的长度
            int length = text.length();
            //遍历输入的每个字符
            for (int i = 0; i < length; i++) {
                Character findC = Character.valueOf(textArray[i]);
                boolean isFind = SensitiveMap.containsKey(findC);
                //如果首字找到
                if (isFind) {
                    List<String> list = SensitiveMap.get(findC);
                    //list 中的集合 {共产党,共产,共匪}
                    for (String s : list) {
                        //判断集合中的每个字符串
                        int count = 0;
                        for (int j = 0; j < s.length(); j++) {
                            //如果敏感词库中的词长度大于剩余字符长度 或者 匹配不成功
                            if ((s.length() > length - i) || textArray[i + j] != s.charAt(j)) {
                                continue;
                            } else {
                                count++;
                            }
                            //如果每个字符都匹配到 也就是找到敏感词
                            if (count == s.length()) {
                                //修改字符数组
                                for (int sl = 0; sl < s.length(); sl++) {
                                    textArray[i + sl] = '*';
                                }
                                //将指针向后推字符长度减1的值 比如 共产党 
                                i += s.length() - 1;
                                //退出当前循环 找下一个字
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("敏感词替换失败");
            return DEFAULTWORD;
        }
        return String.valueOf(textArray);
    }

    /**
     * 判断所给的字符串是否合法
     *
     * @param text
     * @return 合法返回true 不合法返回false 若捕获异常返回false
     */
    public static boolean isLegal(String text) {
        if (!isOpen) {
            return true;
        }
        try {
            int length = text.length();
            //text转成字符数组
            char[] textArray = text.toCharArray();
            //遍历输入的每个字符
            for (int i = 0; i < length; i++) {
                Character findC = Character.valueOf(textArray[i]);
                boolean isFind = SensitiveMap.containsKey(findC);
                //如果首字找到
                if (isFind) {
                    List<String> list = SensitiveMap.get(findC);
                    //list 中的集合 {共产党,共产,共匪}
                    for (String s : list) {
                        //判断集合中的每个字符串
                        int count = 0;
                        for (int j = 0; j < s.length(); j++) {
                            //如果敏感词库中的词长度大于剩余字符长度 或者 匹配不成功
                            if ((s.length() > length - i) || textArray[i + j] != s.charAt(j)) {
                                continue;
                            } else {
                                count++;
                            }
                            //如果每个字符都匹配到 也就是找到敏感词
                            if (count == s.length()) {
                                return false;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("敏感词验证失败");
            return ILLEGAL;
        }
        //没有敏感词
        return true;
    }

    private static void loadSensitiveWord() {
        //敏感词
        Set<String> SensitiveWord = new HashSet<String>(FileTool.readLines(new File(getFilePath())));
        SensitiveMap = new HashMap<Character, List<String>>();
        //将敏感词以首字为key进行索引
        Iterator< String> it = SensitiveWord.iterator();
        while (it.hasNext()) {
            String s = it.next();
            Character c = s.charAt(0);
            List<String> list = SensitiveMap.get(c);
            if (list == null) {
                list = new ArrayList<>();
                SensitiveMap.put(c, list);
            }
            list.add(s);
        }
        //排序
        sort();
    }

    /**
     * 对SensitiveMap中的value 进行排序 字数多的排在前面字数少的在后面
     */
    private static void sort() {
        Iterator<Entry<Character, List<String>>> it = SensitiveMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Character, List<String>> entry = it.next();
            List<String> list = entry.getValue();
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (o1.length() > o2.length()) {
                        return -1;
                    } else if (o1.length() < o2.length()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }
    }

    /**
     * 得到敏感字典的文件位置
     *
     * @return
     */
    private static String getFilePath() {
        return PropertiesTool.getPropertiesPath(SENSITIVEFILENAME);
    }

    public static void main(String[] args) {
        System.out.println(isLegal("学习平凡"));
        System.out.println(replace("习近平"));
    }
}
