package com.pc.service;

import com.pc.beans.LookupMenuBeans;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class LookupService {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("classpath:admin/lookups/*")
    private Resource[] resources;

    /**
     * Input: my name is chris
     * Output: My Name Is Chris
     */
    public static String capitalizeEarchWord(String str) {
        String capitalizeWord = "";
        if (str != null && str.length() > 0) {
            String[] words = str.split("\\s");
            for (String w : words) {
                String first = w.substring(0, 1);
                String afterfirst = w.substring(1);
                capitalizeWord += first.toUpperCase() + afterfirst + " ";
            }
        } else {
            capitalizeWord = str;
        }
        return capitalizeWord.trim();
    }

    /**
     * Input: thisIsMyString
     * Output:this Is My String
     */
    public static String spiltWordByupperCase(String input) {
        String output = "";
        if (input != null && input.length() > 0) {
            String[] elements = input.split("(?=\\p{Upper})");
            if (elements.length > 0) {
                for (String word : elements) {
                    output += word + " ";
                }
            } else {
                output = input;
            }
        } else {
            output = input;
        }
        return output.trim();
    }

    public List<LookupMenuBeans> getlookupsMenuBean() throws Exception {
        List<LookupMenuBeans> list = new ArrayList<>();
        for (final Resource res : resources) {
            logger.info("Resources: " + res.getFilename());
            LookupMenuBeans bean = new LookupMenuBeans(res.getFilename(), getLookupHeading(res));
            list.add(bean);

        }

        List<LookupMenuBeans> sortedList = list.stream()
                .sorted(Comparator.comparing(LookupMenuBeans::getHeading))
                .collect(Collectors.toList());

        return sortedList;
    }

    public String getLookupHeading(Resource res) {
        try {
            String fileName = (res.getFilename().replace(".xhtml", "")).replace("_", " ");
            return capitalizeEarchWord(spiltWordByupperCase(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return "Display Name Not Found";
        }


    }
}
