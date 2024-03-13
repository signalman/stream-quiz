package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Quiz1 {

    private static final String TARGET = "좋아";

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException, CsvException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                       .map(line -> line[1].replaceAll("\\s", ""))
                       .flatMap(hobbies -> Arrays.stream(hobbies.split(":")))
                       .collect(Collectors.toMap(hobby -> hobby, hobby -> 1, Integer::sum));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException, CsvException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                       .filter(line -> line[0].startsWith("정"))
                       .map(line -> line[1].replace(" ", ""))
                       .flatMap(hobbies -> Arrays.stream(hobbies.split(":")))
                       .collect(Collectors.toMap(hobby -> hobby, hobby -> 1, Integer::sum));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException, CsvException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                       .map(line -> count(line[2], 0))
                       .reduce(0, Integer::sum);
    }
    public int count(String src, int fromIndex){
        int index = src.indexOf(TARGET, 0);
        if(index >= 0){
            return 1 + count(src, index + 1);
        }
        return 0;
    }

    private List<String[]> readCsvLines() throws IOException, CsvException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv")
                                                                     .getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
