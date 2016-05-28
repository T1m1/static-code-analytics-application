package de.htwg.konstanz.cloud.service;

import javax.xml.parsers.ParserConfigurationException;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.xml.sax.SAXException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;


import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class CheckstyleService {

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity validate(@RequestBody String data) {
        try 
		{
            JSONObject jsonObj = new JSONObject(data);
            String repositoryUrl = jsonObj.getString("repositoryUrl");
            // TODO übergeben der repository Url an Methode oder im Konstruktor zur Validierung - bsp url https://github.com/T1m1/de.htwg.se.monopoly
            CheckGitRep oCheckGitRep = new CheckGitRep();
            // TODO Die Methode "oCheckGitRep" muss einen Fehler zurückliefern, damit dieser auch verarbeitet werden kann
            // TODO Wenn Methode schief läuft "InternalServerError" zurückliefern
            String json = oCheckGitRep.startIt(repositoryUrl);
            return ResponseEntity.ok(json);
        }
		catch (ParserConfigurationException e) 
		{
            e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
		catch (MalformedURLException e) 
		{
            e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
		catch (FileNotFoundException e) 
		{
            e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
		catch (SAXException e) 
		{
            e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
		catch (IOException e) 
		{
            e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
		catch (Exception e) 
		{
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping("/info")
    public String info() {
        return "Checkstyle-Service)";
    }

}
