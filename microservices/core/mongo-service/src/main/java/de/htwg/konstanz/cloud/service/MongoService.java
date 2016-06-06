package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CheckstyleResults;
import de.htwg.konstanz.cloud.model.PMDResults;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Date;
import java.util.List;


@RestController
public class MongoService {


	@Autowired
	MongoOperations mongo;

	@Autowired
	CheckstyleResultsRepository checkstyleRepo;

	@Autowired
	PMDResultsRepository pmdRepo;
	
	@RequestMapping(value = "/addCheckstyleEntry", method = RequestMethod.POST)
	public void addCheckstyleEntry(@RequestBody CheckstyleResults checkstyleResults){
		checkstyleResults.setTimestamp(String.valueOf(new Date().getTime()));
        checkstyleRepo.save(checkstyleResults);
	}

	@RequestMapping(value ="/addPMDEntry", method = RequestMethod.POST)
	public void addPMDEntry(@RequestBody PMDResults pmdResults){
        pmdResults.setTimestamp(String.valueOf(new Date().getTime()));
		pmdRepo.save(pmdResults);
	}

	@RequestMapping(value = "/findLastCheckstyleResult", method = RequestMethod.GET)
    public @ResponseBody
	CheckstyleResults getLastCheckstyleGroupResult(@RequestParam("groupId") String groupId){

       List<CheckstyleResults> userReps =  mongo.find(Query.query(Criteria.where("groupId").is(groupId)).with(new Sort(Sort.Direction.DESC, "timestamp")).limit(1), CheckstyleResults.class);
       return userReps.get(0);
    }


    @RequestMapping(value = "/findLastPMDResult", method = RequestMethod.GET)
    public @ResponseBody
    CheckstyleResults getLastPMDGroupResult(@RequestParam("groupId") String groupId){

        List<CheckstyleResults> userReps =  mongo.find(Query.query(Criteria.where("groupId").is(groupId)).with(new Sort(Sort.Direction.DESC, "timestamp")).limit(1), CheckstyleResults.class);
        return userReps.get(0);
    }
}