package model;



import java.util.HashMap;

import model.tables.User;
import presenter.app_config.MainApp;

/**
 * Created by ddopi on 5/30/2017.
 */

public class LoginModel {



    public void setUser(HashMap<String,String> userData)
    {
        User user=new User();
        user.setUser_id(Integer.parseInt(userData.get("user_id")));
        user.setUser_name(userData.get("username"));
        user.setUser_password(userData.get("user_pass"));
        user.setUser_email(userData.get("email"));
        user.setUser_role(userData.get("user_role"));

        MainApp.realm.beginTransaction();
        MainApp.realm.copyToRealmOrUpdate(user);
        MainApp.realm.commitTransaction();
    }


}
