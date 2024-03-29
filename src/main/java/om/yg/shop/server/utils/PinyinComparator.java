package om.yg.shop.server.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Comparator;

/**
 * 汉字按照拼音排序的比较器
 *
 * @author KennyLee 2009-2-23 10:08:59
 */
public class PinyinComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        char c1 = ((String) o1).charAt(0);
        char c2 = ((String) o2).charAt(0);
        return concatPinyinStringArray(
                PinyinHelper.toHanyuPinyinStringArray(c1)).compareTo(
                concatPinyinStringArray(PinyinHelper
                        .toHanyuPinyinStringArray(c2)));
    }

    private String concatPinyinStringArray(String[] pinyinArray) {
        StringBuffer pinyinSbf = new StringBuffer();
        if ((pinyinArray != null) && (pinyinArray.length > 0)) {
            for (int i = 0; i < pinyinArray.length; i++) {
                pinyinSbf.append(pinyinArray[i]);
            }
        }
        return pinyinSbf.toString();
    }
}