package step_definitions;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.io.File;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.regex.Matcher;

import cucumber.api.java.en.*;
import cucumber.api.java.Before;
import cucumber.api.java.After;
import cucumber.api.Scenario;
import cucumber.api.PendingException;

import java.util.regex.Pattern;


public class GenderBiasChallengeSteps {
	
    private LinkedList<String> inputQueue = new LinkedList<>();
    private String[] outputLines;
    private int nextLine;


    @Before
    public void beforeCallingScenario() {
    }

	
    @After
    public void afterRunningScenario(Scenario scenario) {
        this.inputQueue.clear();
        this.outputLines = null;
        this.nextLine = -1;
    }

    public void provideKeyboardInput() {
        String queuedInput = "";
        for (String s : inputQueue) {
            queuedInput += String.format("%s%n",s);
        }
        System.setIn(new ByteArrayInputStream(queuedInput.getBytes()));
    }
    
    @When("^I run the Gender Bias Challenge$")
    public void iRunTheGenderBiasChallenge() throws Throwable {
        ByteArrayOutputStream outContent = null;
        PrintStream testSystemOut = null;
        try {
            outContent = new ByteArrayOutputStream();
            testSystemOut = new PrintStream(outContent, true, "UTF-8");

            PrintStream originalSystemOut = System.out;
            try {
                System.setOut(testSystemOut);

                provideKeyboardInput();
                Class.forName("ProblemSetMain").getMethod("runGenderBiasChallenge").invoke(null);
            } finally {
                System.setOut(originalSystemOut);
            }

            testSystemOut.flush();
            outputLines = outContent.toString("UTF-8").split("\\R+");
            nextLine = 0;
        } finally {
            testSystemOut.close();
            outContent.close();
        }
    }

    @Then("^I should see this report:$")
    public void iShouldSeeThisReport(String report) throws Throwable {
       // assertThat(OUT_CONTENT.toString("UTF-8")).isEqualTo(report);
    }      

    @Then("^I should see a report that is \\*\\*close\\*\\* to this:$")
    public void iShouldSeeAReportThatIsCloseToThis(String expectedReport) throws Throwable {
        assertGenderReportOutputIsClose(expectedReport);
    }
    
    public void assertGenderReportOutputIsClose(String expectedReport) throws Throwable {
        String[] expectedChunked = expectedReport.split("\\R");
        int expectedOutputLength = expectedChunked.length;
        int actualOutputLength = outputLines.length;

        if (actualOutputLength != expectedOutputLength) {
            throw new AssertionError("Expected output to be 11 lines long, but it wasn't");
        }

        assertRemainingOutputContainsFragment("Run#  M : F");

        for (int i = 1; i < expectedOutputLength; i++) {

            String expectedLine = expectedChunked[i];
            String actualLine = outputLines[i];

            int colonLocationInExpected = expectedLine.indexOf(":");
            int colonLocationInActual = actualLine.indexOf(":");

            String preColonPartExpected = expectedLine.substring(0, colonLocationInExpected);
            String preColonPartActual = actualLine.substring(0, colonLocationInActual);

            if (!preColonPartActual.equals(preColonPartExpected)) {
                String msg = String.format("%nLine %02d is %n%s: (female val)%n but expected %n%s: (female val)%n", i, preColonPartActual, preColonPartExpected);
                throw new AssertionError(msg);
            }

            String postColonPartActual = actualLine.substring(colonLocationInActual + 1).trim();

            if (postColonPartActual.length() != 7) {
                String msg = String.format("%nFemale portion %s does not seem to be formatted to 5 decimal places%n", postColonPartActual);
                throw new AssertionError(msg);
            }

            double postColonPartActualAsDouble = Double.parseDouble(postColonPartActual);

            if (Math.abs(postColonPartActualAsDouble - 1.0) > 0.1) {
                String msg = String.format("%nLine %02d has a female value of %n%s%n but expected something close to %n%s%n", i, postColonPartActual, "1.00000");
                throw new AssertionError(msg);
            }
        }
    }


    public void assertRemainingOutputIsEmpty() throws Throwable {
        if (this.nextLine != this.outputLines.length) {
            throw new AssertionError("Expected no further output, but there was.");
        }
    }
    

    public void assertRemainingOutputContainsFragment(String word) throws Throwable {
        this.assertRemainingOutputContains(word, "\"" + word + "\"");
    }

    public void assertRemainingOutputContainsWord(String word) throws Throwable {
        this.assertRemainingOutputContains("\\b" + word + "\\b", "word \"" + word + "\"");
    }

    public void assertRemainingOutputContains(String regex, String niceName) throws Throwable {

        int lineToCheck = this.nextLine;
        boolean found = false;

        System.err.println(regex);

        regex = regex.replaceAll("[-.\\+*?\\[^\\]$(){}=!<>|:\\\\]", "\\\\$0");
        while (!found && lineToCheck < this.outputLines.length) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(this.outputLines[lineToCheck]);
            found = m.find();

            lineToCheck++;
        }

        if (found) {
            this.nextLine = lineToCheck;
        } else {
            throw new AssertionError("Could not find " + niceName + " in remaining lines:\n" +
                String.join("\n", Arrays.copyOfRange(this.outputLines, this.nextLine, this.outputLines.length)) +
                (this.nextLine > 0 ? "\nPrevious line was:\n" + this.outputLines[this.nextLine - 1] : ""));
        }
    }

    public void assertRemainingOutputMissingWord(String word) throws Throwable {
        this.assertRemainingOutputMissing("\\b" + word + "\\b", word);
    }

    public void assertRemainingOutputMissing(String regex, String niceName) throws Throwable {
        int lineToCheck = this.nextLine;
        boolean found = false;

        regex = regex.replaceAll("[-.\\+*?\\[^\\]$(){}=!<>|:\\\\]", "\\\\$0");
        while (!found && lineToCheck < this.outputLines.length) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(this.outputLines[lineToCheck]);
            found = m.find();

            lineToCheck++;
        }

        if (found) {
            throw new AssertionError("Did not expect to find '" + regex + "', but found it in the line:\n" +
                String.join("\n", this.outputLines[lineToCheck - 1]));
        }
    }                                                                                                                     
}
