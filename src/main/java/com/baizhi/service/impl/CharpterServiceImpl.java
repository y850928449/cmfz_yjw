package com.baizhi.service.impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.CharpterService;
import com.baizhi.utils.AudioUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CharpterServiceImpl implements CharpterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    public List<Chapter> selectTwo(int id) {
        Example example = new Example(Chapter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("albumId", id);
        return chapterDao.selectByExample(example);
    }

    public void insert(Chapter chapter, MultipartFile file) {
        String oldName = file.getOriginalFilename();
        String s = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(oldName);
        String newName = s + "." + extension;
        /*int index=oldName.lastIndexOf(".");
        String newName=s+oldName.substring(index);*/
        File file1 = new File("G:\\mavenCode\\cmfz_yjw\\src\\main\\webapp\\chapter\\" + newName);
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*String pic = "image/album/" + oldName;*/
        //添加id
        String sid = UUID.randomUUID().toString();
        chapter.setId(sid);
        //添加大小
        String printSize = getPrintSize(file.getSize());
        chapter.setSize(printSize);
        //添加时长
        //AudioUtil.getDuration();
        Long duration = AudioUtil.getDuration(file1);
        chapter.setDuration(formatDuring(duration));
        //添加时间
        chapter.setTime(new Date());
        //添加路径
        chapter.setPath("/chapter/" + newName);
        chapterDao.insert(chapter);
    }

    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    public static String formatDuring(long mss) {
        long hours = (mss % (1000 * 60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / 60;
        //long seconds = (mss % (1000 * 60));
        long seconds = mss - hours * 3600 - minutes * 60;
        return hours + " 时 " + minutes + " 分 "
                + seconds + " 秒 ";
    }

    public List<Chapter> select() {
        List<Chapter> list = chapterDao.selectAll();
        return list;
    }

}
//mvn install:install-file -Dfile=G:\后期项目\项目相关\jave-1.0.2.jar -DgroupId=com.baizhi -DartifactId=jave -Dversion=1.0.2.RELEASE -Dpackaging=jar
