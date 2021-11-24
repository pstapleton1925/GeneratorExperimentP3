package randos

import scala.io._
import scala.util.Random
import com.github.tototoshi.csv._

object RandomGenExpP3 extends App {

  //def main: Unit = {

    val random = new Random
    
    val recordTypes = List("Screeners", "Recruiters", "Qualified Lead", "Contact Attempts", "Screening", "Offers")
    
    val schemaScreeners = List("id", "first_name", "last_name")
    val schemaRecruiters = List("id", "first_name", "last_name")
    val schemaQualifiedLead = List("id", "first_name", "last_name", "university", "major", "email", "home_state")
    val schemaContactAttempts = List("recruiter_id", "ql_id", "contact_date", "start_time", "end_time", "contact_method")
    val schemaScreening = List("screener_id", "ql_id", "screening_date", "start_time", "end_time", "screening_type", "question_number", "question_accepted")
    val schemaOffers = List("screener_id", "recruiter_id", "ql_id", "offer_extended_date", "offer_action_date", "contact_method", "offer_action")
    
    val fnPath = "first_name_MOCK_DATA.csv"
    val lnPath = "last_name_MOCK_DATA.csv"
    val uPath = "university_MOCK_DATA.csv"
    val mPath = "major_MOCK_DATA.csv"
    val ePath = "email_MOCK_DATA.csv"
    val sPath = "home_state_MOCK_DATA.csv"

    def poolBuilder(filePath: String): List[String] = {

      //pulls a pool list from a CSV file
      val reader = CSVReader.open(filePath)
      val readerList = reader.all().flatten
      reader.close()
      return readerList

    }
    
    val poolFirst_name = poolBuilder(fnPath)
    val poolLast_name = poolBuilder(lnPath)
    val poolUniversity = poolBuilder(uPath)
    val poolMajor = poolBuilder(mPath)
    val poolEmails = poolBuilder(ePath)
    val poolStates = poolBuilder(sPath)
    val poolContact_method = List("Phone", "Email", "SMS") //examples from requirements doc
    val poolScreening_type = List("Spark", "Standard", "Business Analyst") //examples from requirements doc
  
    def getRandomElement(pool: List[String], random: Random): String = pool(random.nextInt(pool.length - 1))

    def generateScreener: List[String] = {

      //generates a screener using defined schema
      val newScreener = List(
      getRandomElement(poolFirst_name, random),
      getRandomElement(poolLast_name, random)
      )
      return newScreener

    }

    def generateRecruiter: List[String] = {

      //generates a recruiter using defined schema
      val newRecruiter = List(
      getRandomElement(poolFirst_name, random),
      getRandomElement(poolLast_name, random)
      )
      return newRecruiter

    }

    def generaterQualifiedLead: List[String] = {
        
      //generates a qualified lead using defined schema
      val newQualifiedLead = List(
      getRandomElement(poolFirst_name, random),
      getRandomElement(poolLast_name, random),
      getRandomElement(poolUniversity, random),
      getRandomElement(poolMajor, random), 
      getRandomElement(poolEmails, random),
      getRandomElement(poolStates, random)
      )
      return newQualifiedLead

    }

    println(s"""
Test Screener 1: ${generateScreener.mkString(" ")}
Test Screener 2: ${generateScreener.mkString(" ")}

Test Recruiter 1: ${generateRecruiter.mkString(" ")}
Test Recruiter 1: ${generateRecruiter.mkString(" ")}

Test Qualified Lead 1: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 2: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 3: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 4: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 5: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 6: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 7: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 8: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 9: ${generaterQualifiedLead.mkString(" ")}
Test Qualified Lead 10: ${generaterQualifiedLead.mkString(" ")}
      """)

  //}
}