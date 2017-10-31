Feature: Assignment 05, Go Loop Translation Challenge

    Scenario: Push past upper limit
        Given the sequence of numbers "100,-100"
        When I run the Go Loop Translation Challenge
        Then I should see this output from the Go Loop Translation Challenge:
        """
         HEAL(100): LIFE 100
          DMG(100): LIFE 000
        Dead now.
        """
        
    Scenario: Straight to death
        Given the sequence of numbers "-100"
        When I run the Go Loop Translation Challenge
        Then I should see this output from the Go Loop Translation Challenge:
        """
          DMG(100): LIFE 000
        Dead now.
        """

    Scenario: Really, really dead
        Given the sequence of numbers "-10000"
        When I run the Go Loop Translation Challenge
        Then I should see this output from the Go Loop Translation Challenge:
        """
          DMG(10000): LIFE 000
        Dead now.
        """

    Scenario: Flirting with disaster
        Given the sequence of numbers "-99,1,-1,2,-2,3,-3,-1"
        When I run the Go Loop Translation Challenge
        Then I should see this output from the Go Loop Translation Challenge:
        """
          DMG(099): LIFE 001
         HEAL(001): LIFE 002
          DMG(001): LIFE 001
         HEAL(002): LIFE 003
          DMG(002): LIFE 001
         HEAL(003): LIFE 004
          DMG(003): LIFE 001
          DMG(001): LIFE 000
        Dead now.
        """

    Scenario: Yo yo
        Given the sequence of numbers "-99,200,-99,101,-99,999,-99,-1"
        When I run the Go Loop Translation Challenge
        Then I should see this output from the Go Loop Translation Challenge:
        """
          DMG(099): LIFE 001
         HEAL(200): LIFE 100
          DMG(099): LIFE 001
         HEAL(101): LIFE 100
          DMG(099): LIFE 001
         HEAL(999): LIFE 100
          DMG(099): LIFE 001
          DMG(001): LIFE 000
        Dead now.
        """