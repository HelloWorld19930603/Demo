package com.yg.application.shop.server.utils;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip工具类
 * 扩展HuTool 压缩类无法别名文件名问题
 *
 * @author bxx
 */
public class ZipExpUtil {

    /**
     * 将文件压缩到Zip中去
     *
     * @param file      需要压缩的文件
     * @param zos       {@link ZipOutputStream}
     * @param entryName 节点名
     * @param filter    文件过滤条件
     * @throws IOException
     */
    public static void zip(File file, ZipOutputStream zos, String entryName, FileFilter filter)
            throws IOException {
        if (filter.accept(file) && file.isFile()) {
            try (
                    InputStream in = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(in)
            ) {
                zos.putNextEntry(new ZipEntry(FileNameUtil.cleanInvalid(entryName)));
                byte[] bytes = new byte[1024];
                int len;
                while ((len = bis.read(bytes)) != -1) {
                    zos.write(bytes, 0, len);
                }
                zos.closeEntry();
            }
        }
    }

    /**
     * 将文件压缩到Zip中去
     *
     * @param bytes     文件内容
     * @param zos       {@link ZipOutputStream}
     * @param entryName 节点名
     * @throws IOException
     */
    public static void zipByte(ZipOutputStream zos, byte[] bytes, String entryName)
            throws IOException {
        zos.putNextEntry(new ZipEntry(FileNameUtil.cleanInvalid(entryName)));
        zos.write(bytes);
        zos.closeEntry();
    }

    /**
     * 基于HuTool 将Excel数据压入Zip
     *
     * @param excelData excel数据
     * @param zos       {@link ZipOutputStream}
     * @param entryName 节点名称
     * @throws IOException
     */
    public static void zipExcel(Iterable<?> excelData, ZipOutputStream zos, String entryName) throws IOException {
        zos.putNextEntry(new ZipEntry(FileNameUtil.cleanInvalid(entryName)));
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(excelData, true);
        writer.flush(zos);
        writer.close();
        zos.closeEntry();
    }
}
