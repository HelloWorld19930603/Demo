package om.yg.shop.server.utils;

import java.util.Arrays;
import java.util.List;

/**
 * PingYingUtil
 *
 * @author bxx
 */
public class PingYingUtil {
    public static void main(String[] args) {
        test_sort_pinyin2();
    }

    public static void test_sort_pinyin2() {
        String[] arr = {"张三", "李四", "王五", "赵六", "安心", "哈哈A"
                , "哈哈", "哈", "怡情"};
        List<String> list = Arrays.asList(arr);
        Arrays.sort(arr, new PinyinComparator());
        System.out.println(list);
    }
}
