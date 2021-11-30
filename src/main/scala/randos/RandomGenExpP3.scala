package randos

import scala.io._
import randos._
import scala.util.Random
import com.github.tototoshi.csv._
import scala.collection.mutable.ArrayBuffer
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/*  
    _ need to switch from list[String] to JSON format for events/records
      _ thinking list[string] -> Map[string, string] -> String(json format)?
      _ actually could probs do this with case classes...
    _ build kafka producer
    _ drop qual lead from arraybuffer if offer accepted or rejected
      _ maybe store dropped qual leads in new accepted and rejected arraybuffers?
    _ move a percentage of qual leads into "no response" arraybuffer 
      and drop from og arraybuffer after a certain number of contact attempts?
      _ could have extra fields that track contact attempts
        _ # of attempts
        _ response or nah
      - this is probably unneccesary
    _ conditions for screening?
    _ screenedQualLeads arraybuffer?
    _ conditions for offer?
    
*/

object RandomGenExpP3 extends App {

  //def main: Unit = {

    var idCounter = 0
    val random = new Random 
        
    var screenersArray = new ArrayBuffer[List[String]](0)
    var recruitersArray = new ArrayBuffer[List[String]](0)
    var qualLeadsArray = new ArrayBuffer[List[String]](0)
    var contactAttemptsArray = new ArrayBuffer[List[String]](0)
    var screeningArray = new ArrayBuffer[List[String]](0)
    var offersArray = new ArrayBuffer[List[String]](0)
    
    def getRandomElement(pool: List[String], random: Random): String = pool(random.nextInt(pool.length - 1))

    def mapSchemaToValues(schema: List[String], values: List[String]): Map[String, String] = (schema zip values).toMap

    def generateScreener: List[String] = {

      idCounter += 1
      
      val tmpID = idCounter.toString()
      val tmpFN = getRandomElement(RGStoredVals.poolFirst_name, random)
      val tmpLN = getRandomElement(RGStoredVals.poolLast_name, random)
      
      val newScreener = List(
        tmpID,
        tmpFN,
        tmpLN
      )

      screenersArray += newScreener
      return newScreener

    }

    def generateRecruiter: List[String] = {

      idCounter += 1

      val tmpID = idCounter.toString()
      val tmpFN = getRandomElement(RGStoredVals.poolFirst_name, random)
      val tmpLN = getRandomElement(RGStoredVals.poolLast_name, random)
      
      val newRecruiter = List(
        tmpID,
        tmpFN,
        tmpLN
      )
      
      recruitersArray += newRecruiter
      return newRecruiter

    }

    // _ need to update this so the email is created out of fn-ls using preconfiged email formats
    def generateQualifiedLead: List[String] = {
        
      idCounter += 1
      //generates a qualified lead using defined schema
      val newQualifiedLead = List(
        idCounter.toString,
        getRandomElement(RGStoredVals.poolFirst_name, random),
        getRandomElement(RGStoredVals.poolLast_name, random),
        getRandomElement(RGStoredVals.poolUniversity, random),
        getRandomElement(RGStoredVals.poolMajor, random), 
        getRandomElement(RGStoredVals.poolEmails, random),
        getRandomElement(RGStoredVals.poolStates, random),
        assignRecruiter,
        assignScreener
      )
      
      qualLeadsArray += newQualifiedLead
      return newQualifiedLead

    }    

    def assignRecruiter: String = {
      val randomRecruiter = recruitersArray(random.nextInt((recruitersArray.length - 1)))
      val idRR = randomRecruiter(0).toString
      return idRR
    }

    def assignScreener: String = {
      val randomScreener = screenersArray(random.nextInt((screenersArray.length - 1)))
      val idRS = randomScreener(0).toString
      return idRS
    }

    def assignQualLead: String = {
      val randomQualLead = qualLeadsArray(random.nextInt((qualLeadsArray.length - 1)))
      val idRQL = randomQualLead(0).toString
      return idRQL
    }

