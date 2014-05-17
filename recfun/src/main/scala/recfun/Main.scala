package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {

    def pascalIter(c: Int, r: Int): Int = {
      if (r * c == 0 || r == 1 || r == c) 1
      else pascalIter(c - 1, r - 1) + pascalIter(c, r - 1)
    }

    pascalIter(c, r)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def balanceIter(chars: List[Char], acc: Int, openFlag: Boolean): Boolean = {
      chars match {
        case '(' :: tail => balanceIter(tail, acc+1, openFlag = true)
        case ')' :: tail => balanceIter(tail, acc-1, openFlag = false)
        case _ :: tail => balanceIter(tail, acc, openFlag)
        case Nil => acc == 0 && !openFlag
      }
    }
//    def balanceIter(chars: List[Char]): Int = {
//      if (chars.isEmpty) 0
//      else if (chars.head == '(') {
//        openFlag = true
//        1 + balanceIter(chars.tail)
//      }
//      else if (chars.head == ')') {
//        openFlag = false
//        -1 + balanceIter(chars.tail)
//      }
//      else balanceIter(chars.tail)
//    }
    balanceIter(chars, 0, openFlag = false)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    def countIter(coins: List[Int], money: Int): Int = {
      if (money == 0) 1
      else if (money < 0) 0
      else if (coins.isEmpty) 0
      else countIter(coins.tail, money) + countIter(coins, money-coins.head)
    }
    countIter(coins, money)
  }
}
