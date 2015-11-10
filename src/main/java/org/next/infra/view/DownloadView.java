package org.next.infra.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

@Slf4j
public class DownloadView extends AbstractView {

    public static final String DOWNLOAD_VIEW_BEAN_NAME = "downloadView";
    public static final String SAVE_AS_FILE_NAME = "saveAs";
    public static final String DOWNLOAD_FILE = "downloadFile";

    public static ModelAndView downloadView(File fileToClient, String saveAs) {
        ModelAndView mav = new ModelAndView(DOWNLOAD_VIEW_BEAN_NAME);
        mav.addObject(SAVE_AS_FILE_NAME, saveAs);
        mav.addObject(DOWNLOAD_FILE, fileToClient);
        return mav;
    }

    public void Download() {
        setContentType("application/download; utf-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = (File) model.get(DOWNLOAD_FILE);
        String saveAsFileName = (String) model.get(SAVE_AS_FILE_NAME);

        response.setContentType(getContentType());
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeSaveAsFileName(saveAsFileName) + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        sendFile(response, file);
    }

    private String encodeSaveAsFileName(String saveAs) throws UnsupportedEncodingException {
        return URLEncoder.encode(saveAs, "utf-8").replace("+", " ");
    }

    private void sendFile(HttpServletResponse response, File file) throws IOException {
        OutputStream clientOut = response.getOutputStream();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            FileCopyUtils.copy(fileInputStream, clientOut);
        } catch (Exception e) {
            log.debug("파일 다운로드중 오류 발생", e.getStackTrace());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    log.debug("파일 다운로드중 오류 발생", e.getStackTrace());
                    fileInputStream.close();
                } finally {
                    fileInputStream.close();
                }
            }
        }
        clientOut.flush();
    }
}
