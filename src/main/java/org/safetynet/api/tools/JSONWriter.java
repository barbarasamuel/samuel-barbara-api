package org.safetynet.api.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.safetynet.api.entity.PersonEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.safetynet.api.constants.PathsConstants.WRITTENFILEPATH;
@Slf4j
@Component
public class JSONWriter {

    public void writtenPersonsInJSON(List<PersonEntity> personEntityList) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writeValue(new File(WRITTENFILEPATH), personEntityList);/**/
            log.info("The persons list is writen.");

        }catch(IOException e){
            log.error("Error writing the persons list.", e);
        }
    }

}
