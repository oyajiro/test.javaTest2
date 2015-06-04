package kouta.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import kouta.service.UserService;
import kouta.entity.User;

@ManagedBean
@SessionScoped
public class UserController implements Serializable {

    private static final long serialVersionUID = 2962757649900398635L;

    @Autowired
    private UserService userService;
    private User user = new User();
    private User managedUser;
    private String SALT = "abc";
    private Boolean loggedIn = false;
    private HashMap<Integer, String> roles;

    public UserController() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext
                .getCurrentInstance());
        userService = ctx.getBean(UserService.class);
        HashMap<Integer, String> roles = new HashMap<Integer, String>();
        roles.put(0, "user");
        roles.put(1, "admin");
        this.roles = roles;
    }

    public HashMap<Integer, String> getRoles() {
        return roles;
    }

    public User getManagedUser() {
        if (this.managedUser == null) {
            String userId = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get("userId");
            if (userId != null) {
                this.managedUser = this.userService.read(Integer.parseInt(userId));
                this.managedUser.setIsNew(false);
            } else {
                this.managedUser = new User();
            }
        }

        return this.managedUser;
    }

    public String getRole() {
        if (!this.loggedIn) {
            return null;
        } else {
            return this.roles.get(user.getRole());
        }

    }

    public String save() {
        if (!managedUser.getNewPassword().isEmpty() || managedUser.getIsNew()) {
            managedUser.setPassword(this.getHash(managedUser.getNewPassword()));
        }

        userService.save(managedUser);
        this.managedUser = null;
        return "index?faces-redirect=true";
    }

    public String register() {
        user.setPassword(this.getHash(user.getPassword()));
        userService.save(user);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("The User " + this.user.getFio() + " Is Registered Successfully"));
        return "index?faces-redirect=true";
    }

    public String delete(User user) {
        userService.remove(user);
        return "index?faces-redirect=true";
    }

    public void auth(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error",
                "login and password required");
        if (user.getLogin() != null && user.getPassword() != null) {
            User dbuser = userService.findByLogin(user.getLogin());
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "User not found");

            if (dbuser != null) {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error",
                        "Invalid credentials");

                if (dbuser.getPassword().equals(this.getHash(user.getPassword()))) {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) facesContext.getExternalContext()
                            .getSession(true);
                    session.setAttribute("login", dbuser.getLogin());
                    session.setAttribute("fio", dbuser.getFio());
                    session.setAttribute("role", dbuser.getRole());
                    this.user = dbuser;
                    this.loggedIn = true;
                    try {
                        FacesContext.getCurrentInstance().getExternalContext()
                                .redirect("index.xhtml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", this.loggedIn);
    }

    public String getHash(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        digest.reset();
        byte[] input = null;
        password = password.concat(SALT);
        try {
            input = digest.digest(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Internal error"));
        }

        StringBuffer sb = new StringBuffer();
        for (byte b : input) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }

    public String logout() {
        FacesContext.getCurrentInstance().addMessage(null,

        new FacesMessage("Goodbye, " + user.getFio()));

        user = null;
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        httpSession.invalidate();
        return "index?faces-redirect=true";
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public UserService getuserService() {
        return userService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<User> getUsers() {
        return userService.getAll();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
