package com.jasreport.web;

import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 5918509942002071902L;

	protected static Log log = LogFactory.getLog(BaseAction.class);

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	protected String getDatePattern() {
		return "yyyy-MM-dd";
	}

	protected ObjectMapper jackson0() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat(getDatePattern()));
		mapper.getDeserializationConfig().setDateFormat(new SimpleDateFormat(getDatePattern()));
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		return mapper;
	}

	protected ObjectMapper jackson() {
		ServletContext ctx = ServletActionContext.getServletContext();
		ObjectMapper mapper = (ObjectMapper) ctx.getAttribute("jackson_" + getDatePattern());
		if (mapper == null) {
			mapper = jackson0();
			ctx.setAttribute("jackson_" + getDatePattern(), mapper);
		}
		return mapper;
	}

	protected <T> T readJson(Class<T> typeOfT) {
		return readJson(typeOfT, null);
	}

	protected <T> T readJson(Class<T> typeOfT, String paramName) {
		try {
			String data = getRequest().getParameter(paramName == null ? "data" : paramName);
			if (data == null)
				return null;
			return jackson().readValue(data, typeOfT);
		} catch (Exception e) {
			log.error("readJson error", e);
		}
		return null;
	}

	protected String sf_bl_str;

	protected String[] getSerializeFieldBlackList() {
		return sf_bl_str != null ? sf_bl_str.split(" ") : null;
	}

	protected String sf_wl_str;

	protected String[] getSerializeFieldWhiteList() {
		return sf_wl_str != null ? sf_wl_str.split(" ") : null;
	}

	protected void writeText(String txt) {
		getResponse().setContentType("text/plain");
		getResponse().setCharacterEncoding("utf-8");
		try {
			getResponse().getWriter().write(txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void writeJson(Object obj) {
		FilterProvider filters = null;
		ObjectMapper mapper = null;
		if (getSerializeFieldWhiteList() != null) {
			filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept(getSerializeFieldWhiteList()));
		} else if (getSerializeFieldBlackList() != null) {
			filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.serializeAllExcept(getSerializeFieldBlackList()));
		} else {
			mapper = jackson();
		}
		// and then serialize using that filter provider:
		getResponse().setContentType("text/html");
		getResponse().setCharacterEncoding("utf-8");
		try {
			if (mapper == null) {
				mapper = jackson0();
				mapper.getSerializationConfig().setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
					@Override
					public Object findFilterId(AnnotatedClass ac) {
						return "myFilter";
					}
				});
				mapper.filteredWriter(filters).writeValue(getResponse().getOutputStream(), obj);
			} else {
				mapper.writeValue(getResponse().getOutputStream(), obj);
			}
		} catch (Exception e) {
			log.error("writeJson error", e);
		}
	}

	protected ActionResult getSuccessResult(Object data) {
		ActionResult result = new ActionResult();
		result.setSuccess(true);
		result.setMessage(null);
		result.setData(data);
		return result;
	}

	protected void resultSuccess(Object data) {
		resultSuccess(null, data);
	}

	protected void resultSuccess(String message) {
		resultSuccess(message, null);
	}

	protected void resultSuccess(String message, Object data) {
		resultSuccess(message, data, 0);
	}

	protected void resultSuccess(String message, Object data, long total) {
		ActionResult result = new ActionResult();
		result.setSuccess(true);
		result.setMessage(message);
		result.setData(data);
		result.setTotal((int) total);
		writeJson(result);
	}

	protected void resultFailed(String message) {
		ActionResult result = new ActionResult();
		result.setSuccess(false);
		result.setMessage(message);
		writeJson(result);
	}

	protected String getRealPath(String relativePath) {
		return getRequest().getSession().getServletContext().getRealPath(relativePath);
	}

	protected String ispub;

	public void setIspub(String ispub) {
		this.ispub = ispub;
	}

	protected Object getFeature(String key) {
		return getRequest().getSession().getAttribute(key);
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		} else {
			String[] candidates = ip.split(",");
			for (String cand : candidates) {
				if (!("unknown".equalsIgnoreCase(cand))) {
					ip = cand;
					break;
				}
			}
		}
		return ip;
	}

	public static class ActionResult {
		private boolean success;

		private String message;

		private Object data;

		private int total;

		private Object attachment;

		public Object getAttachment() {
			return attachment;
		}

		public void setAttachment(Object attachment) {
			this.attachment = attachment;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

	}
}
