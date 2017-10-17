package dev.local.mongo.dao;

import dev.local.mongo.model.Teacher;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherDao extends BasicDAO<Teacher, ObjectId> {
    @Autowired
    public TeacherDao(Datastore ds) {
        super(ds);
    }

    /*public void saveTeacher(Teacher teacher) {
        getDatastore().save(teacher);
        ObjectId id = teacher.getId();
        System.out.println("======================");
        System.out.println(id);
    }

    public void deleteTeacher(ObjectId id) {
        getDatastore().delete(Teacher.class, id);
        System.out.println("======================");
        System.out.println("delete success! id:"+id);
    }*/

}
