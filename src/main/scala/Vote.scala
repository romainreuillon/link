/**
  * Created by Romain Reuillon on 20/07/16.
  *
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU Affero General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *
  */

package vote.webapp

import scala.scalajs.js
import org.scalajs.dom
import dom.document
import upickle.default._

import scalatags.JsDom.all._
import rx._
import RxWrap._
import org.scalajs.dom.crypto.Crypto
import org.scalajs.dom.raw._

object Vote extends js.JSApp {

  @js.native
  trait Transaction extends js.Object {
    def verifySignature(): Boolean = js.native
    def sign(privateKey: js.Array[Int]): Unit = js.native
    def serialize(): js.Array[Int] = js.native
  }

  @js.native
  trait Wallet extends js.Object {
    def getAddressString(): String = js.native
    def getPrivateKey(): js.Array[Int] = js.native
  }

  def main(): Unit = {

    println("C'est parti")

    val v = Var { "Hello world "}

//    js.Dynamic.global.requirejs(
//      js.Array("./ethereumjs-tx"),
//      (ns: js.Dynamic) => {
//        val rawTx = js.Array(
//          "0x00",
//          "0x09184e72a000",
//          "0x2710",
//          "0x0000000000000000000000000000000000000000",
//          "0x00",
//          "0x7f7465737432000000000000000000000000000000000000000000000000000000600057",
//          "0x1c",
//          "0x5e1d3a76fbf824220eafc8c79ad578ad2b67d01b0c2425eb1f1347e8f50882ab",
//          "0x5bd428537f05f9830e93792f90ea6a3e2d1ee84952dd96edbae9f658f831ab13")
//        val tx = js.Dynamic.newInstance(ns.Tx)(rawTx).asInstanceOf[Transaction]
//        println(tx.verifySignature())
//      }
//    )

    def load(wallet: String, password: String) = {
      println(js.typeOf(js.Dynamic.global.mist))
      js.Dynamic.global.requirejs(
        js.Array("./ethereumjs-wallet", "ethereumjs-tx"),
        (nsW: js.Dynamic, nsTx: js.Dynamic) => {
          val w = nsW.Wallet.fromV3(wallet, password).asInstanceOf[Wallet]

          val rawTx = js.Dictionary(
             "nonce" -> "0x00",
             "gasPrice" -> "0x09184e72a000",
             "gasLimit" -> "0x2710",
             "to" -> "0x0000000000000000000000000000000000000000",
             "value" -> "0x00",
             "data" -> "0x7f7465737432000000000000000000000000000000000000000000000000000000600057"
          )

          val tx = js.Dynamic.newInstance(nsTx.Tx)(rawTx).asInstanceOf[Transaction]
          tx.sign(w.getPrivateKey)

          println(tx.serialize().mkString)
         // v() = w.getPrivateKey()
        }
      )
    }

    val uploadWallet = input(`type`:="file", id := "fileInput", accept := ".json").render
    val password = input(`type`:="password").render

    val loadWallet = (e: Event) => {
      val reader = new dom.FileReader()
      reader.readAsText(uploadWallet.files.item(0))
      reader.onloadend =
        (e: ProgressEvent) => {
          load(reader.result.asInstanceOf[String], password.value)
          // v() = read[Wallet](reader.result.asInstanceOf[String]).toString
        }
    }

    uploadWallet.onchange = loadWallet
    password.onkeyup = loadWallet


    dom.document.body.appendChild(div(uploadWallet, password, br(), u(Rx(v()))).render)


  }
}
