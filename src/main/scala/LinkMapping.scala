/**
  * Created by Mathieu Leclaire on 06/09/17.
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
import scala.scalajs.js.annotation.JSGlobal
import scala.scalajs.js.|

object LinkMapping {

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

    def sendTransaction(transactionObject: js.Dictionary[js.Any], callBack: CallBack[js.Dynamic] = js.native): js.Dynamic

    def getBlock(id: String, callBack: CallBack[Block] = js.native): Block
  }

  @js.native
  trait Block extends js.Object {
    def hash: js.Dynamic

    def number: Int
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

}
