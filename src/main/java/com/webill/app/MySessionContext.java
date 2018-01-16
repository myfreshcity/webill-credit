package com.webill.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by david on 16/11/16.
 */
public class MySessionContext {
    private static ConcurrentHashMap mymap = new ConcurrentHashMap();
    public static Log logger = LogFactory.getLog(MySessionContext.class);

    public static void addSession(HttpSession session) {
        if (session != null) {

            logger.debug("add session:"+session.getId());
            mymap.put(session.getId(), session);
        }
    }

    public static void delSession(HttpSession session) {
        if (session != null) {
            logger.debug("remove session:"+session.getId());
            mymap.remove(session.getId());
        }
    }

    public static HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) mymap.get(session_id);
    }
}
