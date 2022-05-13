package apiAssignmentDemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ApiEndpointDemoApplication

//Used to represent the JSON object "past_experiences"
class PastExperiences (val sales: Boolean, val support: Boolean)

//Used to represent the JSON object "internet_test"
class InternetTest (val download_speed: Float, val upload_speed: Float)

//Used to represent the JSON payload input containing a Pro's personal information
data class ProInfo (
	val age: Int,
	val education_level: String,
	val past_experiences: PastExperiences,
	val internet_test: InternetTest,
	val writing_score: Float,
	val referral_code: String? = null //referral_code isn't required therefore nullable
)

//Used to represent the JSON payload output containing
//a Pro's information regarding their score and possible projects
data class ProEvaluation (
	var score: Int = 0, //Default score is zero
	var selected_project: String? = null, //selected_projected is null if no project is eligible
	var eligible_projects: MutableList<String>,
	var ineligible_projects: MutableList<String>
)

//Starts the API
fun main(args: Array<String>) {
	runApplication<ApiEndpointDemoApplication>(*args)
}

//Responsible for handling the HTTP requests
@RestController
class PayloadHandler {
	//Stores a Pro's final result once their information is processed
	var proResult = ProEvaluation(
		selected_project = "",
		ineligible_projects = mutableListOf(),
		eligible_projects = mutableListOf()
	)

	//Handles GET requests, outputting a Pro's final results
	@GetMapping
	fun output(): ProEvaluation = proResult

	//Handles POST requests, receiving a Pro's initial information
	@PostMapping
	fun input(@RequestBody proInput: ProInfo) {
		proResult = defineEligibleProjects( //Returns a Pro's final evaluation with their paired project
			defineEligibilityScore(proInput) //Defines the eligibility score of a Pro
		)
	}
}

//Takes the Pro's personal information and calculates
//an eligibility score according to rules using said information
fun defineEligibilityScore (proA: ProInfo): Int {
	if (proA.age < 18) return 0 //Pro is ineligible for any project if considered underage

	var score = 0 //Stores a Pro's final eligibility score, defaulting to zero

	//A high school education level nets 1 points to the score while
	//a bachelor's degree or higher nets 2
	if (proA.education_level == "high_school") score += 1
	else if (proA.education_level == "bachelors_degree_or_high") score += 2

	//Having experience with sales rewards 5 points and with support rewards 3
	if (proA.past_experiences.sales) score += 5
	if (proA.past_experiences.support) score += 3

	//A download speed faster than 50MB rewards a point while
	// one slower than 5MB deducts one instead
	if (proA.internet_test.download_speed > 50f) score += 1
	else if (proA.internet_test.download_speed < 5f) score -= 1

	//An upload speed faster than 50MB rewards a point while
	// one slower than 5MB deducts one instead
	if (proA.internet_test.upload_speed > 50f) score += 1
	else if (proA.internet_test.upload_speed < 5f) score -= 1

	//A point is deducted for writing scores lower than 0.3, those not lower
	//get 1 point and those higher than 0.7 get another point
	if (proA.writing_score < 0.3f) score -=1
	else if (proA.writing_score <= 0.7f) score += 1
	else score += 2

	//A valid referral code from another Pro rewards a point
	if (proA.referral_code == "token1234") score += 1

	//Returns the Pro's final eligibility score
	return score
}

//Takes a Pro's eligibility score and pairs them with the first critical project
// they're eligible for while listing which ones they're considered eligible for
// and which ones they're not
fun defineEligibleProjects (proScore: Int): ProEvaluation {
	//Stores the Pro's evaluation data, starting with no project selected
	var proA = ProEvaluation(
		proScore,
		null,
		mutableListOf(),
		mutableListOf()
	)

	//List of possible projects, sorted by their required score
	val projects = listOf(
		"Calculate the Dark Matter of the universe for Nasa",
		"Determine if the Schrodinger's cat is alive",
		"Attend to users support for a YXZ Company",
		"Collect specific people information from their social media for XPTO Company"
	)

	var bestProject = projects.size //Identifies the first critical project available

	//Decides which project to pair the Pro with based on their eligibility score
	if (proScore > 10) bestProject = 0
	else if (proScore > 5) bestProject = 1
	else if (proScore > 3) bestProject = 2
	else if (proScore > 2) bestProject = 3

	//Separates the available project based on whether the Pro is eligible for them or not
	proA.eligible_projects.addAll(projects.subList(bestProject, projects.size))
	proA.ineligible_projects.addAll(projects.subList(0, bestProject))

	//If the Pro is eligible for a project they are paired with the first critical one
	if (bestProject < projects.size) proA.selected_project = projects[bestProject]

	//Returns the Pro's final score and their situation regarding the available projects
	return proA
}

/*
Notes for next time:
spring intializr dependency used: Spring Web
gradle project
spring boot 2.6.7
jar packaging
java 18
This project uses Gradle, Kotlin and Spring Web

Ask these things:
If a Pro is underage, does their score just get set to 0 or is it a special clause?
Can underage be assumed to be <18?
Is the value "token1234" to be taken as the only valid one?
Is it necessary to check for error or can I assume the input is always correct?
Project names in algorithm and output sections are different, which one should I use?
 */