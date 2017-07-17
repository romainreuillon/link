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
import scala.scalajs.js._
import org.scalajs.dom

import scalatags.JsDom.all._
import rx._
import scaladget.api.{BootstrapTags=> bs}
import scaladget.stylesheet.all._
import bs._
import scaladget.tools.JsRxTags._
import scala.scalajs.js.annotation.{JSGlobal, JSName}

object Link extends js.JSApp {
//
//  @js.native
//  trait Transaction extends js.Object {
//    def verifySignature(): Boolean = js.native
//    def sign(privateKey: js.Array[Int]): Unit = js.native
//    def serialize(): js.Array[Int] = js.native
//  }
//
//  @js.native
//  trait Wallet extends js.Object {
//    def getAddressString(): String = js.native
//    def getPrivateKey(): js.Array[Int] = js.native
//  }


  type CallBack[T] = js.Function2[js.Error, T, _]

  @js.native
  trait HttpProvider extends js.Object {

  }

  @js.native
  trait Web3 extends js.Object {
    def setProvider(provider: HttpProvider): Unit
    def providers: Providers
    def eth: Eth
  }

  @js.native
  trait Providers extends js.Object {
    def HttpProvider: js.Dynamic = js.native
  }

  @js.native
  trait Eth extends js.Object {
    def getBalance(address: String, defaultBlock: String | Number = js.native, callBack: CallBack[BigNumber] = js.native): BigNumber
    def contract(abi: js.Dynamic): js.Dynamic
    def accounts: js.Dynamic
    def getAccounts(callBack: CallBack[js.Array[js.Dynamic]])
    def sendTransaction(transactionObject: js.Dictionary[js.Any], callBack: CallBack[String] = js.native): String
  }

  @js.native
  trait BigNumber extends js.Object {
    def toNumber(): Double
    def toString(l: Int): String
  }


  @JSGlobal("uportconnect.Connect")
  @js.native
  class Connect(appName: String) extends js.Object {
    def getWeb3(): Web3 = js.native
  }


