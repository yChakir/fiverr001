package fiverr.filter;

import fiverr.util.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component
public class FrontendInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String uri = request.getRequestURI();

    if (
        !uri.equals("/index.html") &&
        !uri.startsWith("/css/") &&
        !uri.startsWith("/js/") &&
        !uri.startsWith("/image/") &&
        !uri.startsWith("/api")
    ) {
      FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
      response.sendRedirect("/");
    }

    return super.preHandle(request, response, handler);
  }

}
