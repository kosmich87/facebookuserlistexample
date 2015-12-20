package kz.kkurtukov.facebookuserlistexample.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by kkurtukov on 19.12.2015.
 */
@Table(name = "FBUser")
public class FBUser extends Model {

    @Column(name = "FbUid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
        public String fbUid;
    @Column(name = "Name")
        public String name;
    @Column(name = "LastName")
        public String lastName;
    @Column(name = "Gender")
        public String gender;
    @Column(name = "AgeRange")
        public int ageRange;
    @Column(name = "PhotoUrl")
        public String photoUrl;
    @Column(name = "BirthOrLivingPlace")
    public String birthOrLivingPlace;
        @Column(name = "StudyPlace")
    public String studyPlace;
        @Column(name = "State")
    public int state;
}
