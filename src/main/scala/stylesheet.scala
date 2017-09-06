package vote.webapp

import scalatags.Text.all._

package object stylesheet {
  def center(percentage: Int) = Seq(
    width := s"$percentage%",
    margin := "0 auto",
    display := "block"
  )
}