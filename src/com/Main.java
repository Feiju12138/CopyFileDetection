package com;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        // 当前路径
        File file = new File("./");

        File[] files = file.listFiles();

        // 不重复文件列表
        List<File> notExist = new ArrayList();
        // 重复文件列表
        List<File> exist = new ArrayList();

        for (File f : files) {

            boolean key = false;

            String src = f.getParent()+"/"+f.getName();
            Date createTime = getCreateTime2(src);
            long time = createTime.getTime();

//            System.out.println(time);

            for (File notExistFile : notExist) {
                Date notExistFileCreateTime = getCreateTime2(notExistFile.getParent()+"/"+notExistFile.getName());
                long time2 = notExistFileCreateTime.getTime();
                if (time==time2) {
                    key = true;
                }
            }

            if (key) {
                exist.add(f);
            } else {
                notExist.add(f);
            }


        }

        System.out.println("不重复文件列表：");
        for (File f : notExist) {
            System.out.println(f.getName());
        }

        System.out.println();
        System.out.println("重复文件列表：");
        for (File f : exist) {
            System.out.println(f.getName());
        }

//        Date date = getCreateTime2("/Users/hatsunemiku/Downloads/index1.py");
//        System.out.println(date.getTime());
    }

    // 获取文件创建时间方法
    private static Date getCreateTime2(String fullFileName){
        Path path= Paths.get(fullFileName);
        BasicFileAttributeView basicview= Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
        BasicFileAttributes attr;
        try {
            attr = basicview.readAttributes();
            Date createDate = new Date(attr.creationTime().toMillis());
            return createDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.set(1970, 0, 1, 0, 0, 0);
        return cal.getTime();
    }
}
