package dev.local;

import com.mongodb.client.model.Aggregates;
import dev.local.mongo.dao.TeacherDao;
import dev.local.mongo.model.Group;
import dev.local.mongo.model.Teacher;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Projection;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.project;
import static org.mongodb.morphia.aggregation.Group.grouping;
import static org.mongodb.morphia.aggregation.Group.id;
import static org.mongodb.morphia.aggregation.Group.sum;
import static org.mongodb.morphia.aggregation.Projection.*;

public class MongoTools {
    private static final String XML = "classpath:mongoTest.xml";


    public static void main(String[] args) {
        ApplicationContext ap = new ClassPathXmlApplicationContext(XML);
        TeacherDao dao = (TeacherDao) ap.getBean("teacherDao");
//        batchAdd(dao);
        aggregateTest(dao);
        /*long i = dao.count();
        System.out.println(""+ i);*/
        /*Query<Teacher> q = dao.createQuery().field("name").equal("Andy");
        Teacher t2 = q.get();*/
        /*List<Teacher> list = dao.find().asList();
        for (Teacher tt : list) {
            dao.delete(tt);
        }*/

        /*System.out.println(t2);
        dao.deleteTeacher(t2.getId());*/
    }

    private static void aggregateTest(TeacherDao dao) {
        Datastore ds = dao.getDatastore();
        Query<Object> q = ds.getQueryFactory().createQuery(ds);
        Projection secToMs = expression("$month", "$createGmt");
        AggregationPipeline pipeline = ds.createAggregation(Teacher.class).project(projection("month", secToMs))
                .group(
                        id(grouping("month")),
                        grouping("count", new Accumulator("$sum", 1)),
                        grouping("totalAge", sum("age"))
                )
                .sort(Sort.ascending("count"));
        Iterator<Group> it = pipeline.aggregate(Group.class);
        int i = 0;
        while (it.hasNext()) {
            Group g = it.next();
            System.out.println(g);
            i++;
        }
        System.out.println("document count: "+ i);
    }
    private static void batchAdd(TeacherDao dao) {
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 20; i++) {
            Teacher t = new Teacher();
            t.setAge(20 + i);
            t.setName("Lucy" + i);
            t.setSex("M");
            t.setCreateGmt(new Date());
            dao.save(t);
        }
        for (int i = 0; i < 20; i++) {
            Teacher t = new Teacher();
            t.setAge(20 + i);
            t.setName("Danny" + i);
            t.setSex("F");
            if (i < 5) {
                c.set(2016, 5, 10);
            } else if (i < 10) {
                c.set(2016, 6, 16);
            } else {
                c.set(2016, 7, 22);
            }
            t.setCreateGmt(c.getTime());
            dao.save(t);
        }
    }
}
