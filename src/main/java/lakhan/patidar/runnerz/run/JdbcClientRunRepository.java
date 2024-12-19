package lakhan.patidar.runnerz.run;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import jakarta.annotation.PostConstruct;
import lakhan.patidar.runnerz.RunnerzApplication;

@Repository
public class JdbcClientRunRepository {

    public static final Logger log = LoggerFactory.getLogger(RunnerzApplication.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll(){
        return jdbcClient.sql("select * from run")
            .query(Run.class)
            .list();
    }
     // FOR IN MEMORY DATA

    // private List<Run> runs = new ArrayList<>();

    // List<Run> findAll(){
    //     return runs;
    // }

    // Optional<Run> findById(Integer Id){
    //     return runs.stream()
    //     .filter(run -> run.id()==Id)
    //     .findFirst();
    // }


    public void create (Run run){
        var updated = jdbcClient.sql("INSERT INTO RUN(id, title, started_on, completed_on, miles, location) VALUES (?,?,?,?,?,?)")
                .params(List.of(run.id(),run.title(),run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();
        Assert.state(updated==1, "Failed to create run "+run.title());
    }



    public int count(){
        return jdbcClient.sql("select * from RUN").query().listOfRows().size();
    }


    public void saveAll(List<Run> runs){
        runs.stream().forEach(this::create);
    }
    // void update(Run run, Integer id){
    //     Optional<Run> existingRun = findById(id);
    //     if(existingRun.isPresent()){
    //         runs.set(runs.indexOf(existingRun.get()), run);
    //     }
    // }

    // void delete(Integer id){
    //     runs.removeIf(run -> run.id().equals(id));
    // }

    // @PostConstruct
    // private void init(){
    //     runs.add(new Run( 1, "First Runner", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS),5, Location.OUTDOOR));
    //     runs.add(new Run( 2, "First Runner", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS),5, Location.OUTDOOR));
    // }


}
