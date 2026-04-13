package com.ershou.ershou.utils;

import javax.servlet.http.HttpServletRequest;

public class GetLoginInfo {
    public static String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");

        if (userAgent == null) {
            return "Unknown Browser";
        }

        // 常见浏览器的正则表达式
        if (userAgent.contains("Chrome")) {
            return "Chrome";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {
            return "Safari";
        } else if (userAgent.contains("Edge")) {
            return "Edge";
        } else if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            return "Internet Explorer";
        }

        return "Unknown Browser";
    }

    public static String getOs(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");

        if (userAgent == null) {
            return "Unknown OS";
        }

        // 判断操作系统
        if (userAgent.contains("Windows NT")) {
            return "Windows";
        } else if (userAgent.contains("Mac OS X")) {
            return "Mac OS";
        } else if (userAgent.contains("X11") || userAgent.contains("Linux")) {
            return "Linux";
        } else if (userAgent.contains("Android")) {
            return "Android";
        } else if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
            return "iOS";
        }

        return "Unknown OS";
    }

    public static String getIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            // 如果没有 X-Forwarded-For 头，回退到直接获取 IP
            clientIp = request.getRemoteAddr();
        } else {
            // 如果有代理信息，取第一个 IP（真实 IP）
            clientIp = clientIp.split(",")[0];
        }
        return clientIp;
    }
}
