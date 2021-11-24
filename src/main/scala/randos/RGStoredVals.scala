package randos

import scala.io._
import scala.util.Random
import com.github.tototoshi.csv._
import scala.collection.mutable.ArrayBuffer
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object RGStoredVals {

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
  
}
