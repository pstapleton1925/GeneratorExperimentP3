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
      thinking list[string] -> map [string, string] -> string(json format)?
    _ build kafka producer
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
    
    def getRandomElement(pool: List[String], random: Random): String = pool(random.nextInt(pool.length))

    def mapSchemaToValues(schema: List[String], values: List[String]): Map[String, String] = (schema zip values).toMap

    def generateScreener: List[String] = {

      idCounter += 1
      //generates a screener using defined schema
      val newScreener = List(
        idCounter.toString,
        getRandomElement(RGStoredVals.poolFirst_name, random),
        getRandomElement(RGStoredVals.poolLast_name, random)
      )
      return newScreener

    }

    def generateRecruiter: List[String] = {

      idCounter += 1
      //generates a recruiter using defined schema
      val newRecruiter = List(
        idCounter.toString,
        getRandomElement(RGStoredVals.poolFirst_name, random),
        getRandomElement(RGStoredVals.poolLast_name, random)
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
        getRandomElement(RGStoredVals.poolFirst_name, random),
        getRandomElement(RGStoredVals.poolLast_name, random),
        getRandomElement(RGStoredVals.poolUniversity, random),
        getRandomElement(RGStoredVals.poolMajor, random), 
        getRandomElement(RGStoredVals.poolEmails, random),
        getRandomElement(RGStoredVals.poolStates, random),
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

${mapSchemaToValues(RGStoredVals.schemaRecruiters, recruitersArray(0))}
${mapSchemaToValues(RGStoredVals.schemaRecruiters, recruitersArray(1))}
${mapSchemaToValues(RGStoredVals.schemaRecruiters, recruitersArray(2))}
      """)



  //}

}