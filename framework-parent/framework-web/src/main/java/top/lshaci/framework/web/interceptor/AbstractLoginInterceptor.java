package top.lshaci.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import top.lshaci.framework.web.constant.WebConstant;
import top.lshaci.framework.web.enums.ErrorCode;
import top.lshaci.framework.web.model.JsonResponse;
import top.lshaci.framework.web.utils.HttpResponseUtils;
import top.lshaci.framework.web.utils.SessionUserUtils;

/**
 * Login Interceptor
 * 
 * @author lshaci
 * @since 0.0.4
 */
@Slf4j
public abstract class AbstractLoginInterceptor implements HandlerInterceptor {
	
	/**
	 * login invalid, redirect url.
	 */
	private String redirectUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("LoginInterceptor: " + request.getRequestURI());

        if (!(handler instanceof HandlerMethod)) {
            log.warn("This request does not access controller!");
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (WebConstant.SWAGGER_CONTROLLER.equals(handlerMethod.getBeanType().getName())) {
            log.warn("This request is to access the swagger ui!");
            return true;
        }
		
		Object loginUser = SessionUserUtils.getUserInSession();
		/*
		 *  Determines whether the user exists, 
		 *  does not exist to return to the login interface, continues to intercept, 
		 *  exists by intercepting, released to the access page.
		 */
		if (loginUser == null) {
			log.warn("Not login.");
			if (isAjaxRequest(request)) {
				log.info("This request is an ajax request.");
				
				JsonResponse<Object> jsonResponse = JsonResponse
						.failure(ErrorCode.NOT_LOGIN_EXCEPTION.getMsg())
						.setCode(ErrorCode.NOT_LOGIN_EXCEPTION.getCode())
						.addOtherData("redirectUrl", redirectUrl);
				
				log.warn("No login, response json.");
				HttpResponseUtils.responseJson(jsonResponse);
			} else {
				log.warn("No login, redirect home page.");
				response.sendRedirect(redirectUrl);
			}
			return false;
		}
		
		log.debug("Already login.");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	/**
	 * Set the redirect url
	 * 
	 * @param redirectUrl the redirect url
	 * @return this
	 */
	public AbstractLoginInterceptor setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
		return this;
	}
	
	/**
	 * Determine whether an ajax request is made.
	 * 
	 * @param request the http servlet request<br><br>
	 * <i><b>For example:</b></i><br>
	 * <code>
	 * 		request.getHeader("x-requested-with") != null &amp;&amp; 
	 * 		request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")
	 * </code>
	 * @return if true means this request is ajax request
	 */
	protected abstract boolean isAjaxRequest(HttpServletRequest request);
	
}
