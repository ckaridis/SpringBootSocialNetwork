package com.christos.test;

import com.christos.App;
import com.christos.model.StatusUpdate;
import com.christos.model.StatusUpdateDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// This test case is going to run with the Spring J Unit 4 Class
@RunWith(SpringJUnit4ClassRunner.class)
// Import the configuration of my main class, App.class
@SpringBootTest(classes = App.class)
// We tell the tester to use the same configuration as our web app
@WebAppConfiguration
// This means that after the test finishes, our DataBase will roll back to it's previous condition.
// Any fields or tables created will be automatically deleted after the test has finished.
@Transactional
public class StatusTest {

    // By setting this to Autowired, Spring will try to find a matching object and automatically create
    // the bin for us. It's not totally clear how it works, but it works.
    // We do not instantiate it. It's all on Spring.
    @Autowired
    private StatusUpdateDao statusUpdateDao;

    @Test
    public void testSave() {
        StatusUpdate status = new StatusUpdate("This is a test status update");

        // This is going to save the Status to the database.
        statusUpdateDao.save(status);

        assertNotNull("Non Null ID", status.getId());
        assertNotNull("Non Null Date", status.getAdded());

        // We create a new status update and we give it the value of the status update stored in our DAO
        // based on the ID of our status object.
        StatusUpdate retrieved = statusUpdateDao.findOne(status.getId());

        // This line of code compares the two objects (status, retrieved) and posts a message on the console
        // if those objects have the same value.
        assertEquals("Matching StatusUpdate", status, retrieved);

    }

    // In this test we create a loop which adds a new day on each loop (1 day per loop). Then, we save
    // all the status updates on the DAO.
    // We also got an object "lastStatusUpdate" to compare the latest status from our DAO with the latest
    // status on the loop, to check if it's the same (if it's working).
    @Test
    public void testFindLatest() {
        Calendar calendar = Calendar.getInstance();
        StatusUpdate lastStatusUpdate = null;
        for (int i = 0; i < 10; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            StatusUpdate status = new StatusUpdate("Status Update" + i, calendar.getTime());
            statusUpdateDao.save(status);
            lastStatusUpdate = status;
        }
        StatusUpdate retrieved = statusUpdateDao.findFirstByOrderByAddedDesc();
        assertEquals("Latest status update ", lastStatusUpdate, retrieved);
    }

}
