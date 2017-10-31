Feature: Assignment 05, Gpa Chart Challenge

    Scenario: No students
        Given the user inputs "0"
        When I run the Gpa Chart Challenge
        Then the program should stop
        
    Scenario: Negative students
        Given the user inputs "-1"
        When I run the Gpa Chart Challenge
        Then the program should stop

    Scenario: One student, Honours low end
        Given the user inputs "1,3.6"
        When I run the Gpa Chart Challenge
        Then I should see this output from the Gpa Chart Challenge:
        """
        GPA Distribution

        Honours   :*
        Pass      :
        Fail      :
        """

    Scenario: One student, Honours high end
        Given the user inputs "1,4.0"
        When I run the Gpa Chart Challenge
        Then I should see this output from the Gpa Chart Challenge:
        """
        GPA Distribution

        Honours   :*
        Pass      :
        Fail      :
        """
        


    Scenario: One student, Fail low end
        Given the user inputs "1,0.0"
        When I run the Gpa Chart Challenge
        Then I should see this output from the Gpa Chart Challenge:
        """
        GPA Distribution

        Honours   :
        Pass      :
        Fail      :*
        """
    Scenario: One student, Fail high end
        Given the user inputs "1,1.9"
        When I run the Gpa Chart Challenge
        Then I should see this output from the Gpa Chart Challenge:
        """
        GPA Distribution

        Honours   :
        Pass      :
        Fail      :*
        """

        Scenario: Two students, Honours and Fail
        Given the user inputs "2,3.8,1.9"
        When I run the Gpa Chart Challenge
        Then I should see this output from the Gpa Chart Challenge:
        """
        GPA Distribution

        Honours   :*
        Pass      :
        Fail      :*
        """

        Scenario: Two students, Fail and Honours
        Given the user inputs "2,1.7,3.9"
        When I run the Gpa Chart Challenge
        Then I should see this output from the Gpa Chart Challenge:
        """
        GPA Distribution

        Honours   :*
        Pass      :
        Fail      :*
        """
