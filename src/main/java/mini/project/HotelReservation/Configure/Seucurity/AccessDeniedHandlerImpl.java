//package mini.project.HotelReservation.Configure.Seucurity;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        String requestUri = request.getRequestURI();
//
//        if (requestUri.contains("/boards/portfolio/write")) {
//            request.setAttribute("msg", "플래너 등급 유저만 작성 가능합니다.");
//            request.setAttribute("nextPage", "/");
//            request.getRequestDispatcher("/error/redirect").forward(request, response);
//        } else {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//    }
//}
