package org.next.infra.util;

import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import java.text.Normalizer;

public class MultipartFileUtils {

    public static String getNormalizedFileName(MultipartFile file) {
        return Normalizer.normalize(UriEncoder.decode(file.getOriginalFilename()), Normalizer.Form.NFC);
    }
}
