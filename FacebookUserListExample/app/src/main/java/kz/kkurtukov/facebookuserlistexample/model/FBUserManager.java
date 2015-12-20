package kz.kkurtukov.facebookuserlistexample.model;

import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

/**
 * Created by kkurtukov on 19.12.2015.
 */
public class FBUserManager {

    private static final int DEFAULT_AGE_RANGE = 12;
    private static final int MAX = 80;
    private static final int MIN = 5;

    public static void parseFbUserList(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            parseSingleFbUser(jsonArray.getJSONObject(i));
        }
    }

    private static void parseSingleFbUser(JSONObject jsonObject) throws JSONException {
        String uid = jsonObject.getString("id");

        FBUser fbUser  = getFbUserById(uid);

        if (fbUser == null) {
            fbUser = new FBUser();
        }

        fbUser.fbUid = uid;
        fbUser.name = jsonObject.isNull("name") ? "" : jsonObject.getString("name");
        fbUser.lastName = jsonObject.isNull("last_name")
                ? fbUser.name.substring(fbUser.name.indexOf(" "), fbUser.name.length())
                : jsonObject.getString("last_name");
        fbUser.gender = jsonObject.isNull("gender") ? getDummyGender() : jsonObject.getString("gender");

        JSONObject tempJsonObject = jsonObject.isNull("age_range") ? null : jsonObject.getJSONObject("age_range");
        if (tempJsonObject != null){
            fbUser.ageRange = jsonObject.isNull("min") ? DEFAULT_AGE_RANGE : jsonObject.getInt("min");
        } else {
            fbUser.ageRange = getDummyAgeRange();
        }

        tempJsonObject = jsonObject.getJSONObject("picture");
        if (tempJsonObject != null){
            fbUser.photoUrl = tempJsonObject.getJSONObject("data").isNull("url")
                    ? ""
                    : tempJsonObject.getJSONObject("data").getString("url");
        }

        fbUser.birthOrLivingPlace = jsonObject.isNull("location")
                ? getDummyLocation()
                : jsonObject.getJSONObject("location").getString("name");

        JSONArray tempJsonArray = jsonObject.isNull("education") ? null : jsonObject.getJSONArray("education");
        if (tempJsonArray != null) {
            if (tempJsonArray.length() > 0) {
                fbUser.studyPlace = tempJsonArray.getJSONObject(0).getJSONObject("school").getString("name");
            }
            for (int i = 1; i < tempJsonArray.length(); i++) {
                fbUser.studyPlace = fbUser.studyPlace + "\n" + tempJsonArray.getJSONObject(i).getJSONObject("school").getString("name");
            }
        }else fbUser.studyPlace = getDummyStudy();

        fbUser.state = getDummyState();

        fbUser.save();
    }

    private static int getDummyState() {
        Random r = new Random();
        if (r.nextBoolean()){
            return 1;
        }
        return 0;
    }

    private static String getDummyStudy() {
        return "BMSTU";
    }

    private static String getDummyLocation() {
        return "Moscow";
    }

    private static int getDummyAgeRange() {
        return (int)(Math.random() * ((MAX - MIN) + 1));
    }

    private static String getDummyGender() {
        Random r = new Random();
        if (r.nextBoolean()){
            return "male";
        }
        return "female";
    }

    private static FBUser getFbUserById(String uid) {
        return new Select()
                .from(FBUser.class)
                .where("FbUid = ?", uid)
                .executeSingle();
    }

    public static List<FBUser> getFriendsByGender(String gender) {
        return new Select()
                .from(FBUser.class)
                .where("Gender = ? AND AgeRange > 12", gender)
                .orderBy("LastName")
                .execute();
    }

    public static List<FBUser> getChilds() {
        return new Select()
                .from(FBUser.class)
                .where("AgeRange <= 12")
                .orderBy("LastName")
                .execute();
    }
}
