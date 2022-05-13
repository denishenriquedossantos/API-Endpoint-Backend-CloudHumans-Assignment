package apiAssignmentDemo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

//Tests for the defineEligibilityScore function
internal class DefineEligibilityScoreKtTest {

    //Average case test, netting some points with no deductions:
    //+1 for education, download speed and writing score
    //+3 for support experience
    @Test
    fun defineEligibilityScoreTestAverage() {
        assertEquals(6, defineEligibilityScore(ProInfo(
            20,
            "high_school",
            PastExperiences(sales = false, support = true),
            InternetTest(75f, 22.4f),
            0.7f
        )))
    }

    //Test for underage case
    //Expected a score of zero for sure rejection
    @Test
    fun defineEligibilityScoreTestUnderage() {
        assertEquals(0, defineEligibilityScore(ProInfo(
            17,
            "high_school",
            PastExperiences(sales = false, support = false),
            InternetTest(100f, 80f),
            0.6f,
            "token1928"
        )))
    }

    //Test for best possible case, maximizing every field
    @Test
    fun defineEligibilityScoreTestBest() {
        assertEquals(15, defineEligibilityScore(ProInfo(
            43,
            "bachelors_degree_or_high",
            PastExperiences(sales = true, support = true),
            InternetTest(150f, 120f),
            1f,
            "token1234"
        )))
    }

    //Test for negative score result
    @Test
    fun defineEligibilityScoreTestNegative() {
        assertEquals(-1, defineEligibilityScore(ProInfo(
            25,
            "high_school",
            PastExperiences(sales = false, support = false),
            InternetTest(8f, 4f),
            0.25f
        )))
    }
}

//Tests for the defineEligibleProjects function
internal class DefineEligibleProjectsKtTest {
    private var projectsList = mutableListOf(
        "Calculate the Dark Matter of the universe for Nasa",
        "Determine if the Schrodinger's cat is alive",
        "Attend to users support for a YXZ Company",
        "Collect specific people information from their social media for XPTO Company"
    )

    //Test for no eligible project
    @Test
    fun defineEligibleProjectsTestRejected() {
        assertEquals(ProEvaluation(
            1,
            null,
            mutableListOf(),
            projectsList
        ), defineEligibleProjects(1))
    }

    //Test for whether the eligible and ineligible projects lists
    //successfully store a certain project
    @Test
    fun defineEligibleProjectsTestContainsCat() {
        assertTrue(defineEligibleProjects(7).eligible_projects.contains(
            "Determine if the Schrodinger's cat is alive"
        ))
        assertFalse(defineEligibleProjects(4).eligible_projects.contains(
            "Determine if the Schrodinger's cat is alive"
        ))
        assertFalse(defineEligibleProjects(10).ineligible_projects.contains(
            "Determine if the Schrodinger's cat is alive"
        ))
    }
}