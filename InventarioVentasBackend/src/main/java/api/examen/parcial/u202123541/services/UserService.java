package api.examen.parcial.u202123541.services;

import org.apache.catalina.User;

public interface UserService {
    public User findById(Long id);

    public User register(User user);

    public User changePassword(User user);
}
