package org.safetynet.api.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.safetynet.api.constants.PathsConstants.FILEPATH;

@Service
public class ReadService {
    @PostConstruct
    public String loadFile() throws Exception{

            FileReader fileReader = null;
            BufferedReader reader = null;
            StringBuilder content = new StringBuilder();

            try {

                fileReader = new FileReader(FILEPATH);
                reader = new BufferedReader(fileReader);


                String line = reader.readLine();
                while (line != null) {

                    content.append(reader.readLine()).append(" \r");
                }
                System.out.println(content);
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
                reader.close();
            }
            return content.toString();

    }
}
