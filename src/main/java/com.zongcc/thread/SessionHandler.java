package com.zongcc.thread;

/**
 * @author chunchengzong
 * @date 2018-04-24 15:44
 **/
public class SessionHandler {
    public static ThreadLocal<Session> session = new ThreadLocal<Session>();

    //@Data
    public static class Session {
        private String id;
        private String user;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public void createSession() {
        session.set(new Session());
    }

    public String getUser() {
        return session.get().getUser();
    }

    public String getStatus() {
        return session.get().getStatus();
    }

    public void setStatus(String status) {
        session.get().setStatus(status);
    }

    public void setUser(String user) {
        session.get().setUser(user);
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            final int j = i;
            new Thread(() -> {
                SessionHandler handler = new SessionHandler();
                handler.createSession();
                handler.setStatus(Thread.currentThread().getName() + ":status:close:"+session.get().hashCode());
                handler.setUser(Thread.currentThread().getName() + ":user:" + j+":"+session.get().hashCode());
                System.out.println(handler.getStatus());
                System.out.println(handler.getUser());
            }, "threadName:" + i).start();
        }
    }
}