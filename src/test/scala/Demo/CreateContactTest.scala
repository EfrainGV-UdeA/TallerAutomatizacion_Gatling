package Demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import Demo.Data._

class CreateContactTest extends Simulation{

  // 1 Http Conf
  val httpConf = http.baseUrl(url)
    .acceptHeader("application/json")
    //Verificar de forma general para todas las solicitudes
    .check(status.is(200))

  // 2 Scenario Definition
  val scn = scenario("CreateContact").
    exec(http("Create Contact")
      .post(s"contacts")
         .header("Authorization", "Bearer ${authToken}")
         .body(StringBody(s"""{"firstName": "Armando",
    "lastName": "Cassas",
    "birthdate": "1970-01-01",
    "email": "jdoe@fake.com",
    "phone": "8005555555",
    "street1": "1 Main St.",
    "street2": "Apartment A",
    "city": "Anytown",
    "stateProvince": "KS",
    "postalCode": "12345",
    "country": "USA"}""")).asJson
       //Recibir información de la cuenta
      .check(status.is(201))
    )

  // 3 Load Scenario
  setUp(
    scn.inject(rampUsersPerSec(5).to(15).during(30))
  ).protocols(httpConf);
}
