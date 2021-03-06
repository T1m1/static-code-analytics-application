package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Duplication;
import de.htwg.konstanz.cloud.util.Git;
import de.htwg.konstanz.cloud.util.OperatingSystemCheck;
import de.htwg.konstanz.cloud.util.Svn;
import de.htwg.konstanz.cloud.util.Util;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.text.BadLocationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Cpd {
    private static final Logger LOG = LoggerFactory.getLogger(Cpd.class);

    private final List<Duplication> lDuplications = new ArrayList<>();

    private final String svnServerIp;

    private File oRepoDir;

    private final Util oUtil = new Util();

    private final Git oGit = new Git();

    private final Svn oSvn = new Svn();

    private String sFileSeparator = "";

    /**
     * Constructor to initialize the Svn server ip for Cpd.
     * It is stored in the config file
     * @param svnServerIp - SVN Server ip address
     */
    public Cpd(String svnServerIp) {
        this.svnServerIp = svnServerIp;
    }

    /**
     * Connection point for incoming post request
     * @param gitRepository - given git repository from post request
     * @return - Problems or Valid Json
     * @throws IOException - throw for the handling in CpdService
     * @throws ParserConfigurationException - throw for the handling in CpdService
     * @throws SAXException - throw for the handling in CpdService
     * @throws BadLocationException - throw for the handling in CpdService
     * @throws GitAPIException - throw for the handling in CpdService
     * @throws NullPointerException - throw for the handling in CpdService
     * @throws InterruptedException - throw for the handling in CpdService
     */
    String startIt(List<String> gitRepository) throws IOException, ParserConfigurationException,
            SAXException, BadLocationException, GitAPIException, NullPointerException, InterruptedException {
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult;

        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        sFileSeparator = oOperatingSystemCheck.getOperatingSystemSeparator();

        oJsonResult = determineVersionControlSystem(gitRepository, lStartTime);
        if (oRepoDir == null) {
            LOG.info("Error: Local Directory is null!");
        } else {
            FileUtils.deleteDirectory(oRepoDir);
        }

        return oUtil.checkJsonResult(oJsonResult);
    }

    /**
     * checks if the given list of repositories belongs to git or svn. After the checkout of the repository
     * this method executes the analysis of Cpd
     * @param sRepoUrl - within POST request given list of repositories url (svn or git)
     * @param lStartTime - start time of the service execution
     * @return  - generated JSON file with all duplication who figured out in Cpd
     * @throws IOException - throw for the handling in CpdService
     * @throws BadLocationException - throw for the handling in CpdService
     * @throws GitAPIException - throw for the handling in CpdService
     * @throws ParserConfigurationException - throw for the handling in CpdService
     * @throws SAXException - throw for the handling in CpdService
     * @throws InterruptedException - throw for the handling in CpdService
     */
    private JSONObject determineVersionControlSystem(List<String> sRepoUrl, long lStartTime)
            throws IOException, BadLocationException, GitAPIException, ParserConfigurationException,
                                                                    SAXException, InterruptedException {
        JSONObject oJson = null;
        String sLocalDir;
        String[] sLocalDirArray;
        List<String> lRepoDirs = new ArrayList<>();
        StringBuilder oStringBuilder;

        LOG.info("Repository URL: " + sRepoUrl);
        oUtil.checkLocalPmd();
        oRepoDir = oUtil.createDirectory("repositories" + sFileSeparator + "cpd-repositories_" + System.currentTimeMillis());

        /* Download SVN or Git Repos */
        for (String sRepo : sRepoUrl) {


            oStringBuilder = new StringBuilder();
            /* Svn Checkout */
            if (sRepo.contains(svnServerIp)) {
                /* URL needs to start with HTTP:// */
                if (!sRepo.startsWith("http://")) {
                    oStringBuilder.append("http://");
                }
                /* remove the last / */
                if (sRepo.endsWith("/")) {
                    oStringBuilder.append(sRepo.substring(0, sRepo.length() - 1));
                } else {
                    oStringBuilder.append(sRepo);
                }
                LOG.info("SVN " + oStringBuilder.toString());
                sLocalDir = oSvn.downloadSvnRepo(oStringBuilder.toString(), oRepoDir.getAbsolutePath());
                lRepoDirs.add(sLocalDir);
            }
            /* Git */
            else if (sRepo.contains("github.com")) {
                LOG.info("Git " + sRepo);
                sLocalDirArray = oGit.downloadGitRepo(sRepo, oRepoDir.getPath());
                lRepoDirs.add(sLocalDirArray[0]);
            } else {
                LOG.info("Repository URL has no valid Svn/Git attributes. (" + sRepoUrl + ")");
            }

        }

        /* Run CPD */
        if (!lRepoDirs.isEmpty()) {
            oJson = runCpd(oRepoDir.getAbsolutePath(), lStartTime);
        }

        return oJson;
    }

    /**
     * Create Cpd output file in XML format. Depends on OS, which Command Line will be use.
     * @param sRepoString - String of Directory where all downloaded Repositories are stored
     * @param lStartTime - start time of the service execution
     * @return Json Object with all dupliactions found with Cpd
     * @throws IOException - throw for the handling in CpdService
     * @throws ParserConfigurationException - throw for the handling in CpdService
     * @throws SAXException - throw for the handling in CpdService
     * @throws InterruptedException - throw for the handling in CpdService
     */
    private JSONObject runCpd(String sRepoString, long lStartTime) throws ParserConfigurationException,
            SAXException, IOException, InterruptedException {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        final String sOutputFileName = "Duplications.xml";
        String sStartScript;
        String sMainPath = sRepoString + sFileSeparator + "repositories-cpd" + System.currentTimeMillis();
        JSONObject oJson = null;

        /* Check if Dir exists and if needed create new*/
        oUtil.createDirectory(sMainPath);

        if (oOperatingSystemCheck.isWindows()) {
            sStartScript = "pmd-bin-5.4.2\\bin\\cpd.bat";
            String sCpdCommand = sStartScript + " --minimum-tokens 75 --files " + oRepoDir.getAbsolutePath()
                    + " --skip-lexical-errors --format xml > " + sMainPath + sFileSeparator + "CpdCheck_" + sOutputFileName;
            LOG.info("Command: " + sCpdCommand);
            int nReturnCode = oUtil.execCommand(sCpdCommand);
            LOG.info("Process Return Code: " + nReturnCode);
        } else if (oOperatingSystemCheck.isLinux()) {
            ArrayList<String> sProcessBuilder = new ArrayList<>();

            sStartScript = "pmd-bin-5.4.2/bin/run.sh";
            sProcessBuilder.add(sStartScript);
            sProcessBuilder.add("cpd");
            sProcessBuilder.add("--minimum-tokens");
            sProcessBuilder.add("75");
            sProcessBuilder.add("--files");
            sProcessBuilder.add(oRepoDir.getAbsolutePath());
            sProcessBuilder.add("--skip-lexical-errors");
            sProcessBuilder.add("--format");
            sProcessBuilder.add("xml");

            ProcessBuilder oCommandExecure = new ProcessBuilder(sProcessBuilder);
            oCommandExecure.redirectOutput(new File(sMainPath + sFileSeparator + "CpdCheck_" + sOutputFileName));
            Process p = oCommandExecure.start();
            int nReturnCode = p.waitFor();
            LOG.info("Process Return Code: " + nReturnCode);
        }

        /* Cpd Informationen eintragen */
        storeCpdInformation(sMainPath + sFileSeparator + "CpdCheck_" + sOutputFileName, sRepoString);

        if (!lDuplications.isEmpty()) {
            /* Schoene einheitliche JSON erstellen */
            oJson = buildJson(sMainPath, lStartTime);
            /* JSON an Database weitersenden */
        }

        return oJson;
    }

    /**
     * Uses the XML output file from Cpd to create dupliaction in form of duplication model
     * and add them to a List
     * @param sXmlPath - String of XML file path which contains Cpd duplications results
     * @param sMainPath - String of Directory where all downloaded Repositories are stored
     * @throws IOException - throw for the handling in CpdService
     * @throws ParserConfigurationException - throw for the handling in CpdService
     * @throws SAXException - throw for the handling in CpdService
     */
    private void storeCpdInformation(String sXmlPath, String sMainPath)
            throws ParserConfigurationException, SAXException, IOException {
        InputStream oInputStream = new FileInputStream(sXmlPath);
        Reader oReader = new InputStreamReader(oInputStream, "UTF-8");
        InputSource oInputSource = new InputSource(oReader);
        oInputSource.setEncoding("UTF-8");

        DocumentBuilderFactory oDbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder oDBuilder = oDbFactory.newDocumentBuilder();
        Document oDocument = oDBuilder.parse(oInputSource);

        Node oMainNode = oDocument.getFirstChild();
        Element eMainElement = (Element) oMainNode;

        NodeList oNodeList = eMainElement.getElementsByTagName("duplication");
        for (int nNodePos = 0; nNodePos < oNodeList.getLength(); nNodePos++) {

            Node oNode = oNodeList.item(nNodePos);

            if (oNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eNodeElement = (Element) oNode;

				/* Default Values */
                List<String> lInvolvedData = new ArrayList<>();
                String sCodeFragment = "";

                //Duplication Infos
                int nLinesCount = oUtil.getParsableElement(eNodeElement, "lines");
                int nTokens = oUtil.getParsableElement(eNodeElement, "tokens");

                //CheckFileNodes
                NodeList nNodeFiles = eNodeElement.getElementsByTagName("file");
                for (int nNodeFilePos = 0; nNodeFilePos < nNodeFiles.getLength(); nNodeFilePos++) {
                    Node nNodeFile = nNodeFiles.item(nNodeFilePos);
                    Element eNodeFileElement = (Element) nNodeFile;
                    String sRepoString = String.valueOf(eNodeFileElement.getAttribute("path"))
                            .substring(String.valueOf(eNodeFileElement.getAttribute("path"))
                                    .indexOf(sMainPath) + (sMainPath).length() + 1);
                    if (oUtil.checkIfDifferentReops(lInvolvedData, sRepoString)) {
                        lInvolvedData.add(sRepoString);
                    }
                }

                //CheckCodefragment
                NodeList nNodeCodeFragment = eNodeElement.getElementsByTagName("codefragment");
                if (nNodeCodeFragment != null) {
                    sCodeFragment = nNodeCodeFragment.item(0).getTextContent();
                }

                //Create Duplication
                if (lInvolvedData.size() > 1) {
                    lDuplications.add(Duplication.getDupliactionInstance(nLinesCount, nTokens, lInvolvedData, sCodeFragment));
                }
            }
        }
    }

    /**
     * Create the Json with the stored data in the duplication list
     * @param sMainDir - String of Directory where all downloaded Repositories are stored
     * @param lStartTime - start time of the service execution
     * @return - JsonObject with stored Information in the duplication list
     */
    private JSONObject buildJson(String sMainDir, long lStartTime) {
        JSONObject oJsonRoot = new JSONObject();
        int nDuplicationCounter = 0;

		/* add general information to the JSON object */
        oJsonRoot.put("duplicationCursPath", sMainDir);

		/* all Classes */
        JSONArray lJsonDuplications = new JSONArray();
        for (Duplication oDuplication : lDuplications) {
            JSONObject oJsonDuplication = new JSONObject();

			/* Duplication Infos*/
            oJsonDuplication.put("duplicatedLines", oDuplication.getDuplicatedLine());
            oJsonDuplication.put("tokens", oDuplication.getTokens());

            //New Json Array with involved Paths
            JSONArray lJsonFilePaths = new JSONArray();
            for (String sDuplicationFilePath : oDuplication.getInvolvedData()) {
                lJsonFilePaths.put(new JSONObject().put("filePath", sDuplicationFilePath));
            }
            oJsonDuplication.put("filePaths", lJsonFilePaths);
            oJsonDuplication.put("codefragment", oDuplication.getDuplicatedCode());
            lJsonDuplications.put(new JSONObject().put("duplication", oJsonDuplication));
            nDuplicationCounter++;
        }
        oJsonRoot.put("duplications", lJsonDuplications);

        long lEndTime = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oJsonRoot.put("numberOfDuplications", nDuplicationCounter);
        oJsonRoot.put("totalExpendedTime", lTotalTime);
        oJsonRoot.put("assignments", "Copy Paste Check for " + sMainDir);

        LOG.debug("Code Cuplication Analysis check for: " + sMainDir);
        return oJsonRoot;
    }
}
