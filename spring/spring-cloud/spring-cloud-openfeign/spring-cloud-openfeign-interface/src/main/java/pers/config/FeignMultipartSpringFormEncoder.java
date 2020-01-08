package pers.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static feign.form.ContentType.MULTIPART;
import static java.util.Collections.singletonMap;

public class FeignMultipartSpringFormEncoder extends FormEncoder {

    public FeignMultipartSpringFormEncoder (Encoder delegate) {
        super(delegate);

        MultipartFormContentProcessor processor = (MultipartFormContentProcessor) getContentProcessor(MULTIPART);
        processor.addFirstWriter(new SpringSingleMultipartFileWriter());
        processor.addFirstWriter(new SpringManyMultipartFilesWriter());
    }

    @Override
    public void encode (Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (bodyType.equals(MultipartFile[].class)) {
            MultipartFile[] files = (MultipartFile[]) object;
            LinkedMultiValueMap data = new LinkedMultiValueMap<String, MultipartFile>(files.length);
            for (MultipartFile file : files) {
                data.add(file.getName(), file);
            }
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else if (bodyType.equals(MultipartFile.class)) {
            MultipartFile file = (MultipartFile) object;
            Map<String, Object> data = singletonMap(file.getName(), object);
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else if (isMultipartFileCollection(object)) {
            Iterable iterable = (Iterable<?>) object;
            Map data = new HashMap<String, Object>();
            for (Object item : iterable) {
                MultipartFile file = (MultipartFile) item;
                data.put(file.getName(), file);
            }
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else {
            super.encode(object, bodyType, template);
        }
    }

    private boolean isMultipartFileCollection (Object object) {
        if (!(object instanceof Iterable)) {
            return false;
        }
        Iterable<?> iterable = (Iterable<?>) object;
        Iterator<?> iterator = iterable.iterator();
        return iterator.hasNext() && iterator.next() instanceof MultipartFile;
    }

}
