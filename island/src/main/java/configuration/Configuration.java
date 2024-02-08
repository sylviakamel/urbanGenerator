package configuration;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    public static final String OUTPUT = "o"; //-o means output file
    public static final String INPUT = "i"; // -i means input file
    public static final String MODE = "mode"; //-mode
    // public static final String CITYCOUNT = "cityCount";
    
    private CommandLine cli;
    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    // public String cityCount() {
    //     return this.cli.getOptionValue(CITYCOUNT);
    // }

    public String input() {
        return this.cli.getOptionValue(INPUT);
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.svg");
    }   
    
    public String mode() {
        return this.cli.getOptionValue(MODE);
    }   

    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }
    

    public String export(String key) { //returns the value you type. i.e. export(INPUT) returns the input file name
        return cli.getOptionValue(key);
    }

    private Options options() {
        Options options = new Options();
        // options.addOption(new Option(CITYCOUNT, true, "Number of cities (CITYCOUNT)"));
        options.addOption(new Option(INPUT, true, "Input file (MESH)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(MODE, true, "Mode type (LAGOON Y/N)")); //true = cmd line expects an arg after the key. i.e. -o test.mesh
        return options;
    }

}