    /* 
    X move typeArray += actions into generateType methods
    _ randomize number of each type (with similar ratios?)
    */
    def generateBatchPeople: Unit = {

      var batchArray = new ArrayBuffer[List[String]](0)

      for (i<-1 to 3) {
        val nr = generateRecruiter
        batchArray += nr
      }
      for (i<-1 to 2) {
        val ns = generateScreener
        batchArray += ns
      }
      for (i<-1 to 30) {
        val nql = generateQualifiedLead
        batchArray += nql
      }

      // return batchArray

    }

    // WIP
    def generateContactAttempt: List[String] = {

      /* 
      X need to first pick a random ql 
        _ (meeting conditions?)
      X then pull their assigned recruiter
      _ need method(s) for dates, start_time, end_time
      X need to pull from contact method pool
      _ maybe need to expand contact method pool?
      */

      idCounter += 1

      val tmpID = idCounter.toString()
      val tmpQualLead = assignQualLead
      val tmpAssignedRecruiter = findAssignedRecruiter(tmpQualLead)
      val tmpDate = generateDate
      val tmpStart = "generateStart"
      val tmpEnd = "generateEnd(tmpStart)"
      val tmpCM = getRandomElement(RGStoredVals.poolContact_method, random)      
      
      val newContactAttempt = List(
        tmpID,
        tmpAssignedRecruiter,
        tmpQualLead,
        tmpDate,
        tmpStart,
        tmpEnd,
        tmpCM
      )

      contactAttemptsArray += newContactAttempt 
      return newContactAttempt

    }

    // WIP
    def generateScreening: List[String] = {

      idCounter += 1

      val tmpID = idCounter.toString()
      val tmpAssignedScreener = "screener_id"
      val tmpQualLead =  "ql_id"
      val tmpDate =  "screening_date"
      val tmpStart =  "start_time"
      val tmpEnd = "end_time"
      val tmpType =  "screening_type"
      val tmpQTotal =  "question_number"
      val tmpQAccepted =  "question_accepted"
      
      val newScreening = List(
        tmpID,
        tmpAssignedScreener,
        tmpQualLead,
        tmpDate,
        tmpStart,
        tmpEnd,
        tmpType,
        tmpQTotal,
        tmpQAccepted
      )

      screeningArray += newScreening
      return newScreening

    }

    // WIP
    def generateOffer: List[String] = {

      idCounter += 1

      val tmpID = idCounter.toString()
      val tmpAssignedScreener = "screener_id"
      val tmpAssignedRecruiter = "recruiter_id"
      val tmpQualLead = "ql_id"
      val tmpDateExtended = "offer_extended_date"
      val tmpDateAction = "offer_action_date"
      val tmpCM = "contact_method"
      val tmpAction = "offer_action"

      val newOffer = List(
        tmpID,
        tmpAssignedScreener,
        tmpAssignedScreener,
        tmpQualLead,
        tmpDateExtended,
        tmpDateAction,
        tmpCM,
        tmpAction
      )

      offersArray += newOffer
      return newOffer

    }

    def findAssignedRecruiter(qualLead: String): String = {      
      val qlRecord = qualLeadsArray(qualLead.toInt)
      // this currently pulls a specified element, but it might be better to have the generators make maps and find by key
      val qlar = qlRecord(8)
      return qlar
    }
    
    def generateDate: String = {
      val test = "12/12/12"
      return test
    }

    // WIP
    def generateBatchActions: Unit = {
      
      // this is where we generate contact attempts, screenings, and offers

    }

    println("""
generating a batch...
  """)

    generateBatchPeople

    println(s"""

testing assignRecruiter...
assigned recruiter id: $assignRecruiter

${recruitersArray.mkString("\n")}

${screenersArray.mkString("\n")}

${qualLeadsArray.mkString("\n")}

testing mapping...

${mapSchemaToValues(RGStoredVals.schemaRecruiters, recruitersArray(0))}
${mapSchemaToValues(RGStoredVals.schemaRecruiters, recruitersArray(1))}
${mapSchemaToValues(RGStoredVals.schemaRecruiters, recruitersArray(2))}

testing actions...

${generateContactAttempt.mkString(" ")}

${generateScreening.mkString(" ")}

${generateOffer.mkString(" ")}
      """)

  //}

}