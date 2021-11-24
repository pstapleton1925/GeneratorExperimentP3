package randos

import scala.io._
import scala.util.Random
import com.github.tototoshi.csv._
import scala.collection.mutable.ArrayBuffer
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/*  
    _ need to switch from list[String] to JSON format for events/records
      thinking list[string] -> map [string, string] -> string(json format)?
    _ separate stored vals into a different file
      _ recordTypes
      _ schemas
      _ filepaths
      _ pools and pool builder?
    _ new file for kafka producer
*/

object RandomGenExpP3 extends App {

  //def main: Unit = {

    var idCounter = 0
    val random = new Random
    
    val recordTypes = List("Screeners", "Recruiters", "Qualified Lead", "Contact Attempts", "Screening", "Offers")
    
    var screenersArray = new ArrayBuffer[List[String]](0)
    var recruitersArray = new ArrayBuffer[List[String]](0)
    var qualLeadsArray = new ArrayBuffer[List[String]](0)
    var contactAttemptsArray = new ArrayBuffer[List[String]](0)
    var screeningArray = new ArrayBuffer[List[String]](0)
    var offersArray = new ArrayBuffer[List[String]](0)

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
  
    def getRandomElement(pool: List[String], random: Random): String = pool(random.nextInt(pool.length))

    def mapSchemaToValues(schema: List[String], values: List[String]): Map[String, String] = (schema zip values).toMap

    def generateScreener: List[String] = {

      idCounter += 1
      //generates a screener using defined schema
      val newScreener = List(
        idCounter.toString,
        getRandomElement(poolFirst_name, random),
        getRandomElement(poolLast_name, random)
      )
      return newScreener

    }

    def generateRecruiter: List[String] = {

      idCounter += 1
      //generates a recruiter using defined schema
      val newRecruiter = List(
        idCounter.toString,
        getRandomElement(poolFirst_name, random),
        getRandomElement(poolLast_name, random)
      )
      return newRecruiter

    }

    def assignRecruiter: String = {
      val randomRecruiter = recruitersArray(random.nextInt((recruitersArray.length)))
      val idRR = randomRecruiter(0).toString
      return idRR
    }

    def assignScreener: String = {
      val randomScreener = screenersArray(random.nextInt((screenersArray.length)))
      val idRS = randomScreener(0).toString
      return idRS
    }

    // need to update this so the email is created out of fn-ls using preconfiged email formats
    def generaterQualifiedLead: List[String] = {
        
      idCounter += 1
      //generates a qualified lead using defined schema
      val newQualifiedLead = List(
        idCounter.toString,
        getRandomElement(poolFirst_name, random),
        getRandomElement(poolLast_name, random),
        getRandomElement(poolUniversity, random),
        getRandomElement(poolMajor, random), 
        getRandomElement(poolEmails, random),
        getRandomElement(poolStates, random),
        assignRecruiter,
        assignScreener
      )
      return newQualifiedLead

    }

    def generateBatchPeople: Unit = {

      var batchArray = new ArrayBuffer[List[String]](0)

      for (i<-1 to 3) {
        val nr = generateRecruiter
        batchArray += nr
        recruitersArray += nr
      }
      for (i<-1 to 2) {
        val ns = generateScreener
        batchArray += ns
        screenersArray += ns
      }
      for (i<-1 to 30) {
        val nql = generaterQualifiedLead
        batchArray += nql
        qualLeadsArray += nql
      }

      // either in the method or another, need to send each record to kafka topic
      // maybe a separate method that combines with generateBatchActions?

      // return batchArray

    }

    generateBatchPeople

/*
    println(s"""
    assignRecruiter: $assignRecruiter
    """)
*/


    println(s"""

${recruitersArray.mkString("\n")}

${screenersArray.mkString("\n")}

${qualLeadsArray.mkString("\n")}

testing mapping...

${mapSchemaToValues(schemaRecruiters, recruitersArray(0))}
${mapSchemaToValues(schemaRecruiters, recruitersArray(1))}
${mapSchemaToValues(schemaRecruiters, recruitersArray(2))}
      """)



  //}

}