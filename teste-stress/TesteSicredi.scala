/*
 * Copyright 2011-2018 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class TesteSicredi extends Simulation {

  val httpProtocol = http
    .baseUrl("https://sicredi-teste.herokuapp.com")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_10 = Map("Content-Type" -> "application/json")

  val scn = scenario("Teste Stress Avaliação Sicredi")
    .exec(http("Contar Votos")
      .get("/v1/sessoes-votacao/1/contagem-votos"))
    .pause(2)
    .exec(http("Incluir Pauta")
      .post("/v1/pautas")
      .headers(headers_10)
      .body(StringBody(
        """{
        "assunto": "Vamos trabalhar no Sicredi?"
        }""".stripMargin)))
    .pause(2)
    .exec(http("Abrir Sessão de Votação")
      .post("/v1/pautas/1/sessoes-votacao")
      .headers(headers_10)
      .body(StringBody(
        """{
        "horas": 24,
        "minutos": 0
        }""".stripMargin)))
    .pause(2)
  setUp(scn.inject(atOnceUsers(300)).protocols(httpProtocol))
}
