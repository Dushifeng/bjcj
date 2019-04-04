package cn.lovezsm.bjcj.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
@Component
public class FileUtil {
    public static int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        reader.skip(Long.MAX_VALUE);
        int lines = reader.getLineNumber()+1;
        reader.close();

        return lines;
    }
}
