package org.fitnessapp.data.db;

import org.fitnessapp.data.db.model.Users;
import java.util.List;

public interface DatabaseOperations {

    boolean create(Users users);
    List<Users> read();
    boolean update(Users users);
    boolean delete(Users users);
    boolean checkIfExist(Users users);

    boolean checkIfUserNameExist(Users users);

    boolean checkIfCredentialsAreCorrect(Users users);
}
