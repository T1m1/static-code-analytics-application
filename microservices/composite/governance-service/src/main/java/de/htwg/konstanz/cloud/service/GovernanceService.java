package de.htwg.konstanz.cloud.service;


import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.htwg.konstanz.cloud.model.Courses;
import de.htwg.konstanz.cloud.model.MoodleCourse;
import de.htwg.konstanz.cloud.model.MoodleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class GovernanceService {


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        return "governance";
    }

    @Autowired
    DatabaseService databaseService;

    @Autowired
    MoodleService moodleService;

    @RequestMapping(value = "/courses", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getCouses() {
        try {
            return createResponse(databaseService.getAllCourses(), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getGroups() {
        try {
            // TODO alle Gruppen (Nur mit Zusammenfassung)
            return createResponse(databaseService.getAllGroups(), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/groups/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getGroup(@PathVariable String groupId) {
        try {

            // TODO mit letztem Ergebnis, wenn vorhanden
            return createResponse(databaseService.getGroupWithId(groupId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/courses/{courseId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getCourse(@PathVariable String courseId) {
        try {
            return createResponse(databaseService.getCourseWithId(courseId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> getToken(@RequestBody MoodleCredentials credentials) {

        try {
            return createResponse(moodleService.getToken(credentials), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/import/{token}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> importCourses(@PathVariable String token) {
        try {

            // get the list of all courses
            String courses = moodleService.getCourses(token);

            return createResponse(courses, HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/import/courses/{token}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> importCoursesOfProf(@PathVariable String token, @RequestBody Courses courses) {
        try {


            JSONObject user = new JSONObject(moodleService.getUserInformation(token));

            if (user.isNull("userid")) {
                return createErrorResponse("No User information found for token: " + token, HttpStatus.BAD_REQUEST);
            }

            databaseService.saveUser(user);


            Integer userId = (Integer) user.get("userid");

            JSONObject response = new JSONObject();

            // iterate over courses and save them
            for (MoodleCourse course : courses.getCourses()) {

                JSONObject jsonCourse = new JSONObject(course);

                if (!moodleService.hasPermission(userId, course.getId(), token)) {
                    System.out.println("User " + user.get("lastname") + " has no permission for course: " + course.getFullname());
                    response.append("notImported", course.getFullname());
                    continue;
                }

                // first save course
                databaseService.saveCourse(userId, jsonCourse);

                // then get submissions of course
                String groups = moodleService.getSubmissionsOfCourses(course.getId(), token);

                if ("[]".equals(groups)) {
                    continue;
                }

                // finally save submissions
                databaseService.saveGroups(course.getId(), groups);
                response.append("imported", course.getFullname());

            }

            response.put("ok", true);
            return createResponse(response.toString(), HttpStatus.OK);

        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (JSONException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @HystrixCommand(fallbackMethod = "getDefaultMoodleStuff")
    private Object getMoodleStuff() {
        return null;
    }

    private Object getDefaultMoodleStuff() {
        return null;
    }


    <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }

    ResponseEntity<String> createErrorResponse(String errorMessage, HttpStatus status) {
        HashMap<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);
        JSONObject errorResponseObject = new JSONObject(errorResponse);
        return createResponse(errorResponseObject.toString(), status);
    }
}
