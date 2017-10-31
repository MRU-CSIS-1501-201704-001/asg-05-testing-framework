Feature: Assignment 05, Gender Bias Challenge

    Scenario: Desired output from input without errors
        When I run the Gender Bias Challenge
        Then I should see a report that is **close** to this:
            """
            Run#  M : F
               1  1 : 1.00000
               2  1 : 1.00000
               3  1 : 1.00000
               4  1 : 1.00000
               5  1 : 1.00000
               6  1 : 1.00000
               7  1 : 1.00000
               8  1 : 1.00000
               9  1 : 1.00000
              10  1 : 1.00000
            """ 
