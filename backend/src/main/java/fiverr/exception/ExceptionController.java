package fiverr.exception;

import ch.qos.logback.core.net.server.Client;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleMultipartException(HttpServletRequest request, Exception exception) {
        //todo custom message
        ClientException customException = new ClientException("olala");
        customException.setStackTrace(new StackTraceElement[]{});
        return ResponseEntity.badRequest().body(customException);
    }
}