  def main(): Unit = {
//
    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
    val contractAddress = "0xae59f07cd8cb680db5d265359d6faa841570656b"
//    val web3 = Var[Option[Web3]] {
//      None
//    }

//    def localWeb3 = {
//      val web3 = js.Dynamic.newInstance(js.Dynamic.global.Web3)().asInstanceOf[Web3]
//      val provider = js.Dynamic.newInstance(web3.providers.HttpProvider)("http://localhost:8545").asInstanceOf[HttpProvider]
//      web3.setProvider(provider)
//      web3
//    }

    dom.window.addEventListener("load", (e: dom.Event) => {

      // Checking if Web3 has been injected by the browser (Mist/MetaMask)
      if (js.typeOf(js.Dynamic.global.web3) != "undefined") {
        startApp(js.Dynamic.global.web3.asInstanceOf[Web3])
      } else {
        val uport = new Connect("DataWards")
        startApp(uport.getWeb3)
      }
    })

    def startApp(web3: Web3) {

      val contractABI =
        js.JSON.parse("""[{"constant":false,"inputs":[],"name":"getNb","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"propose","outputs":[{"name":"","type":"bool"}],"payable":false,"type":"function"},{"inputs":[],"payable":false,"type":"constructor"}]""")

      val errorValue = Var[Option[Error]] { None }
      val balanceValue = Var { "NA" }
      val contactValue = Var { "NA" }
      val transactionValue = Var[Option[String]] { None }

      def defined[T](t: T) =
        if(js.isUndefined(t)) None else Some(t)

      def contract: js.Dynamic = web3.eth.contract(contractABI).at(contractAddress)

      def logError[T](error: Error)(f: => T): Unit = {
        if(error != null) errorValue() = Some(error)
        else f
      }

      def queryBalance = () => {
        web3.eth.getAccounts(querryAccounts)

        def querryAccounts(error: Error, accounts: js.Array[js.Dynamic]) = logError(error) {
          def add = defined(accounts).flatMap(_.headOption)

          def setBalance(error: js.Error, balance: BigNumber) = logError(error) {
            balanceValue() = s"${balance.toNumber() / 1e18} RETH"
          }

          add match {
            case Some(add) => web3.eth.getBalance(add.toString, callBack = setBalance)
            case None => errorValue() = Some(Error("Address undefined"))
          }
        }

      }


      def queryContract = () => {
        def setNbContract(error: js.Error, result: js.Dynamic) = logError(error) {
         contactValue() = result.toString
        }

        contract.getNb.call(setNbContract(_,_))
      }

      def callPropose = () => {
        val getData = contract.propose.getData()
        val transaction =
          js.Dictionary[js.Any](
            "to" -> contractAddress,
            "data" -> getData)

        def proposeCallBack(error: Error, result: String) = logError(error) {
          transactionValue() = Some(result)
        }

        web3.eth.sendTransaction(transaction, proposeCallBack(_, _))

        //println(getData)
        //finally paas this data parameter to send Transaction
//        web3.eth.sendTransaction({to:address, from:organisationWallet, data: getData});
//
//
//        web3.eth.sendTransaction(contract.propose)
//        contract.propose()
//        miniTokentoken.transfer(toAddress, value, { from: addr })
//
//          .then(function (txHash) {
//            console.log('Transaction sent')
//            console.dir(txHash)
//
//            waitForTxToBeMined(txHash)
//          })
//
//        .catch(console.error)
      }

      //    val uploadWallet = input(`type`:="file", id := "fileInput", accept := ".json").render
      //    val password = input(`type`:="password").render

      //    val loadWallet = (e: Event) => {
      //      val reader = new dom.FileReader()
      //      reader.readAsText(uploadWallet.files.item(0))
      //      reader.onloadend =
      //        (e: ProgressEvent) => {
      //          load(reader.result.asInstanceOf[String], password.value)
      //          // v() = read[Wallet](reader.result.asInstanceOf[String]).toString
      //        }
      //    }
      //
      //    uploadWallet.onchange = loadWallet
      //    password.onkeyup = loadWallet

      //    dom.document.body.appendChild(div(uploadWallet, password, br(), u(Rx(v()))).render)

     val buttonStyle: ModifierSeq = Seq(
      margin := 10
    )

      val balanceButton = bs.button("Query Balance", buttonStyle +++ btn_primary)(onclick := queryBalance)
      val contractButton = bs.button("Query Contract", buttonStyle +++ btn_primary)(onclick := queryContract)
      val proposeButton = bs.button("Propose", buttonStyle +++ btn_primary)(onclick := callPropose)

      val errorMessage: Rx[String] = errorValue.map(_.map(_.message).getOrElse("No error"))

      dom.document.body.appendChild(
        div(
          balanceButton, "balance: ", Rx(balanceValue()), br(),
          contractButton, "number of contracts: ", Rx(contactValue()), br(),
          proposeButton, br(),
          a(href := "https://rinkeby.etherscan.io/address/" + contractAddress, target := "_blank", "contract transactions"), br(),
          "Error: ", Rx(errorMessage())
        ).render
      )

    }
  }



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

  //    def load(wallet: String, password: String) = {
  //      println(js.typeOf(js.Dynamic.global.mist))
  //      js.Dynamic.global.requirejs(
  //        js.Array("./ethereumjs-wallet", "ethereumjs-tx"),
  //        (nsW: js.Dynamic, nsTx: js.Dynamic) => {
  //          val w = nsW.Wallet.fromV3(wallet, password).asInstanceOf[Wallet]
  //
  //          val rawTx = js.Dictionary(
  //             "nonce" -> "0x00",
  //             "gasPrice" -> "0x09184e72a000",
  //             "gasLimit" -> "0x2710",
  //             "to" -> "0x0000000000000000000000000000000000000000",
  //             "value" -> "0x00",
  //             "data" -> "0x7f7465737432000000000000000000000000000000000000000000000000000000600057"
  //          )
  //
  //          val tx = js.Dynamic.newInstance(nsTx.Tx)(rawTx).asInstanceOf[Transaction]
  //          tx.sign(w.getPrivateKey)
  //
  //          println(tx.serialize().mkString)
  //         // v() = w.getPrivateKey()
  //        }
  //      )
  //    }
}
