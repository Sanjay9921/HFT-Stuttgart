package data_structures;

import java.util.*;

public class CustomDataStructures {
    public Stack<String> loopWhile;
    public Stack<String> loopFor;
    public HashSet<String> los;
    public HashSet<String> errBranches;
    public HashMap<String, String> variables;
    public HashSet<String> errVariables;

    public ArrayList<Integer> losGOTO;
    public ArrayList<String> pcl;
    public Map<Integer, ArrayList<String>> functions;
    public ArrayList<Integer> functionCall;
    public Map<String, String> var_global;

    public CustomDataStructures(){
        loopWhile = new Stack<String>();
        loopFor = new Stack<String>();
        los = new HashSet<String>();
        errBranches = new HashSet<String>();
        variables = new HashMap<String, String>();
        errVariables = new HashSet<String>();

        losGOTO = new ArrayList<Integer>();
        pcl = new ArrayList<String>();
        functions = new HashMap<>();
        functionCall = new ArrayList<Integer>();
        var_global = new HashMap<String, String>();
    }
}
