package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.ValidationData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Future;

@RestController
@EnableAsync
public class ValidatorService {

    private static final Logger LOG = LoggerFactory.getLogger(ValidatorService.class);

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private Environment environment;

    @Autowired
    ValidateRepositoryService validateRepositoryService;

    @Autowired
    DatabaseService databaseService;

    @Autowired
    Util util;

    @Autowired
    CustomScheduler customScheduler;

    @Value("${spring.application.name}")
    private String serviceName;


    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json")
    public String info() {
        return "{\"timestamp\":\"" + new Date() + "\",\"serviceId\":\"" + serviceName + "\"}";
    }


    @RequestMapping(value = "/courses/{courseId}/validate", method = RequestMethod.POST)
    public ResponseEntity<String> validateCourse(@PathVariable String courseId) {

        try {
            String course = databaseService.getCourse(courseId);
            JSONObject jsonObj = new JSONObject(course);
            JSONArray groups = jsonObj.getJSONArray("groups");


            ArrayList<JSONObject> result = new ArrayList<>();
            // check if service runs on aws
            if (environment.getActiveProfiles()[0].equals("aws")) {
                result = customScheduler.runValidationSchedulerOnAws(groups);
            }


            return util.createResponse(result.toString(), HttpStatus.OK);

        } catch (InstantiationException e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            return util.createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "validate", nickname = "validate")
    @RequestMapping(value = "/groups/{userId}/validate", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "Success", response = String.class)
    public ResponseEntity<String> validateGroup(@PathVariable String userId) {
        try {
            String group = databaseService.getGroup(userId);
            JSONObject jsonObject = new JSONObject(group);
            LOG.info("Validate: " + jsonObject.toString());
            // start execution measurement
            ServiceInstance checkstyleInstance = loadBalancer.choose("checkstyle");
            ServiceInstance pmdInstance = loadBalancer.choose("pmd");

            long startTime = System.currentTimeMillis();
            ValidationData repositoryData = new ValidationData();
            repositoryData.setRepository(jsonObject.getString("repository"));

            Future<String> checkstyleRepo = validateRepositoryService.validateRepository(repositoryData.toString(), checkstyleInstance.getUri());
            Future<String> pmdRepo = validateRepositoryService.validateRepository(repositoryData.toString(), pmdInstance.getUri());

            if (checkstyleRepo == null || pmdRepo == null) {
                return util.createErrorResponse("Validation services not found!", HttpStatus.SERVICE_UNAVAILABLE);
            }

            boolean run = true;
            // Wait until they are done
            while (run) {
                if (checkstyleRepo.isDone() && pmdRepo.isDone()) {
                    run = false;
                }
                //10-millisecond pause between each check
                Thread.sleep(500);
            }

            JSONObject checkstyleResult = new JSONObject(checkstyleRepo.get());
            checkstyleResult.put("userId", userId);
            checkstyleResult.put("duration", (System.currentTimeMillis() - startTime));

            JSONObject pmdResult = new JSONObject(pmdRepo.get());
            pmdResult.put("userId", userId);
            pmdResult.put("duration", (System.currentTimeMillis() - startTime));

            databaseService.savePmdResult(pmdResult.toString());
            databaseService.saveCheckstleResult(checkstyleResult.toString());

            jsonObject.put("checkstyle", checkstyleResult);
            jsonObject.put("pmd", pmdResult);

            return util.createResponse(jsonObject.toString(), HttpStatus.OK);
        } catch (InstantiationException e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> validateGroupVerify(@RequestBody ValidationData data) {
        try {
            ServiceInstance checkstyleInstance = loadBalancer.choose("checkstyle");
            ServiceInstance pmdInstance = loadBalancer.choose("pmd");

            // Call validation asynchronous
            Future<String> checkstyleRepo = validateRepositoryService.validateRepository(data.toString(), checkstyleInstance.getUri());
            Future<String> pmdRepo = validateRepositoryService.validateRepository(data.toString(), pmdInstance.getUri());

            boolean run = true;
            // Wait until they are done
            while (run) {
                if (checkstyleRepo.isDone() && pmdRepo.isDone()) {
                    run = false;
                }
                //10-millisecond pause between each check
                Thread.sleep(500);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("checkstyle", checkstyleRepo.get());
            jsonObject.put("pmd", pmdRepo.get());

            return util.createResponse(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/groups/{userId}/checkstyle/last-result", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getLastCheckstyleResult(@PathVariable String userId) {
        try {
            return util.createResponse(databaseService.getLastCheckstyleResult(userId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/groups/{userId}/pmd/last-result", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getLastPmdResult(@PathVariable String userId) {
        try {
            return util.createResponse(databaseService.getLastPmdResult(userId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}