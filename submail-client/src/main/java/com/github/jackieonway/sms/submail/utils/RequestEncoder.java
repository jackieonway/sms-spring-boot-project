package com.github.jackieonway.sms.submail.utils;

import com.github.jackieonway.sms.core.exception.SmsException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * request Encoder
 *
 * @author bing_huang
 * @since V1.0 2020/07/09 7:36
 */
public class RequestEncoder {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";

    /**
     * 编码的字符串
     *
     * @param algorithm 编码类型 不允许为空
     * @param str       需编码字符串
     * @return String 已编码
     */
    @Nullable
    public static String encode(@NonNull String algorithm, @Nullable String str) {
        Assert.hasText(algorithm, "`algorithm` can not blank");
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new SmsException(e.getMessage(),e);
        }

    }

    /**
     * 获取原始字节并将其格式化。
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (byte aByte : bytes) {
            buf.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 将参数 url化
     *
     * @param data 参数
     * @return get url参数化
     * @see <a href="https://www.mysubmail.com/chs/documents/developer/gbibb3">数字签名验证模式</a>
     */
    public static String formatRequest(Map<String, Object> data) {
        //keySet获取键集
        List<String> keys = new ArrayList<>(data.keySet());
        //对键集进行排序
        Collections.sort(keys);

        StringBuilder reqStr = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String curKey = keys.get(i);
            Object curValue = data.get(curKey);
            if (i == keys.size() - 1) {
                reqStr.append(curKey).append("=").append(curValue);
            } else {
                reqStr.append(curKey).append("=").append(curValue).append("&");
            }

        }
        return reqStr.toString();
    }
}
